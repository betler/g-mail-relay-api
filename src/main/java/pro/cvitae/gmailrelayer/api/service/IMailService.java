package pro.cvitae.gmailrelayer.api.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.model.SendingType;

public interface IMailService {

    void sendEmail(final EmailMessage message, final SendingType sendingType) throws MessagingException;

    void sendAsyncEmail(final EmailMessage message, final SendingType sendingType);

    void sendEmail(final MimeMessage message, final SendingType sendingType) throws MessagingException;

    void sendAsyncEmail(final MimeMessage message, final SendingType sendingType);
}
