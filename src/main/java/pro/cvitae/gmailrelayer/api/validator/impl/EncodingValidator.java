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
package pro.cvitae.gmailrelayer.api.validator.impl;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import pro.cvitae.gmailrelayer.api.validator.Encoding;

public class EncodingValidator implements ConstraintValidator<Encoding, String> {

    @Override
    public void initialize(final Encoding encoding) {
        // default constructor
    }

    @Override
    public boolean isValid(final String encoding, final ConstraintValidatorContext cxt) {

        if (encoding == null) {
            return true;
        }

        try {
            Charset.forName(encoding);
            return true;
        } catch (final IllegalCharsetNameException | UnsupportedCharsetException uce) {
            return false;
        }
    }
}
