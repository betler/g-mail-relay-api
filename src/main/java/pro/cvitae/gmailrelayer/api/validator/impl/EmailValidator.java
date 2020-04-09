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
        InternetAddress[] parsed;
        try {
            parsed = InternetAddress.parse(emailField);
        } catch (final AddressException e) {
            return false;
        }
        return parsed.length == 1;
    }
}
