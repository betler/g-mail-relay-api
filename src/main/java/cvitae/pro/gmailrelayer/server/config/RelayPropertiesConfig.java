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
package cvitae.pro.gmailrelayer.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author betler
 *
 */
@Configuration
@ConfigurationProperties(prefix = "relayer.smtp.relay")
public class RelayPropertiesConfig {

	private boolean overrideFrom;

	private Auth auth;

	private Server server;

	/**
	 * @return the overrideFrom
	 */
	public boolean isOverrideFrom() {
		return this.overrideFrom;
	}

	/**
	 * @param overrideFrom the overrideFrom to set
	 */
	public void setOverrideFrom(final boolean overrideFrom) {
		this.overrideFrom = overrideFrom;
	}

	/**
	 * @return the auth
	 */
	public Auth getAuth() {
		return this.auth;
	}

	/**
	 * @param auth the auth to set
	 */
	public void setAuth(final Auth auth) {
		this.auth = auth;
	}

	/**
	 * @return the server
	 */
	public Server getServer() {
		return this.server;
	}

	/**
	 * @param server the server to set
	 */
	public void setServer(final Server server) {
		this.server = server;
	}

}
