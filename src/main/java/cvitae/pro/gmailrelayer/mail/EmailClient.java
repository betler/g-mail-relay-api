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
package cvitae.pro.gmailrelayer.mail;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.Validate;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author betler
 *
 */
public class EmailClient {

	public interface SmtpServer {
		SslConnection smtpServer(String host, int port);
	}

	public interface SslConnection {
		SmtpCredentials isSsl(boolean ssl);
	}

	public interface SmtpCredentials {

		Addressing ntlmAuthentication(String user, String pass, String domain);

		Addressing userpassAuthentication(String user, String pass);
	}

	public interface Addressing {
		Addressing to(List<String> to);

		Addressing cc(List<String> cc);

		Addressing bcc(List<String> bcc);

		Subject headers(HashMap<String, String> headers);

		Subject noExtraHeaders();
	}

	public interface Subject {
		Body subject(String subject);
	}

	public interface Body {
		void body(String body);

		void htmlBody(String htmlBody);
	}

	public static SmtpServer instance() {
		return new ClientBuilder();
	}

	public static final class ClientBuilder
			implements SmtpServer, SslConnection, SmtpCredentials, Addressing, Subject, Body {

		@Setter
		@Getter
		private String host;

		@Setter
		@Getter
		private int port;

		@Setter
		@Getter
		private boolean ssl;

		@Setter
		@Getter
		private String user;

		@Setter
		@Getter
		private String pass;

		@Setter
		@Getter
		private String domain;

		@Setter
		@Getter
		private HashMap<String, String> headers;

		@Setter
		@Getter
		private List<String> bcc;

		@Setter
		@Getter
		private List<String> cc;

		@Setter
		@Getter
		private List<String> to;

		@Setter
		@Getter
		private String subject;

		@Setter
		@Getter
		private String body;

		@Setter
		@Getter
		private String htmlBody;

		ClientBuilder() {

		}

		@Override
		public SmtpCredentials isSsl(final boolean ssl) {
			this.ssl = ssl;
			return this;
		}

		@Override
		public SslConnection smtpServer(final String host, final int port) {
			Validate.notBlank(host);
			Validate.inclusiveBetween(25, 65535, port);
			this.host = host;
			this.port = port;
			return this;
		}

		@Override
		public Addressing ntlmAuthentication(final String user, final String pass, final String domain) {
			Validate.notBlank(user);
			Validate.notBlank(pass);
			Validate.notBlank(domain);
			this.user = user;
			this.pass = pass;
			this.domain = domain;
			return this;
		}

		@Override
		public Addressing userpassAuthentication(final String user, final String pass) {
			Validate.notBlank(user);
			Validate.notBlank(pass);
			this.user = user;
			this.pass = pass;
			return this;
		}

		@Override
		public void body(final String body) {
			this.body = body;
		}

		@Override
		public void htmlBody(final String htmlBody) {
			this.htmlBody = htmlBody;
		}

		@Override
		public Body subject(final String subject) {
			Validate.notBlank(subject);
			this.subject = subject;
			return this;
		}

		@Override
		public Addressing to(final List<String> to) {
			this.to = to;
			return this;
		}

		@Override
		public Addressing cc(final List<String> cc) {
			this.cc = cc;
			return this;
		}

		@Override
		public Addressing bcc(final List<String> bcc) {
			this.bcc = bcc;
			return this;
		}

		@Override
		public Subject headers(final HashMap<String, String> headers) {
			this.headers = headers;
			return this;
		}

		@Override
		public Subject noExtraHeaders() {
			return this;
		}
	}
}
