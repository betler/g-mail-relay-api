package pro.cvitae.gmailrelayer.api.service;

import javax.mail.MessagingException;

import pro.cvitae.gmailrelayer.api.model.EmailMessage;

public interface IMailService {

    void sendEmail(final EmailMessage message) throws MessagingException;

    void sendAsyncEmail(final EmailMessage message);
}
