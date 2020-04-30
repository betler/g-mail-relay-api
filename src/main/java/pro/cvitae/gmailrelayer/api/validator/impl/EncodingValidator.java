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
