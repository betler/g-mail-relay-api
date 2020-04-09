package pro.cvitae.gmailrelayer.api.model;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Attachment
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-04-04T22:29:14.146Z[GMT]")
public class Attachment implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("cid")
    private String cid = null;

    @JsonProperty("filename")
    private String filename = null;

    @JsonProperty("contentType")
    private String contentType = null;

    @JsonProperty("content")
    private String content = null;

    public Attachment cid(final String cid) {
        this.cid = cid;
        return this;
    }

    /**
     * Optional CID identificator for inline attachements. Setting a CID will force
     * the attachment to be inlined. If you do not want the attachment to be inline,
     * do not set the CID field.
     *
     * @return cid
     **/
    @Size(max = 200)
    @Pattern(regexp = "[\\w\\d\\_\\-\\$\\&\\(\\)\\[\\]]{1,200}")
    @ApiModelProperty(value = "Optional CID identificator for inline attachements. Setting a CID will force the attachment to be inlined. "
            + "If you do not want the attachment to be inline, do not set the CID field. Alphanumeric characters and _-$&()[] are allowed")
    public String getCid() {
        return this.cid;
    }

    public void setCid(final String cid) {
        this.cid = cid;
    }

    public Attachment filename(final String filename) {
        this.filename = filename;
        return this;
    }

    /**
     * Filename of the attachment
     *
     * @return filename
     **/
    @NotNull
    @Size(max = 50)
    @Pattern(regexp = "[\\w\\d\\_\\-\\$\\&\\(\\)\\[\\]\\ ]{1,50}")
    @ApiModelProperty(example = "photo-album.png", required = true, value = "Filename of the attachment.  Alphanumeric characters, space and _-$&()[] are allowed")
    public String getFilename() {
        return this.filename;
    }

    public void setFilename(final String filename) {
        this.filename = filename;
    }

    public Attachment contentType(final String contentType) {
        this.contentType = contentType;
        return this;
    }

    /**
     * Optional content type
     *
     * @return contentType
     **/
    @ApiModelProperty(example = "image/png", value = "Optional content type")
    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }

    public Attachment content(final String content) {
        this.content = content;
        return this;
    }

    /**
     * Base64 attachment
     *
     * @return content
     **/
    @NotNull
    @ApiModelProperty(required = true, value = "Base64 attachment")
    public String getContent() {
        return this.content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    @Override
    public boolean equals(final java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Attachment attachment = (Attachment) o;
        return Objects.equals(this.cid, attachment.cid) && Objects.equals(this.filename, attachment.filename)
                && Objects.equals(this.contentType, attachment.contentType)
                && Objects.equals(this.content, attachment.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.cid, this.filename, this.contentType, this.content);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class Attachment {\n");

        sb.append("    cid: ").append(this.toIndentedString(this.cid)).append("\n");
        sb.append("    filename: ").append(this.toIndentedString(this.filename)).append("\n");
        sb.append("    contentType: ").append(this.toIndentedString(this.contentType)).append("\n");
        sb.append("    content: ").append(this.toIndentedString(this.content)).append("\n");
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
