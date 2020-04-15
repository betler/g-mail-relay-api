package pro.cvitae.gmailrelayer.config;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({ "overrideFrom", "overrideFromAddress", "authType", "username", "password", "domain", "host",
        "port", "starttls" })
public class DefaultConfigItem {

    @Getter
    @Setter
    private Boolean overrideFrom;

    @Getter
    @Setter
    private String overrideFromAddress;

    @Getter
    @Setter
    private String authType;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String domain;

    @Getter
    @Setter
    private String host;

    @Getter
    @Setter
    private Integer port;

    @Getter
    @Setter
    private Boolean starttls;
}
