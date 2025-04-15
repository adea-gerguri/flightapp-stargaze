package reviews.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


import java.util.ArrayList;
import java.util.List;

public class ForbiddenWordsValidator implements ConstraintValidator<NoForbiddenWords, String> {
    private final List<String> forbiddenWords = new ArrayList<>();

    @Override
    public void initialize(NoForbiddenWords constraintAnnotation) {
        forbiddenWords.addAll(List.of("forbidden", "words", "hitler")); // todo find library for this
    }

    @Override
    public boolean isValid(String payload, ConstraintValidatorContext constraintValidatorContext) {
        return forbiddenWords.stream().noneMatch(payload::contains);
    }
}
