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
