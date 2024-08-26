package dkcorp.post_service.client;

import dkcorp.post_service.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${user-service.host}:${user-service.port}", path = "/api/v1")
public interface UserServiceClient {
    @GetMapping("/users/{userId}")
    UserDto getUserById(@PathVariable Long userId);
}
