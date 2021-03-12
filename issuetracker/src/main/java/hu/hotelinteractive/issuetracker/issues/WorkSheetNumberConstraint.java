package hu.hotelinteractive.issuetracker.issues;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = WorkSheetNumberValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface WorkSheetNumberConstraint {
        String message() default "Invalid worksheet number";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
}
