package pro.cvitae.gmailrelayer.api.service;

import javax.mail.MessagingException;

import pro.cvitae.gmailrelayer.api.model.EmailMessage;

public interface IMailService {

    void sendEmail(final EmailMessage message, final String applicationId, final String messageType)
            throws MessagingException;

    void sendAsyncEmail(final EmailMessage message, final String applicationId, final String messageType);
}
