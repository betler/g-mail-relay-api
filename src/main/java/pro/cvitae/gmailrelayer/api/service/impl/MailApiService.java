/**
 * g-mail-relayer smtp mail relayer and API for sending emails
 * Copyright (C) 2020  https://github.com/betler
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
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

    private static final Short DEFAULT_PRIORITY = (short) 3;

    @Override
    public SendEmailResult sendEmail(final EmailMessage message, final SendingType sendingType)
            throws MessagingException {

        // Completes default value for empty fields
        this.completeEmailMessage(message);

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

    private void completeEmailMessage(final EmailMessage message) {
        if (message.getPriority() == null) {
            message.setPriority(DEFAULT_PRIORITY);
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
