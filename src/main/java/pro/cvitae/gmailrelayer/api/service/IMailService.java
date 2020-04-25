package pro.cvitae.gmailrelayer.api.service;

import javax.mail.MessagingException;

import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.config.SendingType;

public interface IMailService {

    void sendEmail(final EmailMessage message, final SendingType sendingType) throws MessagingException;

    void sendAsyncEmail(final EmailMessage message, final SendingType sendingType);
}
