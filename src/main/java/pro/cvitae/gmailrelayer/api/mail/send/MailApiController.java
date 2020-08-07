/**
 * g-mail-relayer smtp mail relayer and API for sending emails
 * Copyright (C) 2020  https://github.com/betler
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
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
    public ResponseEntity<SendEmailResult> sendEmail(final @Valid EmailMessage message) throws MailApiException {

        try {
            final SendEmailResult result = this.mailApiService.sendEmail(message, SendingType.API);
            return ResponseEntity.ok(result);
        } catch (final MessagingException me) {
            throw new MailApiException(me);
        }

    }

}
