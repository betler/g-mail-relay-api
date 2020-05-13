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

import java.util.concurrent.Future;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.model.SendEmailResult;
import pro.cvitae.gmailrelayer.api.model.SendingType;

public interface IMailService {

    /**
     * Sends an email message ({@link EmailMessage}) synchronously.
     *
     * @param message
     * @param sendingType
     * @return
     * @throws MessagingException
     */
    SendEmailResult sendEmail(final EmailMessage message, final SendingType sendingType) throws MessagingException;

    /**
     * Sends an email message ({@link MimeMessage}) synchronously.
     *
     * @param message
     * @param sendingType
     * @return
     * @throws MessagingException
     */
    SendEmailResult sendEmail(final MimeMessage message, final SendingType sendingType) throws MessagingException;

    /**
     * Sends an email message ({@link EmailMessage}) asynchronously.
     *
     * @param message
     * @param sendingType
     * @return
     */
    Future<SendEmailResult> sendAsyncEmail(final EmailMessage message, final SendingType sendingType);

    /**
     * Sends an email message ({@link MimeMessage}) asynchronously.
     *
     * @param message
     * @param sendingType
     * @return
     */
    Future<SendEmailResult> sendAsyncEmail(final MimeMessage message, final SendingType sendingType);

    /**
     * Tries to retrieve the given header from the message. If it is set more than
     * once and {@link IllegalArgumentException} is thrown. If not set or empty it
     * returns <code>null</code>. Else, it returns the header value.
     *
     * @param name of the header
     * @param msg
     * @return
     * @throws MessagingException
     */
    String getValidatedHeader(String name, MimeMessage msg) throws MessagingException;
}
