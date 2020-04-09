package pro.cvitae.gmailrelayer.api.service.impl;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
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
    public void sendEmail(final EmailMessage message) throws MessagingException {

        final DeliveryType deliveryType = DeliveryType.valueOf(message.getDeliveryType());

        if (deliveryType.equals(DeliveryType.PRIORITY_SYNC)) {
            // Synchronized sending
            this.mailService.sendEmail(message);
        } else if (deliveryType.equals(DeliveryType.PRIORITY_ASYNC)) {
            // Async sending
            this.mailService.sendAsyncEmail(message);
        } else if (deliveryType.equals(DeliveryType.QUEUE)) {
            // Queued sending
        } else {
            // Should not happen
            throw new UnsupportedOperationException();
        }

    }

}
