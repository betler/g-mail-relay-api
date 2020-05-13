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

import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import pro.cvitae.gmailrelayer.api.validator.EmailList;

public class EmailListValidator implements ConstraintValidator<EmailList, List<String>> {

    @Override
    public void initialize(final EmailList emailList) {
        // default constructor
    }

    @Override
    public boolean isValid(final List<String> emailListField, final ConstraintValidatorContext cxt) {

        // Null list allowed
        if (emailListField == null) {
            return true;
        }

        for (final String email : emailListField) {
            try {
                final InternetAddress[] parsed = InternetAddress.parse(email, true);

                if (parsed.length != 1) {
                    // TODO Change message
                    return false;
                }

                parsed[0].validate();

            } catch (final AddressException e) {
                return false;
            }
        }

        return true;
    }

}
