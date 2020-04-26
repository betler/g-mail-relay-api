package pro.cvitae.gmailrelayer.config;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.apache.commons.lang3.Validate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.google.common.base.Strings;

import pro.cvitae.gmailrelayer.api.model.SendingType;

/**
 * Helper class to manage the application's {@link ConfigFile}. Should be used
 * as singleton, although each call to the constructor will return a new
 * instance. An application with several {@link ConfigFileHelper} could be
 * possible, but only one file is read on startup and one file should give
 * enought flexibility to cover all cases. If you found any that does not, ask
 * for it in.
 *
 * @author mikel
 *
 */
public class ConfigFileHelper {

    private final ConfigFile configFile;

    /**
     * Public constructor. <code>configFile</code> cannot be null. The configuration
     * is validated. See project documentation for further reference.
     *
     * @param configFile
     * @throws IllegalArgumentException if <code>configFile</code> is null or if
     *                                  validation fails.
     */
    public ConfigFileHelper(final ConfigFile configFile) {
        Validate.notNull(configFile, "configFile cannot be null");
        this.configFile = configFile;
        this.validateConfigFile(this.configFile);
    }

    /**
     * <p>
     * Retrieves a {@link SendingConfiguration} selected for the given parameters.
     * <code>applicationId</code> and <code>messageType</code> parameters take
     * precedence over <code>from</code>
     * <ol>
     * <li>If an <code>applicationId</code> is set, it searchs in the configuration
     * for a corresponding <code>applicationId</code> and <code>messageType</code>
     * configuration. If found, it is returned.</li>
     * <li>If not found, it searchs for a corresponding <code>applicationId</code>
     * configuration. If found, it is returned.</li>
     * <li>If not found, it searchs for a corresponding <code>from</code>
     * configuration. If found, it is returned.</li>
     * <li>As a fallback, the default configuration for the type specified
     * (smtp/api) is returned.</li>
     * </ol>
     * </p>
     * <p>
     * Although some parameter combinations are invalid (i.e. <code>from</code> and
     * <code>applicationId</code> should not be set at same time) no check is done.
     * This is made to avoid exceptions during execution. <strong>This could lead to
     * an undesired condition by sending mails to a default configuration when a
     * specific configuration is not set in config file.</strong>
     * </p>
     * <p>
     * Code could be optimized with a {@link Map}, but results are cached so no
     * effort in this one by the moment.
     * </p>
     *
     * @param sendingType   for choosing the specific and default configuration.
     *                      Cannot be null or {@link IllegalArgumentException} will
     *                      be thrown
     * @param from
     * @param applicationId
     * @param messageType
     * @return
     */
    @Cacheable(value = "mailConfigCache", key = "'senderForSmtp#' + #sendingType + '#' + #from + '#' + #applicationId + '#' + #messageType")
    public SendingConfiguration senderFor(final SendingType sendingType, final String from, final String applicationId,
            final String messageType) {

        Validate.notNull(sendingType, "sendingType cannot be null");

        // Select the default config for the method
        final DefaultConfigItem defaultConfigItem = sendingType.equals(SendingType.API)
                ? this.configFile.getApiDefault()
                : this.configFile.getSmtpDefault();

        // Select the config list for the method
        final List<ConfigItem> configItems = sendingType.equals(SendingType.API) ? this.configFile.getApiConfig()
                : this.configFile.getSmtpConfig();

        Optional<ConfigItem> itemOpt = Optional.empty();
        if (!Strings.isNullOrEmpty(applicationId)) {
            // ApplicationId takes priority over 'from'
            if (!Strings.isNullOrEmpty(messageType)) {
                // appID + messageType
                itemOpt = configItems.stream()
                        .filter(c -> c.getForApplicationId() != null && c.getForApplicationId().equals(applicationId)
                                && c.getForMessageType() != null && c.getForMessageType().equals(messageType))
                        .findFirst();
            }

            // Fallback if not applicationId + messageType is found
            if (!itemOpt.isPresent()) {
                // only appID
                itemOpt = configItems.stream().filter(c -> c.getForApplicationId().equals(applicationId)).findFirst();
            }
        }

        // if still not configuration is found, try with 'from' address
        if (!itemOpt.isPresent()) {
            itemOpt = configItems.stream().filter(c -> c.getForFrom() != null && c.getForFrom().equals(from))
                    .findFirst();
        }

        final DefaultConfigItem config = itemOpt.isPresent() ? itemOpt.get() : defaultConfigItem;
        return new SendingConfiguration(this.getMailSender(config), config);
    }

    /**
     * Returns a new {@link JavaMailSender} built with the given configuration.
     *
     * @param item
     * @return
     */
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

