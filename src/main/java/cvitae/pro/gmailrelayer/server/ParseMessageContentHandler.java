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
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.james.mime4j.MimeException;
import org.apache.james.mime4j.parser.AbstractContentHandler;
import org.apache.james.mime4j.stream.BodyDescriptor;
import org.apache.james.mime4j.stream.Field;

/**
 * @author mikel
 *
 */
public class ParseMessageContentHandler extends AbstractContentHandler {

	@Override
	public void endMultipart() throws MimeException {
		System.out.println("endMultipart event");
	}

	@Override
	public void startMultipart(final BodyDescriptor bd) throws MimeException {
		System.out.println("startMultipart - Multipart message detexted, header data = " + bd);
	}

	@Override
	public void body(final BodyDescriptor bd, final InputStream is) throws MimeException, IOException {
		System.out.println("body - Body detected");
		System.out.println("       contents = " + IOUtils.toString(is, bd.getCharset()));
		System.out.println("       header data = " + bd);
	}

	@Override
	public void endBodyPart() throws MimeException {
		System.out.println("endBodyPart event");
	}

	@Override
	public void endHeader() throws MimeException {
		System.out.println("endHeader event");
	}

	@Override
	public void endMessage() throws MimeException {
		System.out.println("endMessage event");
	}

	@Override
	public void epilogue(final InputStream is) throws MimeException, IOException {
		System.out.println("epilogue event, contents = " + is);
	}

	@Override
	public void field(final Field field) throws MimeException {
		System.out.println("field - Header field detected: " + field.getName() + "=" + field.getBody());
	}

	@Override
	public void preamble(final InputStream is) throws MimeException, IOException {
		System.out.println("preamble event, contents = " + is);
	}

	@Override
	public void startBodyPart() throws MimeException {
		System.out.println("startBodyPart event");
	}

	@Override
	public void startHeader() throws MimeException {
		System.out.println("startHeader event");
	}

	@Override
	public void startMessage() throws MimeException {
		System.out.println("startMessage event");
	}

	@Override
	public void raw(final InputStream is) throws MimeException, IOException {
		System.out.println("raw event, content = " + is);
	}

}
