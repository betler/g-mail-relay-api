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
package pro.cvitae.gmailrelayer.api.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import pro.cvitae.gmailrelayer.api.validator.impl.EmailMessageTypeValidator;

/**
 * Verifies that no messageType is set without an applicationId.
 *
 * @author betler
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailMessageTypeValidator.class)
public @interface EmailMessageType {

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "can't set messageType without applicationId";

}
