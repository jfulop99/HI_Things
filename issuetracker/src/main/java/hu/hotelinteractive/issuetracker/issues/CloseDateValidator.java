package hu.hotelinteractive.issuetracker.issues;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class CloseDateValidator implements ConstraintValidator<CloseDateConstraint, Issue> {
    @Override
    public boolean isValid(Issue issue, ConstraintValidatorContext constraintValidatorContext) {
        return issue.getCloseDate() == null || issue.getOpenDate().isBefore(issue.getCloseDate());
    }

    @Override
    public void initialize(CloseDateConstraint constraintAnnotation) {

    }
}
