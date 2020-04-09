package pro.cvitae.gmailrelayer.api.service;

import javax.mail.MessagingException;

import pro.cvitae.gmailrelayer.api.model.EmailMessage;

public interface IMailApiService {

    /**
     * Processes the email received.
     *
     * @param message
     * @throws MessagingException
     */
    void sendEmail(final EmailMessage message) throws MessagingException;
}