    private boolean validateConfigFile(final ConfigFile configFile) {

        // Validate default smtp
        final DefaultConfigItem smtpDefault = configFile.getSmtpDefault();
        Validate.notNull(smtpDefault, "You must define a default smtp configuration");
        this.validateDefaultConfigItem(smtpDefault);

        // Validate default smtp
        final DefaultConfigItem apiDefault = configFile.getApiDefault();
        Validate.notNull(apiDefault, "You must define a default api configuration");
        this.validateDefaultConfigItem(apiDefault);

        // Validate api config
        this.validateConfigItemList(configFile.getApiConfig());

        // Validate smtp config
        this.validateConfigItemList(configFile.getSmtpConfig());

        return true;

    }

    /**
     * Checks if configuration is valid for an api or smtp section. First checks if
     * configurations (forFrom, forApplicationId, forMessageType) are valid and then
     * checks each item.
     *
     * @param configList
     * @return
     */
    private boolean validateConfigItemList(final List<ConfigItem> configList) {
        // Check if any has forFrom and forApplicationId null
        if (configList.stream()
                .filter(i -> Strings.isNullOrEmpty(i.getForFrom()) && Strings.isNullOrEmpty(i.getForApplicationId()))
                .count() > 0) {
            throw new IllegalArgumentException("Either forFrom or forApplicationId must be set in config");
        }

        // Check if any has forFrom and forApplicationId not null
        if (configList.stream()
                .filter(i -> !Strings.isNullOrEmpty(i.getForFrom()) && !Strings.isNullOrEmpty(i.getForApplicationId()))
                .count() > 0) {
            throw new IllegalArgumentException("forFrom and forApplicationId cannot be set together");
        }

        // Check if forFrom is duplicated, only for those with applicationId null
        final List<ConfigItem> filteredConfigItems = configList.stream().filter(i -> i.getForApplicationId() == null)
                .collect(Collectors.toList());
        final TreeSet<String> duplicateStringChecker = new TreeSet<>();
        for (final ConfigItem i : filteredConfigItems) {
            if (!duplicateStringChecker.add(i.getForFrom())) {
                throw new IllegalArgumentException(i.getForFrom() + " is duplicated in config");
            }
        }

        this.checkApplicationIdMessageTypeDuplicates(configList);

        // Everything seems ok, validate each element
        // (https://jira.sonarsource.com/browse/SONARJAVA-1190 ?)
        configList.stream().map(i -> this.validateDefaultConfigItem(i)).count();

        return true;
    }

    /**
     * <p>
     * This method <strong>relies on having checked before that are none
     * configurations with a {@link ConfigItem#getForFrom()} and
     * {@link ConfigItem#getForApplicationId()} set at the same time</strong>, as
     * all configurations with a <code>forApplicationId</code> set are checked
     * whether they have a <code>forFrom</code> set or not.
     * </p>
     * <p>
     * Checks that no configuration of {@link ConfigItem#getForApplicationId()} and
     * {@link ConfigItem#getForMessageType()} is repeated. <code>null</code> is a
     * valid value for <code>messageType</code> so same <code>applicationId</code>
     * can be set with a <code>null</code> and not <code>null</code> value at the
     * same time.
     * </p>
     * <p>
     * Returns if everything's ok, or throws {@link IllegalArgumentException} if
     * anything is wrong, as per {@link Validate} methods.
     * </p>
     *
     * @param configList
     */
    private void checkApplicationIdMessageTypeDuplicates(final List<ConfigItem> configList) {
        // Check if there are duplicates of applicationId and messageType
        final MultiValuedMap<String, String> helperMap = new HashSetValuedHashMap<>();
        for (final ConfigItem config : configList) {
            // Only check if 'forFrom' is not set
            if (!Strings.isNullOrEmpty(config.getForApplicationId())) {
                // First check if exists. Cannot check null return value of put method, as null
                // is a valid value and will confuse between non existant and null value
                if (helperMap.containsKey(config.getForApplicationId())) {
                    // Check if duplicated
                    final Collection<String> messageTypes = helperMap.get(config.getForApplicationId());

                    if (messageTypes.contains(config.getForMessageType())) {
                        // Nok
                        throw new IllegalArgumentException(
                                "forApplicationId and forMessageType are duplicated in config: "
                                        + config.getForApplicationId() + ":" + config.getForMessageType());
                    }
                }

                // If not duplicated, add it
                helperMap.put(config.getForApplicationId(), config.getForMessageType());
            }
        }
    }

    /**
     * Validates all properties of a {@link DefaultConfigItem}. Returns
     * <code>true</code> if everything's ok, or throws
     * {@link IllegalArgumentException} if anything is wrong, as per
     * {@link Validate} methods.
     *
     * @param item
     * @return
     */
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

        Validate.notNull(item.getStarttls(), "starttls cannot be null");

        return true;
    }

    /**
     * Validates if an email is correct. {@link InternetAddress#parse(String)} is
     * used with strict validation turned on.
     *
     * @param email must not be null.
     * @return true or false if the email is correct.
     */
    private boolean validateEmail(final String email) {
        InternetAddress[] parsed;
        try {
            parsed = InternetAddress.parse(email, true);
        } catch (final AddressException e) {
            return false;
        }
        return parsed.length == 1;

    }

    public ConfigFile getConfigFile() {
        return this.configFile;
    }
}
