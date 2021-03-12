package hu.hotelinteractive.issuetracker.issues;

import hu.hotelinteractive.issuetracker.IssueTrackerApplicationConfig;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class WorkSheetNumberValidator implements ConstraintValidator<WorkSheetNumberConstraint, String > {
    @Override
    public void initialize(WorkSheetNumberConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String wsNumber, ConstraintValidatorContext constraintValidatorContext) {
        return wsNumber == null || wsNumber.isBlank() || wsNumber.matches("^HI[0-9]{4}$");
    }
}
