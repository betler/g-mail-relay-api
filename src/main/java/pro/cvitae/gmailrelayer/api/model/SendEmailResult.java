package pro.cvitae.gmailrelayer.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import java.io.Serializable;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SendEmailResult
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-04-04T22:29:14.146Z[GMT]")
public class SendEmailResult  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("result")
  private String result = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("messageId")
  private String messageId = null;

  @JsonProperty("date")
  private OffsetDateTime date = null;

  public SendEmailResult result(String result) {
    this.result = result;
    return this;
  }

  /**
   * The result of the operation
   * @return result
  **/
  @ApiModelProperty(required = true, value = "The result of the operation")
      @NotNull

    public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public SendEmailResult id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Internally assigned ID. Always set
   * @return id
  **/
  @ApiModelProperty(example = "14", required = true, value = "Internally assigned ID. Always set")
      @NotNull

    public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public SendEmailResult messageId(String messageId) {
    this.messageId = messageId;
    return this;
  }

  /**
   * Message-ID header of the sent message. Empty if the message was delayed.
   * @return messageId
  **/
  @ApiModelProperty(example = "<032d19d1gd15$b9a14470$2si9dm40$@example.com>", value = "Message-ID header of the sent message. Empty if the message was delayed.")
  
    public String getMessageId() {
    return messageId;
  }

  public void setMessageId(String messageId) {
    this.messageId = messageId;
  }

  public SendEmailResult date(OffsetDateTime date) {
    this.date = date;
    return this;
  }

  /**
   * Datetime of the message
   * @return date
  **/
  @ApiModelProperty(example = "2015-03-17T10:30:45Z", value = "Datetime of the message")
  
    @Valid
    public OffsetDateTime getDate() {
    return date;
  }

  public void setDate(OffsetDateTime date) {
    this.date = date;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SendEmailResult sendEmailResult = (SendEmailResult) o;
    return Objects.equals(this.result, sendEmailResult.result) &&
        Objects.equals(this.id, sendEmailResult.id) &&
        Objects.equals(this.messageId, sendEmailResult.messageId) &&
        Objects.equals(this.date, sendEmailResult.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(result, id, messageId, date);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SendEmailResult {\n");
    
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    messageId: ").append(toIndentedString(messageId)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
