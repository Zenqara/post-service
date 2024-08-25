package dkcorp.post_service.validator.user;

import dkcorp.post_service.client.UserServiceClient;
import dkcorp.post_service.dto.UserDto;
import dkcorp.post_service.exception.NotFoundException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidatorImpl implements UserValidator {
    private final UserServiceClient userService;

    @Override
    public void validateUserExistence(Long userId) {
        try {
            userService.getUserById(userId);
        } catch (FeignException.NotFound ex) {
            throw new NotFoundException(String.format("User with id '%d' not found", userId));
        } catch (FeignException ex) {
            throw new RuntimeException("Error fetching user", ex);
        }
    }
}
