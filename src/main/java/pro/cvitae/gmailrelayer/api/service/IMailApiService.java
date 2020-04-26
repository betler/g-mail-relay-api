package pro.cvitae.gmailrelayer.api.service;

import java.io.IOException;

import javax.mail.MessagingException;

import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.model.SendingType;

public interface IMailApiService {

    /**
     * Processes the email received.
     *
     * @param message
     * @param sendingType
     *
     * @throws MessagingException
     * @throws IOException
     */
    void sendEmail(final EmailMessage message, final SendingType sendingType) throws MessagingException;
}
