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
package pro.cvitae.gmailrelayer.config;

import org.springframework.mail.javamail.JavaMailSender;

import lombok.Getter;
import lombok.Setter;

public class SendingConfiguration {

    @Getter
    @Setter
    private JavaMailSender mailSender;

    @Getter
    @Setter
    private DefaultConfigItem configItem;

    public SendingConfiguration(final JavaMailSender mailSender, final DefaultConfigItem configItem) {
        this.mailSender = mailSender;
        this.configItem = configItem;
    }
}
