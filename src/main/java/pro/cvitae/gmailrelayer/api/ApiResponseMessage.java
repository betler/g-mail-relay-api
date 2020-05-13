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
package pro.cvitae.gmailrelayer.api;

import javax.xml.bind.annotation.XmlTransient;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-04-04T22:29:14.146Z[GMT]")
@javax.xml.bind.annotation.XmlRootElement
public class ApiResponseMessage {
    public static final int ERROR = 1;
    public static final int WARNING = 2;
    public static final int INFO = 3;
    public static final int OK = 4;
    public static final int TOO_BUSY = 5;

    int code;
    String type;
    String message;

    public ApiResponseMessage() {
    }

    public ApiResponseMessage(final int code, final String message) {
        this.code = code;
        switch (code) {
        case ERROR:
            this.setType("error");
            break;
        case WARNING:
            this.setType("warning");
            break;
        case INFO:
            this.setType("info");
            break;
        case OK:
            this.setType("ok");
            break;
        case TOO_BUSY:
            this.setType("too busy");
            break;
        default:
            this.setType("unknown");
            break;
        }
        this.message = message;
    }

    @XmlTransient
    public int getCode() {
        return this.code;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
