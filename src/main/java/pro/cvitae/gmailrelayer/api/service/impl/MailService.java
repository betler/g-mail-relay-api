package pro.cvitae.gmailrelayer.api.service.impl;

import java.io.ByteArrayInputStream;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.concurrent.Future;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import pro.cvitae.gmailrelayer.api.model.Attachment;
import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.model.EmailStatus;
import pro.cvitae.gmailrelayer.api.model.Header;
import pro.cvitae.gmailrelayer.api.model.MessageHeaders;
import pro.cvitae.gmailrelayer.api.model.SendEmailResult;
import pro.cvitae.gmailrelayer.api.model.SendingType;
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
    public SendEmailResult sendEmail(final EmailMessage message, final SendingType sendingType)
            throws MessagingException {
        final MimeMessage mime = this.send(message, sendingType);
        this.logger.debug("Sent email to {} via {}", message.getTo(), sendingType);
        return this.getSendEmailResult(mime, EmailStatus.SENT);
    }

    @Override
    public SendEmailResult sendEmail(final MimeMessage message, final SendingType sendingType)
            throws MessagingException {
        this.send(message, sendingType);
        this.logger.debug("Sent email to {} via {}", message.getAllRecipients()[0], sendingType);
        return this.getSendEmailResult(message, EmailStatus.SENT);
    }

    @Async
    @Override
    public Future<SendEmailResult> sendAsyncEmail(final EmailMessage message, final SendingType sendingType) {
        MimeMessage mime = null;
        try {
            mime = this.send(message, sendingType);
            this.logger.debug("Sent async email to {} via {}", message.getTo(), sendingType);
            return new AsyncResult<>(this.getSendEmailResult(mime, EmailStatus.SENT));
        } catch (final MessagingException me) {
            this.logger.error("Error sending mail from {} to {}", message.getFrom(), message.getTo(), me);
            return new AsyncResult<>(this.getSafeSendEmailResult(mime, EmailStatus.ERROR));
        }
    }

    @Async
    @Override
    public Future<SendEmailResult> sendAsyncEmail(final MimeMessage message, final SendingType sendingType) {
        try {
            this.send(message, sendingType);
            this.logger.debug("Sent async email to {} via {}", message.getAllRecipients()[0], sendingType);
            return new AsyncResult<>(this.getSendEmailResult(message, EmailStatus.SENT));
        } catch (final MessagingException me) {
            try {
                this.logger.error("Error sending mail from {} to {}", message.getFrom(), message.getAllRecipients()[0],
                        me);
            } catch (final MessagingException e) {
                this.logger.error("Error sending mail from. Couldn't fetch email data for logging", me);
            }

            return new AsyncResult<>(this.getSafeSendEmailResult(message, EmailStatus.ERROR));
        }
    }

    private MimeMessage send(final EmailMessage message, final SendingType sendingType) throws MessagingException {
        // Get mailer and configuration. Needed for overriding from
        final SendingConfiguration sendingConfiguration = this.configHelper.senderFor(sendingType, message.getFrom(),
                message.getApplicationId(), message.getMessageType());
        final JavaMailSender mailSender = sendingConfiguration.getMailSender();
        final DefaultConfigItem config = sendingConfiguration.getConfigItem();

        // Creates the mime message
        final MimeMessage mime = mailSender.createMimeMessage();

        // Configure helper
        final MimeMessageHelper helper = new MimeMessageHelper(mime, this.isMultipart(message),
                message.getTextEncoding());
        helper.setValidateAddresses(true);

        // Recipients (MessageException avoids stream)
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
        if (message.getPriority() != null) {
            helper.setPriority(message.getPriority().intValue());
        }
        if (message.getReplyTo() != null) {
            helper.setReplyTo(message.getReplyTo());
        }
        if (message.getSubject() != null) {
            helper.setSubject(message.getSubject());
        }
        helper.setText(message.getBody(), message.getTextFormat().equals("HTML"));

        // Headers
        if (message.getHeaders() != null) {
            for (final Header h : message.getHeaders()) {
                mime.setHeader(h.getName(), h.getValue());
            }
        }

        // Attachments
        if (message.getAttachments() != null) {
            for (final Attachment a : message.getAttachments()) {

                final InputStreamSource iss = () -> new ByteArrayInputStream(
                        Base64.getDecoder().decode(a.getContent()));
                if (a.getCid() == null || a.getCid().isEmpty()) {
                    // Attachment
                    helper.addAttachment(a.getFilename(), iss, a.getContentType());
                } else {
                    // Inlined attachment
                    helper.addInline(a.getCid(), iss, a.getContentType());
                }

            }
        }

        mailSender.send(mime);
        return mime;

    }

    private void send(final MimeMessage message, final SendingType sendingType) throws MessagingException {
        // Get mailer and configuration. Needed for overriding from
        final String forApplicationId = this.getValidatedHeader(MessageHeaders.APPLICATION_ID, message);
        final String forMessageType = this.getValidatedHeader(MessageHeaders.MESSAGE_TYPE, message);

        final SendingConfiguration sendingConfiguration = this.configHelper.senderFor(sendingType,
                message.getFrom()[0].toString(), forApplicationId, forMessageType);
        final JavaMailSender mailSender = sendingConfiguration.getMailSender();
        final DefaultConfigItem config = sendingConfiguration.getConfigItem();

        // If from address overriding is set, from is changed
        if (Boolean.TRUE.equals(config.getOverrideFrom())) {
            message.setFrom(config.getOverrideFromAddress());
        }

        mailSender.send(message);
    }

    /**
     * Creates a {@link SendEmailResult}
     *
     * @throws MessagingException
     */
    private SendEmailResult getSendEmailResult(final MimeMessage message, final EmailStatus status)
            throws MessagingException {

        final SendEmailResult result = SendEmailResult.getInstance();
        // result.setId(id); this will be set by the service that stores in DB
        result.setMessageId(message == null ? null : message.getMessageID());
        result.setStatus(status);

        return result;

    }

    /**
     * Wraps a call to {@link #getSendEmailResult(MimeMessage, EmailStatus)} without
     * throwing any exception.
     *
     * @param mime
     * @param error
     * @return
     */
    private SendEmailResult getSafeSendEmailResult(final MimeMessage mime, final EmailStatus status) {
        try {
            return this.getSendEmailResult(mime, status);
        } catch (final MessagingException me) {
            this.logger.error(
                    "Error while generating a SendEmailResult. Trying to generate a result without message id", me);
            final SendEmailResult result = new SendEmailResult();
            result.setDate(OffsetDateTime.now(ZoneOffset.UTC));
            result.setStatus(status);

            return result;
        }
    }

    private boolean isMultipart(final EmailMessage message) {
        return message.getAttachments() != null && !message.getAttachments().isEmpty();
    }

    @Override
    public String getValidatedHeader(final String name, final MimeMessage msg) throws MessagingException {
        final String[] header = msg.getHeader(name);

        if (header == null || header.length == 0) {
            return null;
        }

        if (header.length > 1) {
            throw new IllegalArgumentException("Header " + name + " is set more than once");
        }

        final String aux = header[0];
        if ("".equals(aux) || aux == null) {
            return null;
        }

        return aux;
    }

}
