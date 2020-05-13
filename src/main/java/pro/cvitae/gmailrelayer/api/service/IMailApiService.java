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
package pro.cvitae.gmailrelayer.api.service;

import java.io.IOException;

import javax.mail.MessagingException;

import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.model.SendEmailResult;
import pro.cvitae.gmailrelayer.api.model.SendingType;

public interface IMailApiService {

    /**
     * Processes the email received. This is a first level service that attends
     * MailApiController
     *
     * @param message
     * @param sendingType
     * @return
     *
     * @throws MessagingException
     * @throws IOException
     */
    SendEmailResult sendEmail(final EmailMessage message, final SendingType sendingType) throws MessagingException;
}
