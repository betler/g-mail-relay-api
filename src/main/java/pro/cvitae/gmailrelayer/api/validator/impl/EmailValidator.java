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

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import pro.cvitae.gmailrelayer.api.validator.Email;

public class EmailValidator implements ConstraintValidator<Email, String> {

    @Override
    public void initialize(final Email email) {
        // default constructor
    }

    @Override
    public boolean isValid(final String emailField, final ConstraintValidatorContext cxt) {

        if (emailField == null) {
            return true;
        }

        try {
            final InternetAddress[] parsed = InternetAddress.parse(emailField, true);

            if (parsed.length != 1) {
                // TODO Custom message?
                return false;
            }

            parsed[0].validate();
            return true;

        } catch (final AddressException e) {
            return false;
        }
    }

}
