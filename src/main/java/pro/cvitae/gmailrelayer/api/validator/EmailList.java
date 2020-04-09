package pro.cvitae.gmailrelayer.api.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import pro.cvitae.gmailrelayer.api.validator.impl.EmailListValidator;

@Constraint(validatedBy = EmailListValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailList {
    String message() default "is not a valid email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}