package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.model.Attachment;
import io.swagger.model.Header;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * The email message to be sent.
 */
@ApiModel(description = "The email message to be sent.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-04-04T22:29:14.146Z[GMT]")
public class EmailMessage  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("from")
  private String from = null;

  @JsonProperty("replyTo")
  private String replyTo = null;

  @JsonProperty("to")
  @Valid
  private List<String> to = new ArrayList<>();

  @JsonProperty("cc")
  @Valid
  private List<String> cc = null;

  @JsonProperty("bcc")
  @Valid
  private List<String> bcc = null;

  @JsonProperty("subject")
  private String subject = null;

  @JsonProperty("body")
  private String body = null;

  @JsonProperty("textFormat")
  private String textFormat = null;

  @JsonProperty("textEncoding")
  private String textEncoding = null;

  @JsonProperty("priority")
  private BigDecimal priority = null;

  @JsonProperty("notBefore")
  private OffsetDateTime notBefore = null;

  @JsonProperty("attachments")
  @Valid
  private List<Attachment> attachments = null;

  @JsonProperty("headers")
  @Valid
  private List<Header> headers = null;

  public EmailMessage from(String from) {
    this.from = from;
    return this;
  }

  /**
   * Set \"from\" adress. This may be ignored by other configurations which may override the \"from\" address.
   * @return from
  **/
  @ApiModelProperty(example = "Aunt Doe <aunt.doe@example.com>", value = "Set \"from\" adress. This may be ignored by other configurations which may override the \"from\" address.")
  
    public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public EmailMessage replyTo(String replyTo) {
    this.replyTo = replyTo;
    return this;
  }

  /**
   * Optionally set \"replyTo\" address
   * @return replyTo
  **/
  @ApiModelProperty(example = "Uncle Doe <uncle.doe@example.com>", value = "Optionally set \"replyTo\" address")
  
    public String getReplyTo() {
    return replyTo;
  }

  public void setReplyTo(String replyTo) {
    this.replyTo = replyTo;
  }

  public EmailMessage to(List<String> to) {
    this.to = to;
    return this;
  }

  public EmailMessage addToItem(String toItem) {
    this.to.add(toItem);
    return this;
  }

  /**
   * Recipients of the message
   * @return to
  **/
  @ApiModelProperty(required = true, value = "Recipients of the message")
      @NotNull

    public List<String> getTo() {
    return to;
  }

  public void setTo(List<String> to) {
    this.to = to;
  }

  public EmailMessage cc(List<String> cc) {
    this.cc = cc;
    return this;
  }

  public EmailMessage addCcItem(String ccItem) {
    if (this.cc == null) {
      this.cc = new ArrayList<>();
    }
    this.cc.add(ccItem);
    return this;
  }

  /**
   * Carbon copy recipients
   * @return cc
  **/
  @ApiModelProperty(value = "Carbon copy recipients")
  
    public List<String> getCc() {
    return cc;
  }

  public void setCc(List<String> cc) {
    this.cc = cc;
  }

  public EmailMessage bcc(List<String> bcc) {
    this.bcc = bcc;
    return this;
  }

  public EmailMessage addBccItem(String bccItem) {
    if (this.bcc == null) {
      this.bcc = new ArrayList<>();
    }
    this.bcc.add(bccItem);
    return this;
  }

  /**
   * Blind copy recipients
   * @return bcc
  **/
  @ApiModelProperty(value = "Blind copy recipients")
  
    public List<String> getBcc() {
    return bcc;
  }

  public void setBcc(List<String> bcc) {
    this.bcc = bcc;
  }

  public EmailMessage subject(String subject) {
    this.subject = subject;
    return this;
  }

  /**
   * Subject of the message. Maximum length is 255, not only because that is the max lenght of a subject in MS Outlook, but because... hey, who wants to read an email with such a long subject, anyway? Not me.
   * @return subject
  **/
  @ApiModelProperty(example = "Hey John!", value = "Subject of the message. Maximum length is 255, not only because that is the max lenght of a subject in MS Outlook, but because... hey, who wants to read an email with such a long subject, anyway? Not me.")
  
  @Size(max=255)   public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public EmailMessage body(String body) {
    this.body = body;
    return this;
  }

  /**
   * Body of the message. Can be text or html. Format must be specified with the textFormat field, and encoding must be specified with the textEncoding field.
   * @return body
  **/
  @ApiModelProperty(example = "<h1>Attention to this</h1><p>John, a nigerian prince wants to do business with you, lucky man!</p>", required = true, value = "Body of the message. Can be text or html. Format must be specified with the textFormat field, and encoding must be specified with the textEncoding field.")
      @NotNull

  @Size(max=50000)   public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public EmailMessage textFormat(String textFormat) {
    this.textFormat = textFormat;
    return this;
  }

  /**
   * Format of the body message: plain text or html
   * @return textFormat
  **/
  @ApiModelProperty(required = true, value = "Format of the body message: plain text or html")
      @NotNull

    public String getTextFormat() {
    return textFormat;
  }

  public void setTextFormat(String textFormat) {
    this.textFormat = textFormat;
  }

  public EmailMessage textEncoding(String textEncoding) {
    this.textEncoding = textEncoding;
    return this;
  }

  /**
   * Encoding of the message body.
   * @return textEncoding
  **/
  @ApiModelProperty(example = "UTF-8", required = true, value = "Encoding of the message body.")
      @NotNull

    public String getTextEncoding() {
    return textEncoding;
  }

  public void setTextEncoding(String textEncoding) {
    this.textEncoding = textEncoding;
  }

  public EmailMessage priority(BigDecimal priority) {
    this.priority = priority;
    return this;
  }

  /**
   * Set priority of the message. X-Priority header is set with the value
   * minimum: 1
   * maximum: 5
   * @return priority
  **/
  @ApiModelProperty(example = "1", value = "Set priority of the message. X-Priority header is set with the value")
  
    @Valid
  @DecimalMin("1") @DecimalMax("5")   public BigDecimal getPriority() {
    return priority;
  }

  public void setPriority(BigDecimal priority) {
    this.priority = priority;
  }

  public EmailMessage notBefore(OffsetDateTime notBefore) {
    this.notBefore = notBefore;
    return this;
  }

  /**
   * Optionally delay message delivery until the time specified, as defined by date-time - RFC3339 (http://xml2rfc.ietf.org/public/rfc/html/rfc3339.html#anchor14). It is not guaranteed that the email will be sent exactly at this time, but at the first scheduled delivery time after this time
   * @return notBefore
  **/
  @ApiModelProperty(example = "2015-03-17T10:30:45Z", value = "Optionally delay message delivery until the time specified, as defined by date-time - RFC3339 (http://xml2rfc.ietf.org/public/rfc/html/rfc3339.html#anchor14). It is not guaranteed that the email will be sent exactly at this time, but at the first scheduled delivery time after this time")
  
    @Valid
    public OffsetDateTime getNotBefore() {
    return notBefore;
  }

  public void setNotBefore(OffsetDateTime notBefore) {
    this.notBefore = notBefore;
  }

  public EmailMessage attachments(List<Attachment> attachments) {
    this.attachments = attachments;
    return this;
  }

  public EmailMessage addAttachmentsItem(Attachment attachmentsItem) {
    if (this.attachments == null) {
      this.attachments = new ArrayList<>();
    }
    this.attachments.add(attachmentsItem);
    return this;
  }

  /**
   * List of the message attachments
   * @return attachments
  **/
  @ApiModelProperty(value = "List of the message attachments")
      @Valid
    public List<Attachment> getAttachments() {
    return attachments;
  }

  public void setAttachments(List<Attachment> attachments) {
    this.attachments = attachments;
  }

  public EmailMessage headers(List<Header> headers) {
    this.headers = headers;
    return this;
  }

  public EmailMessage addHeadersItem(Header headersItem) {
    if (this.headers == null) {
      this.headers = new ArrayList<>();
    }
    this.headers.add(headersItem);
    return this;
  }

  /**
   * List of the headers that should be added to the email
   * @return headers
  **/
  @ApiModelProperty(value = "List of the headers that should be added to the email")
      @Valid
    public List<Header> getHeaders() {
    return headers;
  }

  public void setHeaders(List<Header> headers) {
    this.headers = headers;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmailMessage emailMessage = (EmailMessage) o;
    return Objects.equals(this.from, emailMessage.from) &&
        Objects.equals(this.replyTo, emailMessage.replyTo) &&
        Objects.equals(this.to, emailMessage.to) &&
        Objects.equals(this.cc, emailMessage.cc) &&
        Objects.equals(this.bcc, emailMessage.bcc) &&
        Objects.equals(this.subject, emailMessage.subject) &&
        Objects.equals(this.body, emailMessage.body) &&
        Objects.equals(this.textFormat, emailMessage.textFormat) &&
        Objects.equals(this.textEncoding, emailMessage.textEncoding) &&
        Objects.equals(this.priority, emailMessage.priority) &&
        Objects.equals(this.notBefore, emailMessage.notBefore) &&
        Objects.equals(this.attachments, emailMessage.attachments) &&
        Objects.equals(this.headers, emailMessage.headers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, replyTo, to, cc, bcc, subject, body, textFormat, textEncoding, priority, notBefore, attachments, headers);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EmailMessage {\n");
    
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    replyTo: ").append(toIndentedString(replyTo)).append("\n");
    sb.append("    to: ").append(toIndentedString(to)).append("\n");
    sb.append("    cc: ").append(toIndentedString(cc)).append("\n");
    sb.append("    bcc: ").append(toIndentedString(bcc)).append("\n");
    sb.append("    subject: ").append(toIndentedString(subject)).append("\n");
    sb.append("    body: ").append(toIndentedString(body)).append("\n");
    sb.append("    textFormat: ").append(toIndentedString(textFormat)).append("\n");
    sb.append("    textEncoding: ").append(toIndentedString(textEncoding)).append("\n");
    sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
    sb.append("    notBefore: ").append(toIndentedString(notBefore)).append("\n");
    sb.append("    attachments: ").append(toIndentedString(attachments)).append("\n");
    sb.append("    headers: ").append(toIndentedString(headers)).append("\n");
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
