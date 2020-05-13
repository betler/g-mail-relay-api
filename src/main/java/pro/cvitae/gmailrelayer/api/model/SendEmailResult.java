/**
 * g-mail-relayer smtp mail relayer and API for sending emails
 * Copyright (C) 2020  https://github.com/betler
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package pro.cvitae.gmailrelayer.api.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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

    @JsonProperty("status")
    private EmailStatus status = null;

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("messageId")
    private String messageId = null;

    @JsonProperty("date")
    private OffsetDateTime date = null;

    /**
     * Creates an instance initialized with the current time
     *
     * @return
     */
    public static SendEmailResult getInstance() {
        final SendEmailResult instance = new SendEmailResult();
        instance.setDate(OffsetDateTime.now(ZoneOffset.UTC));
        return instance;
    }

    /**
     * The status of the message
     *
     * @return status
     **/
    @NotNull
    @ApiModelProperty(required = true, value = "The status of the message")
    public EmailStatus getStatus() {
        return this.status;
    }

    public void setStatus(final EmailStatus status) {
        this.status = status;
    }

    /**
     * Internally assigned ID. Always set
     *
     * @return id
     **/
    @NotNull
    @ApiModelProperty(example = "14", required = true, value = "Internally assigned ID. Always set")
    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
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

    /**
     * Datetime of the message
     *
     * @return date
     **/
    @Valid
    @ApiModelProperty(example = "2015-03-17T10:30:45Z", value = "Datetime of the message")
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
        return Objects.equals(this.status, sendEmailResult.status) && Objects.equals(this.id, sendEmailResult.id)
                && Objects.equals(this.messageId, sendEmailResult.messageId)
                && Objects.equals(this.date, sendEmailResult.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.status, this.id, this.messageId, this.date);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class SendEmailResult {\n");

        sb.append("    result: ").append(this.toIndentedString(this.status)).append("\n");
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
