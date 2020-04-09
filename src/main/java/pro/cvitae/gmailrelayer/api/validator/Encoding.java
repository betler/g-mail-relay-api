package pro.cvitae.gmailrelayer.api.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import pro.cvitae.gmailrelayer.api.validator.impl.EncodingValidator;

@Constraint(validatedBy = EncodingValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Encoding {
    String message() default "is not a valid encoding";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}