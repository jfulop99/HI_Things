package hu.hotelinteractive.issuetracker.issues;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CloseDateValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CloseDateConstraint {
    String message() default "Cannot before open date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
