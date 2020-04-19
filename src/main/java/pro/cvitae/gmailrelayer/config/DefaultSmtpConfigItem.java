package pro.cvitae.gmailrelayer.config;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({ "listeningPort", "overrideFrom", "overrideFromAddress", "authType", "username", "password",
        "domain", "host", "port", "starttls" })
public class DefaultSmtpConfigItem extends DefaultConfigItem {

    @Getter
    @Setter
    private Integer listeningPort;
}