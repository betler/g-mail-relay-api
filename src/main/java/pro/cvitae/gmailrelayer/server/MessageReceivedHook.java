/**
 * Copyright [2020] [https://github.com/betler]
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * @author betler
 *
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
            this.mailService.sendEmail(msg, SendingType.SMTP);
            this.logger.debug("Sent message {} to {}", msg.getMessageID(), msg.getRecipients(Message.RecipientType.TO));

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
