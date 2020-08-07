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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({ "api-default", "smtp-default", "api", "smtp" })
public class ConfigFile {

    @Getter
    @Setter
    @JsonProperty("api-default")
    private DefaultConfigItem apiDefault;

    @Getter
    @Setter
    @JsonProperty("smtp-default")
    private DefaultConfigItem smtpDefault;

    @Getter
    @Setter
    @JsonProperty("api")
    private List<ConfigItem> apiConfig;

    @Getter
    @Setter
    @JsonProperty("smtp")
    private List<ConfigItem> smtpConfig;

    @Getter
    @Setter
    private boolean mailDebug;

}
