package pro.cvitae.gmailrelayer.api.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.model.Header;
import pro.cvitae.gmailrelayer.api.service.IMailService;

@Service
public class MailService implements IMailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JavaMailSender mailSender;

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
        final MimeMessage mime = this.mailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(mime);
        helper.setValidateAddresses(true);

        // TODO helper. addAttachment(attachmentFilename, dataSource);
        if (message.getBcc() != null) {
            for (final String bcc : message.getBcc()) {
                helper.addBcc(bcc);
            }
        }
        if (message.getCc() != null) {
            for (final String cc : message.getCc()) {
                helper.addCc(cc);
            }
        }
        for (final String to : message.getTo()) {
            helper.addTo(to);
        }
        helper.setFrom(message.getFrom());
        helper.setPriority(message.getPriority().intValue());
        helper.setReplyTo(message.getReplyTo());
        helper.setSubject(message.getSubject());
        helper.setText(message.getBody(), message.getTextFormat().equals("HTML"));

        for (final Header h : message.getHeaders()) {
            mime.setHeader(h.getName(), h.getValue());
        }

        this.mailSender.send(mime);
    }
}
