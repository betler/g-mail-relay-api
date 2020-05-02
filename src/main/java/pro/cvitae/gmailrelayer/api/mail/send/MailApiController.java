package pro.cvitae.gmailrelayer.api.mail.send;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import pro.cvitae.gmailrelayer.api.exception.MailApiException;
import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.model.SendEmailResult;
import pro.cvitae.gmailrelayer.api.model.SendingType;
import pro.cvitae.gmailrelayer.api.service.IMailApiService;

@RestController
public class MailApiController implements MailApi {

    @Autowired
    IMailApiService mailApiService;

    @Override
    @PostMapping(value = "/mail/send", produces = { "application/json" }, consumes = { "application/json" })
    public ResponseEntity<SendEmailResult> sendEmail(@Valid final EmailMessage message) throws MailApiException {

        try {
            final SendEmailResult result = this.mailApiService.sendEmail(message, SendingType.API);
            return ResponseEntity.ok(result);
        } catch (final MessagingException me) {
            throw new MailApiException(me);
        }

    }

}
