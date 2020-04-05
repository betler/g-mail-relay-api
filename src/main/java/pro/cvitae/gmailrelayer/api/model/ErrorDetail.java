package pro.cvitae.gmailrelayer.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ErrorDetail
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-04-04T22:29:14.146Z[GMT]")
public class ErrorDetail  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("code")
  private String code = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("params")
  @Valid
  private List<String> params = null;

  public ErrorDetail code(String code) {
    this.code = code;
    return this;
  }

  /**
   * Machine-readable error code
   * @return code
  **/
  @ApiModelProperty(example = "mail.send.error.general", value = "Machine-readable error code")
  
    public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public ErrorDetail description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Longer human-readable message
   * @return description
  **/
  @ApiModelProperty(example = "Could not connect to host", value = "Longer human-readable message")
  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ErrorDetail params(List<String> params) {
    this.params = params;
    return this;
  }

  public ErrorDetail addParamsItem(String paramsItem) {
    if (this.params == null) {
      this.params = new ArrayList<>();
    }
    this.params.add(paramsItem);
    return this;
  }

  /**
   * List of values to complete the error code. Fully dependant on the error code
   * @return params
  **/
  @ApiModelProperty(value = "List of values to complete the error code. Fully dependant on the error code")
  
    public List<String> getParams() {
    return params;
  }

  public void setParams(List<String> params) {
    this.params = params;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorDetail errorDetail = (ErrorDetail) o;
    return Objects.equals(this.code, errorDetail.code) &&
        Objects.equals(this.description, errorDetail.description) &&
        Objects.equals(this.params, errorDetail.params);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, description, params);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorDetail {\n");
    
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    params: ").append(toIndentedString(params)).append("\n");
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
