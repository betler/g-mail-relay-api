package pro.cvitae.gmailrelayer.api.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * SendEmailResult
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-04-04T22:29:14.146Z[GMT]")
public class SendEmailResult implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("result")
    private String result = null;

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("messageId")
    private String messageId = null;

    @JsonProperty("date")
    private OffsetDateTime date = null;

    public SendEmailResult result(final String result) {
        this.result = result;
        return this;
    }

    /**
     * The result of the operation
     *
     * @return result
     **/
    @ApiModelProperty(required = true, value = "The result of the operation")
    @NotNull

    public String getResult() {
        return this.result;
    }

    public void setResult(final String result) {
        this.result = result;
    }

    public SendEmailResult id(final Long id) {
        this.id = id;
        return this;
    }

    /**
     * Internally assigned ID. Always set
     *
     * @return id
     **/
    @ApiModelProperty(example = "14", required = true, value = "Internally assigned ID. Always set")
    @NotNull

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public SendEmailResult messageId(final String messageId) {
        this.messageId = messageId;
        return this;
    }

    /**
     * Message-ID header of the sent message. Empty if the message was delayed.
     *
     * @return messageId
     **/
    @ApiModelProperty(example = "<032d19d1gd15$b9a14470$2si9dm40$@example.com>", value = "Message-ID header of the sent message. Empty if the message was delayed.")

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(final String messageId) {
        this.messageId = messageId;
    }

    public SendEmailResult date(final OffsetDateTime date) {
        this.date = date;
        return this;
    }

    /**
     * Datetime of the message
     *
     * @return date
     **/
    @ApiModelProperty(example = "2015-03-17T10:30:45Z", value = "Datetime of the message")

    @Valid
    public OffsetDateTime getDate() {
        return this.date;
    }

    public void setDate(final OffsetDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(final java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final SendEmailResult sendEmailResult = (SendEmailResult) o;
        return Objects.equals(this.result, sendEmailResult.result) && Objects.equals(this.id, sendEmailResult.id)
                && Objects.equals(this.messageId, sendEmailResult.messageId)
                && Objects.equals(this.date, sendEmailResult.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.result, this.id, this.messageId, this.date);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class SendEmailResult {\n");

        sb.append("    result: ").append(this.toIndentedString(this.result)).append("\n");
        sb.append("    id: ").append(this.toIndentedString(this.id)).append("\n");
        sb.append("    messageId: ").append(this.toIndentedString(this.messageId)).append("\n");
        sb.append("    date: ").append(this.toIndentedString(this.date)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(final java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
