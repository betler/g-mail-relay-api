package pro.cvitae.gmailrelayer.api.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.service.IMailService;

@Service
public class MailService implements IMailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void sendEmail(final EmailMessage message) {
        // TODO Auto-generated method stub
        this.deleteMe();
        this.logger.debug("Sent email to {}", message.getTo());
    }

    @Async
    @Override
    public void sendAsyncEmail(final EmailMessage message) {
        // TODO Auto-generated method stub
        this.deleteMe();
        this.logger.debug("Sent async email to {}", message.getTo());
    }

    private void deleteMe() {
        try {
            Thread.sleep(2000);
        } catch (final InterruptedException e) {
            // TODO Auto-generated catch block
            this.logger.error("Interrupted exception", e);
            Thread.currentThread().interrupt();

        }
    }
}
