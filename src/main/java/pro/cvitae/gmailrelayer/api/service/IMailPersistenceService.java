package pro.cvitae.gmailrelayer.api.service;

import javax.mail.internet.MimeMessage;

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
     */
    Long saveMessage(MimeMessage message, EmailStatus sent);

    /**
     * Updates the status of the given message
     *
     * @param messageId
     * @param error
     */
    void updateStatus(Long messageId, EmailStatus error);

}
