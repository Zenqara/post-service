package dkcorp.post_service.validator.post;

import dkcorp.post_service.validator.user.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostValidatorImpl implements PostValidator {
    private final UserValidator userValidator;

    @Override
    public void validateAuthor(Long authorId) {
        userValidator.validateUserExistence(authorId);
    }
}
