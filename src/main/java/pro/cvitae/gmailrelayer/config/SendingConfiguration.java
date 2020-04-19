package pro.cvitae.gmailrelayer.config;

import org.springframework.mail.javamail.JavaMailSender;

import lombok.Getter;
import lombok.Setter;

public class SendingConfiguration {

    @Getter
    @Setter
    private JavaMailSender mailSender;

    @Getter
    @Setter
    private DefaultConfigItem configItem;

    public SendingConfiguration(final JavaMailSender mailSender, final DefaultConfigItem configItem) {
        this.mailSender = mailSender;
        this.configItem = configItem;
    }
}
