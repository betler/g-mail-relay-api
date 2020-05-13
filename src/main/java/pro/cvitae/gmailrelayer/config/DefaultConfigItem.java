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

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({ "overrideFrom", "overrideFromAddress", "authType", "username", "password", "domain", "host",
        "port", "starttls" })
public class DefaultConfigItem {

    @Getter
    @Setter
    private Boolean overrideFrom;

    @Getter
    @Setter
    private String overrideFromAddress;

    @Getter
    @Setter
    private String authType;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String domain;

    @Getter
    @Setter
    private String host;

    @Getter
    @Setter
    private Integer port;

    @Getter
    @Setter
    private Boolean starttls;
}
