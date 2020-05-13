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
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Header
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-04-04T22:29:14.146Z[GMT]")
public class Header implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("value")
    private String value = null;

    public Header name(final String name) {
        this.name = name;
        return this;
    }

    /**
     * Header name
     *
     * @return name
     **/
    @NotNull
    @Size(max = 200)
    @ApiModelProperty(required = true, value = "Header name")
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Header value(final String value) {
        this.value = value;
        return this;
    }

    /**
     * Header value
     *
     * @return value
     **/
    @NotNull
    @Size(max = 2000)
    @ApiModelProperty(required = true, value = "Header value")
    public String getValue() {
        return this.value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public boolean equals(final java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Header header = (Header) o;
        return Objects.equals(this.name, header.name) && Objects.equals(this.value, header.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.value);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class Header {\n");

        sb.append("    name: ").append(this.toIndentedString(this.name)).append("\n");
        sb.append("    value: ").append(this.toIndentedString(this.value)).append("\n");
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
