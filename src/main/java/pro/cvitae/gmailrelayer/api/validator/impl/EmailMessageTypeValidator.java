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

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.google.common.base.Strings;

import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.validator.EmailMessageType;

public class EmailMessageTypeValidator implements ConstraintValidator<EmailMessageType, EmailMessage> {

    @Override
    public void initialize(final EmailMessageType constraintAnnotation) {
        // Empty
    }

    @Override
    public boolean isValid(final EmailMessage email, final ConstraintValidatorContext context) {

        // cannot set message type without application ID
        return !Strings.isNullOrEmpty(email.getApplicationId()) || Strings.isNullOrEmpty(email.getMessageType());
    }
}