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

import java.util.Properties;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.Validate;
import org.apache.james.protocols.smtp.MailEnvelope;
import org.apache.james.protocols.smtp.SMTPSession;
import org.apache.james.protocols.smtp.hook.HookResult;
import org.apache.james.protocols.smtp.hook.HookReturnCode;
import org.apache.james.protocols.smtp.hook.MessageHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import pro.cvitae.gmailrelayer.config.ConfigFile;
import pro.cvitae.gmailrelayer.config.DefaultConfigItem;

/**
 * @author betler
 *
 */
public class MessageReceivedHook implements MessageHook {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    final ConfigFile configFile;

    public MessageReceivedHook(final ConfigFile configFile) {
        this.configFile = configFile;
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
        final JavaMailSender sender = this.getJavaMailSender();

        try {
            msg = sender.createMimeMessage(mail.getMessageInputStream());
            this.logger.debug("Parsed mime message from {}", msg.getFrom()[0]);
        } catch (final Exception e) {
            this.logger.error("Error parsing mime message from input", e);
            return this.buildHookResult(451, "Error while processing received message");
        }

        try {
            // If from address overriding is set, from is changed
            if (Boolean.TRUE.equals(this.configFile.getSmtpDefault().getOverrideFrom())) {
                msg.setFrom(this.configFile.getSmtpDefault().getOverrideFromAddress());
            }

            // Send message
            sender.send(msg);
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

    private JavaMailSender getJavaMailSender() {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        final Properties props = mailSender.getJavaMailProperties();
        props.putAll(this.getRelayingProperties());

        // Password is not read from properties
        mailSender.setPassword(this.configFile.getSmtpDefault().getPassword());

        return mailSender;
    }

    private Properties getRelayingProperties() {
        final DefaultConfigItem config = this.configFile.getSmtpDefault();
        Validate.matchesPattern(config.getAuthType(), "^(USERPASS|NTLM)$");

        final Properties props = new Properties();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.user", config.getUsername());
        props.put("mail.smtp.password", config.getPassword());
        props.put("mail.smtp.starttls.enable", config.getStarttls());
        props.put("mail.smtp.host", config.getHost());
        props.put("mail.smtp.port", config.getPort());
        props.put("mail.debug", "true");

        if ("NTLM".equals(config.getAuthType())) {
            props.put("mail.smtp.auth.mechanisms", "NTLM");
            props.put("mail.smtp.auth.ntlm.domain", config.getDomain());
        }

        return props;
    }

}
