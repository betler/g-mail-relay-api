package pro.cvitae.gmailrelayer.api.service.impl;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import pro.cvitae.gmailrelayer.api.model.DeliveryType;
import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.service.IMailApiService;
import pro.cvitae.gmailrelayer.api.service.IMailService;

@Service
public class MailApiService implements IMailApiService {

    @Autowired
    IMailService mailService;

    @Override
    public void sendEmail(final EmailMessage message) {

        final DeliveryType deliveryType = DeliveryType.valueOf(message.getDeliveryType());

        if (deliveryType == DeliveryType.PRIORITY_SYNC) {
            // Synchronized sending
            // this.mailService.sendEmail(message);
        } else {
            // Async sending
            // this.mailService.sendAsyncEmail(message);
        }

        try {
            this.forTestingSend(message);
        } catch (final MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void forTestingSend(final EmailMessage message) throws MessagingException {
        final JavaMailSender sender = this.getJavaMailSender();
        final MimeMessage mime = sender.createMimeMessage();
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

        sender.send(mime);
    }

    private JavaMailSender getJavaMailSender() {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("");
        mailSender.setPort(587);

        mailSender.setUsername("");
        mailSender.setPassword(";");

        final Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
