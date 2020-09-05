package pro.cvitae.gmailrelayer.persistence.repository.helper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

import pro.cvitae.gmailrelayer.api.model.DeliveryType;
import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.model.MessageHeaders;
import pro.cvitae.gmailrelayer.api.model.TextFormat;
import pro.cvitae.gmailrelayer.api.service.IMailService;
import pro.cvitae.gmailrelayer.persistence.entity.Message;

public abstract class MessageHelper {

    @Autowired
    static IMailService mailService;

    private MessageHelper() {
        throw new UnsupportedOperationException("Nope");
    }

    public static Message from(final EmailMessage message) {
        final Message entity = new Message();
        entity.setApplicationId(message.getApplicationId());
        entity.setMessageType(message.getMessageType());
        entity.setTextFormat(TextFormat.valueOf(message.getTextFormat()).value());
        entity.setTextEncoding(message.getTextEncoding());
        entity.setDeliveryType(DeliveryType.valueOf(message.getDeliveryType()).value());
        entity.setPriority(message.getPriority());
        entity.setSubject(message.getSubject());

        return entity;
    }

    public static Message from(final MimeMessage message) throws MessagingException {
        final Message entity = new Message();
        final MimeMessageHelper helper = new MimeMessageHelper(message);

        System.out.println("Encoding: " + helper.getEncoding());
        System.out.println("Content type " + message.getContentType());

        entity.setApplicationId(mailService.getValidatedHeader(MessageHeaders.APPLICATION_ID, message));
        entity.setMessageType(mailService.getValidatedHeader(MessageHeaders.MESSAGE_TYPE, message));
        // entity.setTextFormat(TextFormat.valueOf(message.getTextFormat()).value());
        // entity.setTextEncoding(helper.getEncoding());message.getContentType()
        // entity.setDeliveryType(DeliveryType.valueOf(message.getDeliveryType()).value());
        // entity.setPriority(message.getPriority());
        entity.setSubject(message.getSubject());

        return entity;
    }

}
