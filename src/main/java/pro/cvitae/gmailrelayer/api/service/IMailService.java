package pro.cvitae.gmailrelayer.api.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.model.SendingType;

public interface IMailService {

    void sendEmail(final EmailMessage message, final SendingType sendingType) throws MessagingException;

    void sendEmail(final MimeMessage message, final SendingType sendingType) throws MessagingException;

    void sendAsyncEmail(final EmailMessage message, final SendingType sendingType);

    void sendAsyncEmail(final MimeMessage message, final SendingType sendingType);

    /**
     * Tries to retrieve the given header from the message. If it is set more than
     * once and {@link IllegalArgumentException} is thrown. If not set or empty it
     * returns <code>null</code>. Else, it returns the header value.
     *
     * @param name of the header
     * @param msg
     * @return
     * @throws MessagingException
     */
    String getValidatedHeader(String name, MimeMessage msg) throws MessagingException;
}
