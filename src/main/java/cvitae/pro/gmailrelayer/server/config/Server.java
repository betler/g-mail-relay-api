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

/**
 * @author betler
 */
public class Server {

	private String host;

	private Integer port;

	private Boolean starttls;

	/**
	 * @return the host
	 */
	public String getHost() {
		return this.host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(final String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public Integer getPort() {
		return this.port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(final Integer port) {
		this.port = port;
	}

	/**
	 * @return the starttls
	 */
	public Boolean getStarttls() {
		return this.starttls;
	}

	/**
	 * @param starttls the starttls to set
	 */
	public void setStarttls(final Boolean starttls) {
		this.starttls = starttls;
	}

}