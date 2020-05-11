package pro.cvitae.gmailrelayer.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import pro.cvitae.gmailrelayer.api.validator.Email;
import pro.cvitae.gmailrelayer.api.validator.EmailList;
import pro.cvitae.gmailrelayer.api.validator.EmailMessageType;
import pro.cvitae.gmailrelayer.api.validator.Encoding;

/**
 * The email message to be sent.
 */
@Validated
@EmailMessageType
@ApiModel(description = "The email message to be sent.")
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-04-04T22:29:14.146Z[GMT]")
public class EmailMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("applicationId")
    private String applicationId = null;

    @JsonProperty("messageType")
    private String messageType = null;

    @JsonProperty("from")
    private String from = null;

    @JsonProperty("replyTo")
    private String replyTo = null;

    @Valid
    @NotEmpty
    @EmailList
    @JsonProperty("to")
    private List<String> to = new ArrayList<>();

    @Valid
    @JsonProperty("cc")
    private List<String> cc = null;

    @Valid
    @JsonProperty("bcc")
    private List<String> bcc = null;

    @JsonProperty("subject")
    private String subject = null;

    @JsonProperty("body")
    private String body = null;

    @JsonProperty("textFormat")
    private String textFormat = null;

    @JsonProperty("textEncoding")
    private String textEncoding = null;

    @JsonProperty("deliveryType")
    private String deliveryType = null;

    @JsonProperty("priority")
    private BigDecimal priority = null;

    @JsonProperty("notBefore")
    private OffsetDateTime notBefore = null;

    @Valid
    @JsonProperty("attachments")
    private List<Attachment> attachments = null;

    @Valid
    @JsonProperty("headers")
    private List<Header> headers = null;

    public EmailMessage from(final String from) {
        this.from = from;
        return this;
    }

    /**
     * Optional free text to identify the sender of the application. Must be set if
     * a messageType is specified.
     *
     * @return
     */
    @Size(max = 30)
    @ApiModelProperty(example = "TASKSAPP", value = "Optional free text to identify the sender of the application. Must be set if a messageType is specified.")
    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(final String applicationId) {
        this.applicationId = applicationId;
    }

    @Size(max = 30)
    @ApiModelProperty(example = "Password Reminder", value = "Optional free text to identify the specific message type. Cannot be set without an applicationId.")
    public String getMessageType() {
        return this.messageType;
    }

    public void setMessageType(final String messageType) {
        this.messageType = messageType;
    }

    /**
     * Set \"from\" address. This may be ignored by other configurations which may
     * override the \"from\" address.
     *
     * @return from
     **/
    @Email
    @ApiModelProperty(example = "Aunt Doe <aunt.doe@example.com>", value = "Set \"from\" address. This may be ignored by other configurations which may override the \"from\" address.")
    public String getFrom() {
        return this.from;
    }

    public void setFrom(final String from) {
        this.from = from;
    }

    public EmailMessage replyTo(final String replyTo) {
        this.replyTo = replyTo;
        return this;
    }

    /**
     * Optionally set \"replyTo\" address
     *
     * @return replyTo
     **/
    @Email
    @ApiModelProperty(example = "Uncle Doe <uncle.doe@example.com>", value = "Optionally set \"replyTo\" address")
    public String getReplyTo() {
        return this.replyTo;
    }

    public void setReplyTo(final String replyTo) {
        this.replyTo = replyTo;
    }

    public EmailMessage to(final List<String> to) {
        this.to = to;
        return this;
    }

    public EmailMessage addToItem(final String toItem) {
        this.to.add(toItem);
        return this;
    }

    /**
     * Recipients of the message
     *
     * @return to
     **/
    @NotNull
    @EmailList
    @ApiModelProperty(required = true, value = "Recipients of the message")
    public List<String> getTo() {
        return this.to;
    }

    public void setTo(final List<String> to) {
        this.to = to;
    }

    public EmailMessage cc(final List<String> cc) {
        this.cc = cc;
        return this;
    }

    public EmailMessage addCcItem(final String ccItem) {
        if (this.cc == null) {
            this.cc = new ArrayList<>();
        }
        this.cc.add(ccItem);
        return this;
    }

    /**
     * Carbon copy recipients
     *
     * @return cc
     **/
    @EmailList
    @ApiModelProperty(value = "Carbon copy recipients")
    public List<String> getCc() {
        return this.cc;
    }

    public void setCc(final List<String> cc) {
        this.cc = cc;
    }

    public EmailMessage bcc(final List<String> bcc) {
        this.bcc = bcc;
        return this;
    }

    public EmailMessage addBccItem(final String bccItem) {
        if (this.bcc == null) {
            this.bcc = new ArrayList<>();
        }
        this.bcc.add(bccItem);
        return this;
    }

    /**
     * Blind copy recipients
     *
     * @return bcc
     **/
    @EmailList
    @ApiModelProperty(value = "Blind copy recipients")
    public List<String> getBcc() {
        return this.bcc;
    }

    public void setBcc(final List<String> bcc) {
        this.bcc = bcc;
    }

    public EmailMessage subject(final String subject) {
        this.subject = subject;
        return this;
    }

    /**
     * Subject of the message. Maximum length is 255, not only because that is the
     * max lenght of a subject in MS Outlook, but because... hey, who wants to read
     * an email with such a long subject, anyway? Not me.
     *
     * @return subject
     **/
    @Size(max = 255)
    @ApiModelProperty(example = "Hey John!", value = "Subject of the message. Maximum length is 255, not only because that is the max lenght of a subject in MS Outlook, but because... hey, who wants to read an email with such a long subject, anyway? Not me.")
    public String getSubject() {
        return this.subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public EmailMessage body(final String body) {
        this.body = body;
        return this;
    }

    /**
     * Body of the message. Can be text or html. Format must be specified with the
     * textFormat field, and encoding must be specified with the textEncoding field.
     *
     * @return body
     **/
    @NotNull
    @Size(max = 50000)
    @ApiModelProperty(example = "<h1>Attention to this</h1><p>John, a nigerian prince wants to do business with you, lucky man!</p>", required = true, value = "Body of the message. Can be text or html. Format must be specified with the textFormat field, and encoding must be specified with the textEncoding field.")
    public String getBody() {
        return this.body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public EmailMessage textFormat(final String textFormat) {
        this.textFormat = textFormat;
        return this;
    }

    /**
     * Format of the body message: plain text or html
     *
     * @return textFormat
     **/
    @NotNull
    @Pattern(regexp = "(HTML|TEXT)")
    @ApiModelProperty(required = true, value = "Format of the body message: plain text (TEXT) or html (HTML)")
    public String getTextFormat() {
        return this.textFormat;
    }

    public void setTextFormat(final String textFormat) {
        this.textFormat = textFormat;
    }

    public EmailMessage textEncoding(final String textEncoding) {
        this.textEncoding = textEncoding;
        return this;
    }

    /**
     * Encoding of the message body.
     *
     * @return textEncoding
     **/
    @NotNull
    @Encoding
    @ApiModelProperty(example = "UTF-8", required = true, value = "Encoding of the message body.")
    public String getTextEncoding() {
        return this.textEncoding;
    }

    public void setTextEncoding(final String textEncoding) {
        this.textEncoding = textEncoding;
    }

    public EmailMessage priority(final BigDecimal priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Set the delivery type: PRIORITY_SYNC makes a synchronized inmediate sending
     * of the message. The API does not return until the messaged is delivered (or
     * tried to). PRIORITY_ASYNC makes an inmediate background sending. The API
     * returns the ID of the message with QUEUED status but the message is sent
     * inmediately in the background. QUEUE queues the message until the next
     * scheduled batch processing of queued mails
     *
     * @return deliveryType
     **/
    @NotNull
    @Pattern(regexp = "(PRIORITY_SYNC|PRIORITY_ASYNC|QUEUE)")
    @ApiModelProperty(example = "PRIORITY_SYNC", required = true, value = "Set the delivery type: PRIORITY_SYNC makes a synchronized inmediate sending of the message. "
            + "The API does not return until the messaged is delivered (or tried to). PRIORITY_ASYNC makes an inmediate background sending. "
            + "The API returns the ID of the message with QUEUED status but the message is sent inmediately in the background. "
            + "QUEUE queues the message until the next scheduled batch processing of queued mails")
    public String getDeliveryType() {
        return this.deliveryType;
    }

    public void setDeliveryType(final String deliveryType) {
        this.deliveryType = deliveryType;
    }

    /**
     * Set priority of the message. X-Priority header is set with the value minimum:
     * 1 maximum: 5
     *
     * @return priority
     **/
    @Valid
    @DecimalMin("1")
    @DecimalMax("5")
    @ApiModelProperty(example = "1", value = "Set priority of the message. X-Priority header is set with the value")
    public BigDecimal getPriority() {
        return this.priority;
    }

    public void setPriority(final BigDecimal priority) {
        this.priority = priority;
    }

    public EmailMessage notBefore(final OffsetDateTime notBefore) {
        this.notBefore = notBefore;
        return this;
    }

    /**
     * Optionally delay message delivery until the time specified, as defined by
     * date-time - RFC3339
     * (http://xml2rfc.ietf.org/public/rfc/html/rfc3339.html#anchor14). It is not
     * guaranteed that the email will be sent exactly at this time, but at the first
     * scheduled delivery time after this time. This option is ignored if
     * deliveryType is set to other than QUEUE.
     *
     * @return notBefore
     **/
    @Valid
    @ApiModelProperty(example = "2015-03-17T10:30:45Z", value = "Optionally delay message delivery until the time specified, as defined by date-time - RFC3339 (http://xml2rfc.ietf.org/public/rfc/html/rfc3339.html#anchor14). It is not guaranteed that the email will be sent exactly at this time, but at the first scheduled delivery time after this time. This option is ignored if deliveryType is set to other than QUEUE.")
    public OffsetDateTime getNotBefore() {
        return this.notBefore;
    }

    public void setNotBefore(final OffsetDateTime notBefore) {
        this.notBefore = notBefore;
    }

    public EmailMessage attachments(final List<Attachment> attachments) {
        this.attachments = attachments;
        return this;
    }

    public EmailMessage addAttachmentsItem(final Attachment attachmentsItem) {
        if (this.attachments == null) {
            this.attachments = new ArrayList<>();
        }
        this.attachments.add(attachmentsItem);
        return this;
    }

    /**
     * List of the message attachments
     *
     * @return attachments
     **/
    @Valid
    @ApiModelProperty(value = "List of the message attachments")
    public List<Attachment> getAttachments() {
        return this.attachments;
    }

    public void setAttachments(final List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public EmailMessage headers(final List<Header> headers) {
        this.headers = headers;
        return this;
    }

    public EmailMessage addHeadersItem(final Header headersItem) {
        if (this.headers == null) {
            this.headers = new ArrayList<>();
        }
        this.headers.add(headersItem);
        return this;
    }

    /**
     * List of the headers that should be added to the email
     *
     * @return headers
     **/
    @Valid
    @ApiModelProperty(value = "List of the headers that should be added to the email")
    public List<Header> getHeaders() {
        return this.headers;
    }

    public void setHeaders(final List<Header> headers) {
        this.headers = headers;
    }

    /**
     * Searches and optionally returns the "Message-ID" header.
     *
     * @return
     */
    public Optional<Header> getMessageIdHeader() {
        return this.getHeaders().stream().filter(h -> h.getName().equalsIgnoreCase("Message-ID")).findFirst();
    }

    /**
     * Two messages could have the same fields and be different emails. Only returns
     * <code>true</code> if two emails share references or have the same
     * 'Message-Id' header.
     */
    @Override
    public boolean equals(final java.lang.Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        final EmailMessage emailMessage = (EmailMessage) o;

        // Check header
        final Optional<Header> messageIdHeader = this.getMessageIdHeader();
        if (messageIdHeader.isPresent()) {
            // Check second header
            final Optional<Header> messageIdHeader2 = emailMessage.getMessageIdHeader();

            if (messageIdHeader2.isPresent()) {
                return messageIdHeader.get().getValue().equals(messageIdHeader2.get().getValue());
            } else {
                return false;
            }
        }

        // Can't compare message id, treat as different
        return false;

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.from, this.replyTo, this.to, this.cc, this.bcc, this.subject, this.body,
                this.textFormat, this.textEncoding, this.priority, this.notBefore, this.attachments, this.headers);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class EmailMessage {\n");

        sb.append("    from: ").append(this.toIndentedString(this.from)).append("\n");
        sb.append("    replyTo: ").append(this.toIndentedString(this.replyTo)).append("\n");
        sb.append("    to: ").append(this.toIndentedString(this.to)).append("\n");
        sb.append("    cc: ").append(this.toIndentedString(this.cc)).append("\n");
        sb.append("    bcc: ").append(this.toIndentedString(this.bcc)).append("\n");
        sb.append("    subject: ").append(this.toIndentedString(this.subject)).append("\n");
        sb.append("    body: ").append(this.toIndentedString(this.body)).append("\n");
        sb.append("    textFormat: ").append(this.toIndentedString(this.textFormat)).append("\n");
        sb.append("    textEncoding: ").append(this.toIndentedString(this.textEncoding)).append("\n");
        sb.append("    deliveryType: ").append(this.toIndentedString(this.deliveryType)).append("\n");
        sb.append("    priority: ").append(this.toIndentedString(this.priority)).append("\n");
        sb.append("    notBefore: ").append(this.toIndentedString(this.notBefore)).append("\n");
        sb.append("    attachments: ").append(this.toIndentedString(this.attachments)).append("\n");
        sb.append("    headers: ").append(this.toIndentedString(this.headers)).append("\n");
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
