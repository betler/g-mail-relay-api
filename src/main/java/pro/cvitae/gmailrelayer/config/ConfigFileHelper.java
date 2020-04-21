package pro.cvitae.gmailrelayer.config;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang3.Validate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.google.common.base.Strings;

public class ConfigFileHelper {

    private final ConfigFile configFile;

    public ConfigFileHelper(final ConfigFile configFile) {
        this.configFile = configFile;
        this.validateConfig(this.configFile);
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

    private boolean validateConfig(final ConfigFile configFile) {

        // Validate default smtp
        final DefaultConfigItem smtpDefault = configFile.getSmtpDefault();
        Validate.notNull(smtpDefault, "You must define a default smtp configuration");
        this.validateDefaultConfigItem(smtpDefault);

        // Validate default smtp
        final DefaultConfigItem apiDefault = configFile.getApiDefault();
        Validate.notNull(apiDefault, "You must define a default api configuration");
        this.validateDefaultConfigItem(apiDefault);

        // Validate api config
        // TODO Duplicated code
        // Check if any has forFrom and forApplicationId null
        if (configFile.getApiConfig().stream()
                .filter(i -> Strings.isNullOrEmpty(i.getForFrom()) && Strings.isNullOrEmpty(i.getForApplicationId()))
                .count() > 0) {
            throw new IllegalArgumentException("Either forFrom or forApplicationId must be set in api config");
        }

        // Check if any has forFrom and forApplicationId not null
        if (configFile.getApiConfig().stream()
                .filter(i -> !Strings.isNullOrEmpty(i.getForFrom()) && !Strings.isNullOrEmpty(i.getForApplicationId()))
                .count() > 0) {
            throw new IllegalArgumentException("forFrom and forApplicationId cannot be set together");
        }

        // Check if forFrom is duplicated, only for those with applicationId null
        final List<ConfigItem> filteredConfigItems = configFile.getApiConfig().stream()
                .filter(i -> i.getForApplicationId() == null).collect(Collectors.toList());
        final TreeSet<String> duplicateStringChecker = new TreeSet<>();
        for (final ConfigItem i : filteredConfigItems) {
            if (!duplicateStringChecker.add(i.getForFrom())) {
                throw new IllegalArgumentException(i.getForFrom() + " is duplicated in api config");
            }
        }

        // Check if there are duplicates of applicationId and messageType
        final HashMap<String, String> helperMap = new HashMap<>();
        for (final ConfigItem config : configFile.getApiConfig()) {
            // I checked before for from + application redundant configurations
            if (!Strings.isNullOrEmpty(config.getForApplicationId())) {
                // First check if exists. Cannot check null as null is a valid value
                if (helperMap.containsKey(config.getForApplicationId())) {
                    // Check if duplicated
                    final String messageType = helperMap.get(config.getForApplicationId());
                    if (messageType == null && config.getForMessageType() == null
                            || messageType != null && messageType.equals(config.getForMessageType())) {
                        // Nok
                        throw new IllegalArgumentException(
                                "forApplicationId and forMessageType are duplicated in api config: "
                                        + config.getForApplicationId() + ":" + config.getForMessageType());
                    }
                }

                helperMap.put(config.getForApplicationId(), config.getForMessageType());
                // Otherwise, add it
            }
        }

        return true;

    }

    private boolean validateDefaultConfigItem(final DefaultConfigItem item) {
        Validate.notNull(item.getOverrideFrom(), "overridefrom cannot be null");

        // If override, check from
        if (Boolean.TRUE.equals(item.getOverrideFrom())) {
            Validate.notNull(item.getOverrideFromAddress(),
                    "overrideFromAddress cannot be null if overrideFrom is set");
            Validate.isTrue(this.validateEmail(item.getOverrideFromAddress()), "%s is not a valid email address",
                    item.getOverrideFromAddress());
        }

        // Validate auth type
        Validate.notNull(item.getAuthType(), "authType cannot be null");
        Validate.matchesPattern(item.getAuthType(), "^(USERPASS|NTLM)$");

        // Validate domain if NTLM
        if (item.getAuthType().equals("NTLM")) {
            Validate.notBlank(item.getDomain(), "NTLM authentication needs a domain to be set");
        }

        // Validate username and password
        Validate.notBlank(item.getUsername(), "username cannot be null");
        Validate.notBlank(item.getPassword(), "password cannot be null");

        // Validate host:port
        Validate.notBlank(item.getHost(), "host cannot be empty");
        Validate.notNull(item.getPort(), "port cannot be empty");
        Validate.inclusiveBetween(1, 65535, item.getPort(), "Port %s is not a valid number between 1 and 65535",
                item.getPort());

        // TODO Validate starttls
        Validate.notNull(item.getStarttls(), "starttls cannot be null");

        return true;
    }

    private boolean validateEmail(final String email) {
        InternetAddress[] parsed;
        try {
            parsed = InternetAddress.parse(email);
        } catch (final AddressException e) {
            return false;
        }
        return parsed.length == 1;

    }
}
