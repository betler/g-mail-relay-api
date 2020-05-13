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

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pro.cvitae.gmailrelayer.api.exception.MailApiException;
import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.model.ErrorDetail;
import pro.cvitae.gmailrelayer.api.model.SendEmailResult;

@Api(value = "mail")
public interface MailApi {

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    default Optional<String> getAcceptHeader() {
        return this.getRequest().map(r -> r.getHeader("Accept"));
    }

    @ApiOperation(value = "sends an email", nickname = "sendEmail", notes = "Sends an email with the specified data", response = SendEmailResult.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "email sent", response = SendEmailResult.class),
            @ApiResponse(code = 202, message = "email queued for later delivery", response = SendEmailResult.class),
            @ApiResponse(code = 400, message = "invalid input", response = ErrorDetail.class),
            @ApiResponse(code = 401, message = "unauthorized", response = ErrorDetail.class) })
    default ResponseEntity<SendEmailResult> sendEmail(
            @ApiParam(value = "Email to be sent", required = true) @Valid @RequestBody final EmailMessage body)
            throws MailApiException {
        throw new UnsupportedOperationException("Not implemented");
    }

}
