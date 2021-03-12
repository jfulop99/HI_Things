package hu.hotelinteractive.issuetracker.issues;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class CloseDateValidator implements ConstraintValidator<CloseDateConstraint, LocalDate> {
    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate.isAfter(LocalDate.now());
    }

    @Override
    public void initialize(CloseDateConstraint constraintAnnotation) {

    }
}
