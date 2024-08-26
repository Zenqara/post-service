package dkcorp.post_service.validator.post;

import dkcorp.post_service.exception.NotFoundException;
import dkcorp.post_service.validator.user.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PostValidatorTest {
    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private PostValidatorImpl postValidator;

    @Test
    void validateAuthor_shouldCallValidateUserExistence_whenAuthorIdIsValid() {
        Long authorId = 1L;

        postValidator.validateAuthor(authorId);

        verify(userValidator, times(1)).validateUserExistence(authorId);
    }

    @Test
    void validateAuthor_shouldThrowNotFoundException_whenUserValidatorThrowsNotFoundException() {
        Long authorId = 1L;

        doThrow(new NotFoundException("User not found")).when(userValidator).validateUserExistence(authorId);

        assertThrows(NotFoundException.class, () -> postValidator.validateAuthor(authorId));

        verify(userValidator, times(1)).validateUserExistence(authorId);
    }

    @Test
    void validateAuthor_shouldThrowRuntimeException_whenUserValidatorThrowsRuntimeException() {
        Long authorId = 1L;

        doThrow(new RuntimeException("General error")).when(userValidator).validateUserExistence(authorId);

        assertThrows(RuntimeException.class, () -> postValidator.validateAuthor(authorId));

        verify(userValidator, times(1)).validateUserExistence(authorId);
    }
}
