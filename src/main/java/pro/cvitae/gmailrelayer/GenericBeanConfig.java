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
package pro.cvitae.gmailrelayer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import pro.cvitae.gmailrelayer.config.ConfigFile;
import pro.cvitae.gmailrelayer.config.ConfigFileHelper;

@EnableCaching
@Configuration
public class GenericBeanConfig {

    @Bean
    public LocaleResolver localeResolver() {
        return new FixedLocaleResolver(Locale.ENGLISH);
    }

    @Bean
    public ConfigFile configFile(final ObjectMapper mapper) throws IOException {
        final String configFileProperty = System.getProperty("config.file");
        final File configFile;
        if (configFileProperty != null) {
            configFile = new File(configFileProperty);
        } else {
            // Not set, try to read from resources or jar location
            final ApplicationHome home = new ApplicationHome(GMailRelayerApplication.class);
            // returns the folder where the jar is.
            configFile = new File(home.getDir().getAbsolutePath() + "/config.json");
        }

        Validate.isTrue(configFile.exists(), configFile.getAbsolutePath() + " does not exist");
        final String json = FileUtils.readFileToString(configFile, StandardCharsets.UTF_8);
        return mapper.readValue(json, ConfigFile.class);
    }

    @Bean
    @DependsOn("configFile")
    public ConfigFileHelper configFileHelper(final ConfigFile configFile) {
        return new ConfigFileHelper(configFile);
    }

}
