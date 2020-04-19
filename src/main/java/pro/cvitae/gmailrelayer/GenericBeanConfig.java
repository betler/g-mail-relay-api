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
    public ConfigFileHelper configFileHelper(final ConfigFile configFile) {
        return new ConfigFileHelper(configFile);
    }

}
