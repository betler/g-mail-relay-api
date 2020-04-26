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
import javax.mail.MessagingException;
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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import pro.cvitae.gmailrelayer.config.ConfigFileHelper;
import pro.cvitae.gmailrelayer.config.SendingConfiguration;
import pro.cvitae.gmailrelayer.config.SendingType;

/**
 * @author betler
 *
 */
public class MessageReceivedHook implements MessageHook {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
            // If from address overriding is set, from is changed
            if (Boolean.TRUE.equals(this.configFileHelper.getConfigFile().getSmtpDefault().getOverrideFrom())) {
                msg.setFrom(this.configFileHelper.getConfigFile().getSmtpDefault().getOverrideFromAddress());
            }

            // Send message
            final JavaMailSender sender = this.getSendingConfiguration(msg).getMailSender();
            sender.send(msg);

            this.logger.debug("Sent message {} to {}", msg.getMessageID(), msg.getRecipients(Message.RecipientType.TO));

        } catch (final IllegalArgumentException iae) {
            this.logger.error("Error in received email parameters", iae);
            return this.buildHookResult(451, iae.getMessage());
        } catch (final Exception e) {
            this.logger.error("Error sending message", e);
            return this.buildHookResult(451, "Error while relaying message");
        }

        // Everything OK
        return HookResult.OK;
    }

    private SendingConfiguration getSendingConfiguration(final MimeMessage msg) throws MessagingException {

        final String forFrom = msg.getFrom()[0].toString();
        final String forApplicationId = this.getValidatedHeader("X-GMR-APPLICATION-ID", msg);
        final String forMessageType = this.getValidatedHeader("X-GMR-MESSAGE-TYPE", msg);

        return this.configFileHelper.senderFor(SendingType.SMTP, forFrom, forApplicationId, forMessageType);
    }

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
    private String getValidatedHeader(final String name, final MimeMessage msg) throws MessagingException {
        final String[] header = msg.getHeader(name);

        if (header == null || header.length == 0) {
            return null;
        }

        if (header.length > 1) {
            throw new IllegalArgumentException("Header " + name + " is set more than once");
        }

        final String aux = header[0];
        if ("".equals(aux) || aux == null) {
            return null;
        }

        return aux;
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
