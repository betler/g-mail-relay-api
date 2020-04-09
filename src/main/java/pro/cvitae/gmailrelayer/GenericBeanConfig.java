package pro.cvitae.gmailrelayer;

import java.util.Locale;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

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

}
