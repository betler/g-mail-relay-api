package pro.cvitae.gmailrelayer.api.service;

import pro.cvitae.gmailrelayer.api.model.EmailMessage;

public interface IMailApiService {

    /**
     * Processes the email received.
     * 
     * @param message
     */
    void sendEmail(final EmailMessage message);
}
