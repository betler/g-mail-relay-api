package pro.cvitae.gmailrelayer.api.service;

import java.io.IOException;

import javax.mail.MessagingException;

import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.model.SendEmailResult;
import pro.cvitae.gmailrelayer.api.model.SendingType;

public interface IMailApiService {

    /**
     * Processes the email received. This is a first level service that attends
     * MailApiController
     *
     * @param message
     * @param sendingType
     * @return
     *
     * @throws MessagingException
     * @throws IOException
     */
    SendEmailResult sendEmail(final EmailMessage message, final SendingType sendingType) throws MessagingException;
}
