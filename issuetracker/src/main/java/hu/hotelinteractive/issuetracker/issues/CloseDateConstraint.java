package hu.hotelinteractive.issuetracker.issues;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CloseDateValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CloseDateConstraint {
    String message() default "End date must be after begin date and both must be in the future";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
