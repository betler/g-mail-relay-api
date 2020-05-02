package pro.cvitae.gmailrelayer.api.service.impl;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pro.cvitae.gmailrelayer.api.model.DeliveryType;
import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.model.EmailStatus;
import pro.cvitae.gmailrelayer.api.model.SendEmailResult;
import pro.cvitae.gmailrelayer.api.model.SendingType;
import pro.cvitae.gmailrelayer.api.service.IMailApiService;
import pro.cvitae.gmailrelayer.api.service.IMailService;

@Service
public class MailApiService implements IMailApiService {

    @Autowired
    IMailService mailService;

    @Override
    public SendEmailResult sendEmail(final EmailMessage message, final SendingType sendingType)
            throws MessagingException {

        final DeliveryType deliveryType = DeliveryType.valueOf(message.getDeliveryType());

        if (deliveryType.equals(DeliveryType.PRIORITY_SYNC)) {
            // Synchronized sending
            return this.mailService.sendEmail(message, sendingType);
        } else if (deliveryType.equals(DeliveryType.PRIORITY_ASYNC)) {
            // Async sending
            return this.handleAsyncEmail(message, sendingType);
        } else if (deliveryType.equals(DeliveryType.QUEUE)) {
            // Queued sending
            throw new UnsupportedOperationException("Not yet");
        } else {
            // Should not happen
            throw new UnsupportedOperationException();
        }

    }

    /**
     * Sends the message and generates a {@link SendEmailResult}
     * 
     * @param message
     * @param sendingType
     * @return
     */
    private SendEmailResult handleAsyncEmail(final EmailMessage message, final SendingType sendingType) {
        this.mailService.sendAsyncEmail(message, sendingType);
        final SendEmailResult result = SendEmailResult.getInstance();
        result.setStatus(EmailStatus.SENDING);
        return result;
    }

}
