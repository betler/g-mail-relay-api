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
package pro.cvitae.gmailrelayer.server;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.james.protocols.smtp.MailEnvelope;
import org.apache.james.protocols.smtp.SMTPSession;
import org.apache.james.protocols.smtp.hook.HookResult;
import org.apache.james.protocols.smtp.hook.HookReturnCode;
import org.apache.james.protocols.smtp.hook.MessageHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import pro.cvitae.gmailrelayer.api.model.MessageHeaders;
import pro.cvitae.gmailrelayer.api.model.SendingType;
import pro.cvitae.gmailrelayer.api.service.IMailService;
import pro.cvitae.gmailrelayer.config.ConfigFileHelper;

/**
 * @author betler
 *
 */
public class MessageReceivedHook implements MessageHook {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IMailService mailService;

    final ConfigFileHelper configFileHelper;

    /**
     * Default and empty sender in order to parse mime message
     */
    final JavaMailSender defaultSender = new JavaMailSenderImpl();

    public MessageReceivedHook(final ConfigFileHelper configFileHelper) {
        this.configFileHelper = configFileHelper;
    }

    @Override
    public void init(final Configuration config) throws ConfigurationException {
        // Do nothing. It is not invoked, anyway
    }

    @Override
    public void destroy() {
        // Do nothing
    }

    /**
     * Implements the {@link MessageHook#onMessage(SMTPSession, MailEnvelope)}
     * method. Parses incoming message and relays to the configured server.
     */
    @Override
    public HookResult onMessage(final SMTPSession session, final MailEnvelope mail) {

        final MimeMessage msg;
        try {
            msg = new MimeMessage(null, mail.getMessageInputStream());
            this.logger.debug("Parsed mime message from {}", msg.getFrom()[0]);

        } catch (final Exception e) {
            this.logger.error("Error parsing mime message from input", e);
            return this.buildHookResult(451, "Error while processing received message");
        }

        try {
            // Send message
            if ("true".equalsIgnoreCase(this.mailService.getValidatedHeader(MessageHeaders.ASYNC, msg))) {
                this.mailService.sendAsyncEmail(msg, SendingType.SMTP);
                this.logger.debug("Sent async message {} to {}", msg.getMessageID(),
                        msg.getRecipients(Message.RecipientType.TO));
            } else {
                this.mailService.sendEmail(msg, SendingType.SMTP);
                this.logger.debug("Sent message {} to {}", msg.getMessageID(),
                        msg.getRecipients(Message.RecipientType.TO));
            }

        } catch (final Exception e) {
            this.logger.error("Error sending message", e);
            return this.buildHookResult(451, "Error while relaying message");
        }

        // Everything OK
        return HookResult.OK;
    }

    /**
     * Builds a custom {@link HookResult}
     *
     * @param code        SMTP return code
     * @param description description for the return code
     * @return
     */
    private HookResult buildHookResult(final int code, final String description) {
        return HookResult.builder().smtpReturnCode(String.valueOf(code)).smtpDescription(description)
                .hookReturnCode(HookReturnCode.deny()).build();
    }

}
