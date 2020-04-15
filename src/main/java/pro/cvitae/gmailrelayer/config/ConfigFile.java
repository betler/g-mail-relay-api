package pro.cvitae.gmailrelayer.config;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({ "api-default", "smtp-default", "api", "smtp" })
public class ConfigFile {

    @Getter
    @Setter
    @JsonProperty("api-default")
    private DefaultConfigItem apiDefault;

    @Getter
    @Setter
    @JsonProperty("smtp-default")
    private DefaultConfigItem smtpDefault;

    @Getter
    @Setter
    @JsonProperty("api")
    private List<ConfigItem> apiConfig;

    @Getter
    @Setter
    @JsonProperty("smtp")
    private List<ConfigItem> smtpConfig;

}
