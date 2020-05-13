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

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.Validate;
import org.apache.james.protocols.api.handler.ProtocolHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pro.cvitae.gmailrelayer.config.ConfigFileHelper;

/**
 * @author betler
 *
 */
@Configuration
public class SmtpServerConfiguration {

    @Value("${relayer.smtp.server.port}")
    private Integer port;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public SmtpServer smtpServer(final MessageReceivedHook messageReceivedHook) {

        Validate.inclusiveBetween(1L, 65535L, this.port);

        final Collection<ProtocolHandler> handlers = new ArrayList<>();
        handlers.add(messageReceivedHook);
        return new SmtpServer(this.port, handlers);
    }

    @Bean
    public MessageReceivedHook messageReceivedHook(final ConfigFileHelper configFileHelper) {
        return new MessageReceivedHook(configFileHelper);
    }

}
