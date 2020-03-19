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

import java.io.IOException;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.james.mime4j.MimeException;
import org.apache.james.mime4j.parser.ContentHandler;
import org.apache.james.mime4j.parser.MimeStreamParser;
import org.apache.james.protocols.smtp.MailEnvelope;
import org.apache.james.protocols.smtp.SMTPSession;
import org.apache.james.protocols.smtp.hook.HookResult;
import org.apache.james.protocols.smtp.hook.MessageHook;

/**
 * @author mikel
 *
 */
public class CaptureMessageHook implements MessageHook {

	@Override
	public void init(final Configuration config) throws ConfigurationException {
		// Do nothing
	}

	@Override
	public void destroy() {
		// Do nothing
	}

	@Override
	public HookResult onMessage(final SMTPSession session, final MailEnvelope mail) {

		final ContentHandler handler = new ParseMessageContentHandler();
		final MimeStreamParser parser = new MimeStreamParser();
		parser.setContentHandler(handler);
		try {
			parser.parse(mail.getMessageInputStream());
		} catch (MimeException | IOException e) {
			e.printStackTrace();
			return HookResult.DENY;
		}

		return HookResult.OK;
	}

}
