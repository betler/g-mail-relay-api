package pro.cvitae.gmailrelayer.config;

import java.util.Optional;
import java.util.Properties;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.google.common.base.Strings;

public class ConfigFileHelper {

    private final ConfigFile configFile;

    public ConfigFileHelper(final ConfigFile configFile) {
        this.configFile = configFile;
    }

    @Cacheable(value = "mailConfigCache", key = "'senderForSmtp#' + #from + '#' + #applicationId + '#' + #messageType")
    public SendingConfiguration senderForSmtp(final String from, final String applicationId, final String messageType) {

        Optional<ConfigItem> itemOpt = Optional.empty();
        if (!Strings.isNullOrEmpty(applicationId)) {
            // ApplicationId takes priority over 'from'
            if (!Strings.isNullOrEmpty(messageType)) {
                // appID + messageType
                itemOpt = this.configFile.getSmtpConfig().stream()
                        .filter(c -> c.getForApplicationId() != null && c.getForApplicationId().equals(applicationId)
                                && c.getForMessageType() != null && c.getForMessageType().equals(messageType))
                        .findFirst();
            } else {
                // only appID
                itemOpt = this.configFile.getSmtpConfig().stream()
                        .filter(c -> c.getForApplicationId().equals(applicationId)).findFirst();
            }
        }

        // if still not configuration is found, try with 'from' address
        if (!itemOpt.isPresent()) {
            itemOpt = this.configFile.getSmtpConfig().stream()
                    .filter(c -> c.getForFrom() != null && c.getForFrom().equals(from)).findFirst();
        }

        final DefaultConfigItem config = itemOpt.isPresent() ? itemOpt.get() : this.configFile.getSmtpDefault();
        return new SendingConfiguration(this.getMailSender(config), config);
    }

    @Cacheable(value = "mailConfigCache", key = "'senderForApi#' + #from + '#' + #applicationId + '#' + #messageType")
    public SendingConfiguration senderForApi(final String from, final String applicationId, final String messageType) {

        Optional<ConfigItem> itemOpt = Optional.empty();
        if (!Strings.isNullOrEmpty(applicationId)) {
            // ApplicationId takes priority over 'from'
            if (!Strings.isNullOrEmpty(messageType)) {
                // appID + messageType
                itemOpt = this.configFile.getApiConfig().stream()
                        .filter(c -> c.getForApplicationId() != null && c.getForApplicationId().equals(applicationId)
                                && c.getForMessageType() != null && c.getForMessageType().equals(messageType))
                        .findFirst();
            } else {
                // only appID
                itemOpt = this.configFile.getApiConfig().stream()
                        .filter(c -> c.getForApplicationId().equals(applicationId)).findFirst();
            }
        }

        // if still not configuration is found, try with 'from' address
        if (!itemOpt.isPresent()) {
            itemOpt = this.configFile.getApiConfig().stream()
                    .filter(c -> c.getForFrom() != null && c.getForFrom().equals(from)).findFirst();
        }

        final DefaultConfigItem config = itemOpt.isPresent() ? itemOpt.get() : this.configFile.getApiDefault();
        return new SendingConfiguration(this.getMailSender(config), config);
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
        props.put("mail.debug", "true");

        return mailSender;

    }

}
