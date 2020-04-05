package pro.cvitae.gmailrelayer.api;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.model.SendEmailResult;

@RestController
public class MailApiController implements MailApi {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public MailApiController(final ObjectMapper objectMapper, final HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public Optional<ObjectMapper> getObjectMapper() {
        return Optional.ofNullable(this.objectMapper);
    }

    @Override
    public Optional<HttpServletRequest> getRequest() {
        return Optional.ofNullable(this.request);
    }

    @Override
    @PostMapping(value = "/mail/send", produces = { "application/json" }, consumes = { "application/json" })
    public ResponseEntity<SendEmailResult> sendEmail(@Valid final EmailMessage body) {
        // TODO Auto-generated method stub
        return null;
    }

}
