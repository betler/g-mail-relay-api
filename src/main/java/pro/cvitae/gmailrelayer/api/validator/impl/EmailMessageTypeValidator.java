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
        return (!Strings.isNullOrEmpty(email.getApplicationId()) || Strings.isNullOrEmpty(email.getMessageType()));
    }
}