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
package cvitae.pro.gmailrelayer.server;

import java.util.Collection;
import java.util.Properties;

import org.apache.commons.lang3.Validate;
import org.apache.james.protocols.api.handler.ProtocolHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author betler
 *
 */
@Configuration
public class SmtpServerConfiguration {

	@Value("${relayer.smtp.listening.port}")
	private Integer port;

	@Value("${relayer.smtp.starttls.enable}")
	private Boolean starttls;

	@Value("${relayer.smtp.auth.type}")
	private String authType;

	@Value("${relayer.smtp.auth.username}")
	private String username;

	@Value("${relayer.smtp.auth.password}")
	private String password;

	@Value("${relayer.smtp.server.host}")
	private String smtpHost;

	@Value("${relayer.smtp.server.port}")
	private Integer smtpPort;

	@Bean(initMethod = "start", destroyMethod = "stop")
	public SmtpServer smtpServer(final Collection<ProtocolHandler> handlers) {
		handlers.add(new MessageReceivedHook(this.getRelayingProperties()));
		return new SmtpServer(this.port, handlers);
	}

	private Properties getRelayingProperties() {
		Validate.matchesPattern(this.authType, "^(USERPASS|NTLM)$");
		Validate.inclusiveBetween(1l, 65535l, this.smtpPort);

		final Properties props = new Properties();

		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.user", this.username);
		props.put("mail.smtp.password", this.password);
		props.put("mail.smtp.starttls.enable", this.starttls);
		props.put("mail.smtp.host", this.smtpHost);
		props.put("mail.smtp.port", this.smtpPort);
		props.put("mail.debug", "true");

		if ("NTLM".equals(this.authType)) {
			props.put("mail.smtp.auth.mechanisms", "NTLM");
			props.put("mail.smtp.auth.ntlm.domain", "");
		}

		return props;
	}

}
