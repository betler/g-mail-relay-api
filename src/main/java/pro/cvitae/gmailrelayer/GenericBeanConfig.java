package pro.cvitae.gmailrelayer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import pro.cvitae.gmailrelayer.config.ConfigFile;

@Configuration
public class GenericBeanConfig {

    @Bean
    public LocaleResolver localeResolver() {
        return new FixedLocaleResolver(Locale.ENGLISH);
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("");
        mailSender.setPort(587);

        mailSender.setUsername("");
        mailSender.setPassword("");

        final Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
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

}
