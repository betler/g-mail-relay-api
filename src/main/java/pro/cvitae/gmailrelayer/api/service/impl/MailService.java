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

@Service
public class MailService implements IMailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ConfigFileHelper configHelper;

    @Override
    public void sendEmail(final EmailMessage message, final String applicationId, final String messageType)
            throws MessagingException {
        this.send(message, applicationId, messageType);
        this.logger.debug("Sent email to {}", message.getTo());
    }

    @Async
    @Override
    public void sendAsyncEmail(final EmailMessage message, final String applicationId, final String messageType) {
        try {
            this.send(message, applicationId, messageType);
            this.logger.debug("Sent async email to {}", message.getTo());
        } catch (final MessagingException me) {
            this.logger.error("Error sending mail from {} to {}", message.getFrom(), message.getTo(), me);
        }
    }

    private void send(final EmailMessage message, final String applicationId, final String messageType)
            throws MessagingException {
        final JavaMailSender mailSender = this.configHelper.senderForApi(message.getFrom(), applicationId, messageType);
        final MimeMessage mime = mailSender.createMimeMessage();

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

        helper.setFrom(message.getFrom());
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
