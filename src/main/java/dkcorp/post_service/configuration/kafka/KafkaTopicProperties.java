package dkcorp.post_service.configuration.kafka;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "spring.kafka.topic")
public class KafkaTopicProperties {
    @NotEmpty
    private String name;
}
