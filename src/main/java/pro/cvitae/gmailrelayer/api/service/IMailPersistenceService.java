package pro.cvitae.gmailrelayer.api.service;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import pro.cvitae.gmailrelayer.api.exception.GmrException;
import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.model.EmailStatus;

public interface IMailPersistenceService {

    /**
     * Stores the given message in database
     *
     * @param message
     * @param status  the sending status to be set
     * @return
     */
    Long saveMessage(EmailMessage message, EmailStatus status);

    /**
     * Stores the given message in database
     *
     * @param message
     * @param status  the sending status to be set
     * @return
     * @throws MessagingException
     * @throws IOException
     * @throws GmrException       If content type is not supported
     */
    Long saveMessage(MimeMessage message, EmailStatus sent) throws MessagingException, IOException, GmrException;

    /**
     * Updates the status of the given message
     *
     * @param messageId
     * @param error
     */
    void updateStatus(Long messageId, EmailStatus error);

}
