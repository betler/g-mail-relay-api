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
        InternetAddress[] parsed;
        try {
            parsed = InternetAddress.parse(emailField, true);

            if (parsed.length != 1) {
                return false;
            }

            parsed[0].validate();
            return true;

        } catch (final AddressException e) {
            return false;
        }
    }
}
