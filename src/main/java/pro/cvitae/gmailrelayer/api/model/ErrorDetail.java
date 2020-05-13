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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * ErrorDetail
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-04-04T22:29:14.146Z[GMT]")
public class ErrorDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("code")
    private String code = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("params")
    @Valid
    private List<String> params = null;

    public ErrorDetail code(final String code) {
        this.code = code;
        return this;
    }

    /**
     * Machine-readable error code
     *
     * @return code
     **/
    @ApiModelProperty(example = "mail.send.error.general", value = "Machine-readable error code")
    public String getCode() {
        return this.code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public ErrorDetail description(final String description) {
        this.description = description;
        return this;
    }

    /**
     * Longer human-readable message
     *
     * @return description
     **/
    @ApiModelProperty(example = "Could not connect to host", value = "Longer human-readable message")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public ErrorDetail params(final List<String> params) {
        this.params = params;
        return this;
    }

    public ErrorDetail addParamsItem(final String paramsItem) {
        if (this.params == null) {
            this.params = new ArrayList<>();
        }
        this.params.add(paramsItem);
        return this;
    }

    /**
     * List of values to complete the error code. Fully dependant on the error code
     *
     * @return params
     **/
    @ApiModelProperty(value = "List of values to complete the error code. Fully dependant on the error code")
    public List<String> getParams() {
        return this.params;
    }

    public void setParams(final List<String> params) {
        this.params = params;
    }

    @Override
    public boolean equals(final java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ErrorDetail errorDetail = (ErrorDetail) o;
        return Objects.equals(this.code, errorDetail.code) && Objects.equals(this.description, errorDetail.description)
                && Objects.equals(this.params, errorDetail.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.code, this.description, this.params);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class ErrorDetail {\n");

        sb.append("    code: ").append(this.toIndentedString(this.code)).append("\n");
        sb.append("    description: ").append(this.toIndentedString(this.description)).append("\n");
        sb.append("    params: ").append(this.toIndentedString(this.params)).append("\n");
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
