package dkcorp.post_service.validator.user;

import dkcorp.post_service.client.UserServiceClient;
import dkcorp.post_service.dto.UserDto;
import dkcorp.post_service.exception.NotFoundException;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import feign.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserValidatorTest {
    @Mock
    private UserServiceClient userServiceClient;

    @InjectMocks
    private UserValidatorImpl userValidator;

    @Test
    void validateUserExistence_shouldSucceed_whenUserExists() {
        Long userId = 1L;

        when(userServiceClient.getUserById(userId)).thenReturn(new UserDto());

        userValidator.validateUserExistence(userId);

        verify(userServiceClient, times(1)).getUserById(userId);
    }

    @Test
    void validateUserExistence_shouldThrowNotFoundException_whenUserNotFound() {
        Long userId = 1L;

        when(userServiceClient.getUserById(userId)).thenThrow(FeignException.NotFound.class);

        assertThrows(NotFoundException.class, () -> userValidator.validateUserExistence(userId));

        verify(userServiceClient, times(1)).getUserById(userId);
    }

    @Test
    public void validateUserExistence_ShouldThrowRuntimeException_WhenFeignExceptionOccurs() {
        Long userId = 123L;

        Request request = Request.create(
                Request.HttpMethod.GET,
                "https://example.com/users/123",
                new HashMap<>(),
                new byte[0],
                StandardCharsets.UTF_8,
                new RequestTemplate()
        );

        Response response = Response.builder()
                .status(500)
                .reason("Internal Server Error")
                .request(request)
                .build();

        FeignException feignException = FeignException.errorStatus("GET", response);
        doThrow(feignException).when(userServiceClient).getUserById(userId);

        assertThrows(RuntimeException.class, () -> userValidator.validateUserExistence(userId));
    }
}
