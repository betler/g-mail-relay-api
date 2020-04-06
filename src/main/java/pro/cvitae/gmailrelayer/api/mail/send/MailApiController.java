package pro.cvitae.gmailrelayer.api.mail.send;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.model.SendEmailResult;

@RestController
public class MailApiController implements MailApi {

    @Override
    @PostMapping(value = "/mail/send", produces = { "application/json" }, consumes = { "application/json" })
    public ResponseEntity<SendEmailResult> sendEmail(@Valid final EmailMessage message) {

        // TODO Validate

        return ResponseEntity.ok(new SendEmailResult());
    }

}
