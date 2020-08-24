package pro.cvitae.gmailrelayer.persistence.repository.helper;

import pro.cvitae.gmailrelayer.api.model.DeliveryType;
import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.model.TextFormat;
import pro.cvitae.gmailrelayer.persistence.entity.Message;

public abstract class MessageHelper {

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

}
