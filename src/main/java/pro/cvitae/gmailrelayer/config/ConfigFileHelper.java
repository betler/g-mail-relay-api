package pro.cvitae.gmailrelayer.config;

import java.util.Optional;
import java.util.Properties;

import javax.cache.annotation.CacheResult;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.google.common.base.Strings;

public class ConfigFileHelper {

    private final ConfigFile configFile;

    public ConfigFileHelper(final ConfigFile configFile) {
        this.configFile = configFile;
    }

    @CacheResult(cacheName = "mailConfigCache")
    public JavaMailSender senderForSmtp(final String from, final String applicationId, final String messageType) {

        Optional<ConfigItem> itemOpt;
        if (!Strings.isNullOrEmpty(from)) {
            // If there is a 'from', use it
            itemOpt = this.configFile.getSmtpConfig().stream().filter(c -> c.getForFrom().equals("from")).findFirst();
        } else {
            // File is validated on startup, so there must be at least an 'applicationId'
            if (!Strings.isNullOrEmpty(messageType)) {
                // appID + messageType
                itemOpt = this.configFile.getSmtpConfig().stream().filter(
                        c -> c.getForApplicationId().equals(applicationId) && c.getForMessageType().equals(messageType))
                        .findFirst();
            } else {
                // only appID
                itemOpt = this.configFile.getSmtpConfig().stream()
                        .filter(c -> c.getForApplicationId().equals(applicationId)).findFirst();
            }

        }

        return this.getMailSender(itemOpt.isPresent() ? itemOpt.get() : this.configFile.getSmtpDefault());
    }

    @CacheResult(cacheName = "mailConfigCache")
    public JavaMailSender senderForApi(final String from, final String applicationId, final String messageType) {

        Optional<ConfigItem> itemOpt;
        if (!Strings.isNullOrEmpty(from)) {
            // If there is a 'from', use it
            itemOpt = this.configFile.getApiConfig().stream().filter(c -> c.getForFrom().equals("from")).findFirst();
        } else {
            // File is validated on startup, so there must be at least an 'applicationId'
            if (!Strings.isNullOrEmpty(messageType)) {
                // appID + messageType
                itemOpt = this.configFile.getApiConfig().stream().filter(
                        c -> c.getForApplicationId().equals(applicationId) && c.getForMessageType().equals(messageType))
                        .findFirst();
            } else {
                // only appID
                itemOpt = this.configFile.getApiConfig().stream()
                        .filter(c -> c.getForApplicationId().equals(applicationId)).findFirst();
            }

        }

        return this.getMailSender(itemOpt.isPresent() ? itemOpt.get() : this.configFile.getApiDefault());
    }

    private JavaMailSender getMailSender(final DefaultConfigItem item) {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(item.getHost());
        mailSender.setPort(item.getPort());

        mailSender.setUsername(item.getUsername());
        mailSender.setPassword(item.getPassword());

        final Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", item.getStarttls());
        props.put("mail.debug", "false");

        return mailSender;

    }

}
