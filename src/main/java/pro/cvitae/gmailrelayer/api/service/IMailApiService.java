package pro.cvitae.gmailrelayer.api.service;

import java.io.IOException;

import javax.mail.MessagingException;

import pro.cvitae.gmailrelayer.api.model.EmailMessage;

public interface IMailApiService {

    /**
     * Processes the email received.
     *
     * @param message
     * @param applicationId
     * @throws MessagingException
     * @throws IOException
     */
    void sendEmail(final EmailMessage message) throws MessagingException;
}
