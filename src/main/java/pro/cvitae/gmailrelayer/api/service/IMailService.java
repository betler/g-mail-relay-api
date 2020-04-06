package pro.cvitae.gmailrelayer.api.service;

import pro.cvitae.gmailrelayer.api.model.EmailMessage;

public interface IMailService {

    void sendEmail(final EmailMessage message);

    void sendAsyncEmail(final EmailMessage message);
}
