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

import java.net.InetSocketAddress;
import java.util.Collection;

import org.apache.james.metrics.api.NoopMetricFactory;
import org.apache.james.protocols.api.Protocol;
import org.apache.james.protocols.api.handler.ProtocolHandler;
import org.apache.james.protocols.netty.NettyServer;
import org.apache.james.protocols.smtp.SMTPConfigurationImpl;
import org.apache.james.protocols.smtp.SMTPProtocol;
import org.apache.james.protocols.smtp.SMTPProtocolHandlerChain;
import org.jboss.netty.util.HashedWheelTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author betler
 *
 */
public class SmtpServer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Collection<ProtocolHandler> handlers;

    private NettyServer server;

    /**
     * Listening port.
     */
    private final Integer port;

    public SmtpServer(final Integer port, final Collection<ProtocolHandler> handlers) {
        this.handlers = handlers;
        this.port = port;
    }

    public void start() throws Exception {
        final SMTPConfigurationImpl smtpConfiguration = new SMTPConfigurationImpl();
        smtpConfiguration.setSoftwareName("Spring Boot g-mail-relayer SMTP Server");
        smtpConfiguration.setUseAddressBracketsEnforcement(false);

        final SMTPProtocolHandlerChain chain = new SMTPProtocolHandlerChain(new NoopMetricFactory());
        chain.addAll(this.handlers);
        chain.wireExtensibleHandlers();

        final Protocol protocol = new SMTPProtocol(chain, smtpConfiguration);

        this.server = new NettyServer.Factory(new HashedWheelTimer()).protocol(protocol).build();
        this.server.setListenAddresses(new InetSocketAddress(this.port));
        this.server.setTimeout(120);
        this.server.bind();
        this.logger.info("SMTP Server started on port {}", this.port);
    }

    public void stop() {
        this.server.unbind();
        this.logger.info("SMTP Server stopped");
    }
}
