package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Attachment
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-04-04T22:29:14.146Z[GMT]")
public class Attachment  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("cid")
  private String cid = null;

  @JsonProperty("filename")
  private String filename = null;

  @JsonProperty("contentType")
  private String contentType = null;

  @JsonProperty("content")
  private byte[] content = null;

  public Attachment cid(String cid) {
    this.cid = cid;
    return this;
  }

  /**
   * Optional CID identificator for inline attachements. Setting a CID will force the attachment to be inlined. If you do not want the attachment to be inline, do not set the CID field.
   * @return cid
  **/
  @ApiModelProperty(value = "Optional CID identificator for inline attachements. Setting a CID will force the attachment to be inlined. If you do not want the attachment to be inline, do not set the CID field.")
  
  @Size(max=200)   public String getCid() {
    return cid;
  }

  public void setCid(String cid) {
    this.cid = cid;
  }

  public Attachment filename(String filename) {
    this.filename = filename;
    return this;
  }

  /**
   * Filename of the attachment
   * @return filename
  **/
  @ApiModelProperty(example = "photo-album.png", required = true, value = "Filename of the attachment")
      @NotNull

  @Size(max=50)   public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public Attachment contentType(String contentType) {
    this.contentType = contentType;
    return this;
  }

  /**
   * Optional content type
   * @return contentType
  **/
  @ApiModelProperty(example = "image/png", value = "Optional content type")
  
    public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public Attachment content(byte[] content) {
    this.content = content;
    return this;
  }

  /**
   * Base64 attachment
   * @return content
  **/
  @ApiModelProperty(required = true, value = "Base64 attachment")
      @NotNull

    public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Attachment attachment = (Attachment) o;
    return Objects.equals(this.cid, attachment.cid) &&
        Objects.equals(this.filename, attachment.filename) &&
        Objects.equals(this.contentType, attachment.contentType) &&
        Objects.equals(this.content, attachment.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cid, filename, contentType, content);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Attachment {\n");
    
    sb.append("    cid: ").append(toIndentedString(cid)).append("\n");
    sb.append("    filename: ").append(toIndentedString(filename)).append("\n");
    sb.append("    contentType: ").append(toIndentedString(contentType)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
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
