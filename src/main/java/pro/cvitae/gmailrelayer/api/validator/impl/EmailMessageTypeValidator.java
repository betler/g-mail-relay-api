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

        // From or applicationID are required
        if (Strings.isNullOrEmpty(email.getFrom()) && Strings.isNullOrEmpty(email.getApplicationId())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("must specify a from or applicationId")
                    .addConstraintViolation();
            return false;
        } else {
            return true;
        }

        /*
         * // From is enough if (!Strings.isNullOrEmpty(email.getFrom())) { return true;
         * }
         * 
         * // There's no from, check application ID // If we are here, it means
         * applicationID is not null return true;
         */

        /*
         * if (Strings.isNullOrEmpty(email.getMessageType())) { // If messageType is
         * null, everything's OK return false; } else { // Only wrong if applicationId
         * is null or empty return email.getApplicationId() != null &&
         * !email.getApplicationId().isEmpty(); }
         */
    }
}