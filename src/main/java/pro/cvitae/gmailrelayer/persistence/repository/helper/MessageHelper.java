package pro.cvitae.gmailrelayer.persistence.repository.helper;

import javax.mail.MessagingException;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.cvitae.gmailrelayer.api.model.DeliveryType;
import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.model.MessageHeaders;
import pro.cvitae.gmailrelayer.api.model.TextFormat;
import pro.cvitae.gmailrelayer.api.service.util.MimeMessageUtils;
import pro.cvitae.gmailrelayer.persistence.entity.Message;

public abstract class MessageHelper {

    private static final Logger logger = LoggerFactory.getLogger(MessageHelper.class);

    private MessageHelper() {
        throw new UnsupportedOperationException("Nope");
    }

    /**
     * Builds a {@link Message} from a {@link EmailMessage}. Does not set all
     * fields.
     *
     * @param message
     * @return
     * @throws MessagingException
     */
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

    /**
     * Builds a {@link Message} from a {@link MimeMessage}. Does not set all fields.
     *
     * @param message
     * @return
     * @throws MessagingException
     */
    public static Message from(final MimeMessage message) throws MessagingException {
        final Message entity = new Message();

        setContentTypeAndEncoding(entity, message);

        entity.setApplicationId(MimeMessageUtils.getValidatedHeader(MessageHeaders.APPLICATION_ID, message));
        entity.setMessageType(MimeMessageUtils.getValidatedHeader(MessageHeaders.MESSAGE_TYPE, message));
        setDeliveryType(entity, message);
        entity.setPriority((short) MimeMessageUtils.getPriority(message));
        entity.setSubject(message.getSubject());

        return entity;
    }

    /**
     * Sets text format ({@link TextFormat#HTML} and {@link TextFormat#TEXT}
     * supported) and the encoding, both taken from the
     * {@link MessageHeaders#CONTENT_TYPE} header.
     *
     * @param entity  Entity to be modified
     * @param message
     * @throws MessagingException
     */
    private static void setContentTypeAndEncoding(final Message entity, final MimeMessage message)
            throws MessagingException {

        // Try to get header
        final ContentType contentType = new ContentType(
                MimeMessageUtils.getValidatedHeader(MessageHeaders.CONTENT_TYPE, message));

        // Check if supported
        if (!contentType.getPrimaryType().equalsIgnoreCase("text")) {
            logger.warn("Current content type is not supported: {}", contentType.getBaseType());
            throw new UnsupportedOperationException("Only text/plain or text/html messages are supported by now");
        }

        if (contentType.getSubType().equalsIgnoreCase("plain")) {
            // Text message
            entity.setTextFormat(TextFormat.TEXT.value());
        } else if (contentType.getSubType().equalsIgnoreCase("html")) {
            // html content
            entity.setTextFormat(TextFormat.HTML.value());
        } else {
            logger.warn("Current content type is not supported: {}", contentType.getBaseType());
            throw new UnsupportedOperationException("Only text/plain or text/html messages are supported by now");
        }

        entity.setTextEncoding(contentType.getParameter("charset"));

    }

    /**
     * Sets delivery type, only {@link DeliveryType#PRIORITY_ASYNC} and
     * {@link DeliveryType#PRIORITY_SYNC} currently supported.
     *
     * @param entity  Entity to be modified
     * @param message
     * @throws MessagingException
     */
    private static void setDeliveryType(final Message entity, final MimeMessage message) throws MessagingException {
        final String delivery = MimeMessageUtils.getValidatedHeader(MessageHeaders.ASYNC, message);

        if ("true".equalsIgnoreCase(delivery)) {
            entity.setDeliveryType(DeliveryType.PRIORITY_SYNC.value());
        } else {
            entity.setDeliveryType(DeliveryType.PRIORITY_ASYNC.value());
        }
    }

}
