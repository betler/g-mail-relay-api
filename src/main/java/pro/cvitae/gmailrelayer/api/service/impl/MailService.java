package pro.cvitae.gmailrelayer.api.service.impl;

import org.springframework.scheduling.annotation.Async;

import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.service.IMailService;

public class MailService implements IMailService {

    @Override
    public void sendEmail(final EmailMessage message) {
        // TODO Auto-generated method stub

    }

    @Async
    @Override
    public void sendAsyncEmail(final EmailMessage message) {
        // TODO Auto-generated method stub

    }

}
