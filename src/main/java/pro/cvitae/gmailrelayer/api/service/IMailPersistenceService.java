package pro.cvitae.gmailrelayer.api.service;

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
}
