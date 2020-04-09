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
                final InternetAddress[] parsed = InternetAddress.parse(email);
                if (parsed.length != 1) {
                    return false;
                }
            } catch (final AddressException e) {
                return false;
            }
        }

        return true;
    }

}
