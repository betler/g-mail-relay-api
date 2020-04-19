package pro.cvitae.gmailrelayer.api.service.impl;

import java.io.ByteArrayInputStream;
import java.util.Base64;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import pro.cvitae.gmailrelayer.api.model.Attachment;
import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.model.Header;
import pro.cvitae.gmailrelayer.api.service.IMailService;
import pro.cvitae.gmailrelayer.config.ConfigFileHelper;
import pro.cvitae.gmailrelayer.config.DefaultConfigItem;
import pro.cvitae.gmailrelayer.config.SendingConfiguration;

@Service
public class MailService implements IMailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ConfigFileHelper configHelper;

    @Override
    public void sendEmail(final EmailMessage message) throws MessagingException {
        this.send(message);
        this.logger.debug("Sent email to {}", message.getTo());
    }

    @Async
    @Override
    public void sendAsyncEmail(final EmailMessage message) {
        try {
            this.send(message);
            this.logger.debug("Sent async email to {}", message.getTo());
        } catch (final MessagingException me) {
            this.logger.error("Error sending mail from {} to {}", message.getFrom(), message.getTo(), me);
        }
    }

    private void send(final EmailMessage message) throws MessagingException {
        // Get mailer and configuration. Needed for overriding from
        final SendingConfiguration sendingConfiguration = this.configHelper.senderForApi(message.getFrom(),
                message.getApplicationId(), message.getMessageType());
        final JavaMailSender mailSender = sendingConfiguration.getMailSender();
        final DefaultConfigItem config = sendingConfiguration.getConfigItem();

        // Creates the mime message
        final MimeMessage mime = mailSender.createMimeMessage();

        // Configure helper
        final MimeMessageHelper helper = new MimeMessageHelper(mime, this.isMultipart(message));
        helper.setValidateAddresses(true);

        // Recipients
        for (final String to : message.getTo()) {
            helper.addTo(to);
        }

        // Blind copy
        if (message.getBcc() != null) {
            for (final String bcc : message.getBcc()) {
                helper.addBcc(bcc);
            }
        }

        // CC recipients
        if (message.getCc() != null) {
            for (final String cc : message.getCc()) {
                helper.addCc(cc);
            }
        }

        // from overrided?
        helper.setFrom(
                Boolean.TRUE.equals(config.getOverrideFrom()) ? config.getOverrideFromAddress() : message.getFrom());
        helper.setPriority(message.getPriority().intValue());
        helper.setReplyTo(message.getReplyTo());
        helper.setSubject(message.getSubject());
        helper.setText(message.getBody(), message.getTextFormat().equals("HTML"));

        // Headers
        for (final Header h : message.getHeaders()) {
            mime.setHeader(h.getName(), h.getValue());
        }

        // Attachments
        for (final Attachment a : message.getAttachments()) {

            final InputStreamSource iss = () -> new ByteArrayInputStream(Base64.getDecoder().decode(a.getContent()));
            if (a.getCid() == null || a.getCid().isEmpty()) {
                // Attachment
                helper.addAttachment(a.getFilename(), iss, a.getContentType());
            } else {
                // Inlined attachment
                helper.addInline(a.getCid(), iss, a.getContentType());
            }

        }

        mailSender.send(mime);
    }

    private boolean isMultipart(final EmailMessage message) {
        return message.getAttachments() != null && !message.getAttachments().isEmpty();
    }
}
