package pro.cvitae.gmailrelayer.api.mail.send;

import java.util.Arrays;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import pro.cvitae.gmailrelayer.api.exception.ErrorDetailException;
import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.model.SendEmailResult;
import pro.cvitae.gmailrelayer.api.service.IMailApiService;

@RestController
public class MailApiController implements MailApi {

    @Autowired
    IMailApiService mailApiService;

    @Override
    @PostMapping(value = "/mail/send", produces = { "application/json" }, consumes = { "application/json" })
    public ResponseEntity<SendEmailResult> sendEmail(@Valid final EmailMessage message) throws ErrorDetailException {

        try {
            this.mailApiService.sendEmail(message);
        } catch (final MessagingException me) {
            throw new ErrorDetailException(me, "mail.send.error", "Unexpected error while sending mail",
                    Arrays.asList(me.getClass().toString(), me.getMessage()));
        }

        return ResponseEntity.ok(new SendEmailResult());
    }

}
