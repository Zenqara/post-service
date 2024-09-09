package dkcorp.post_service.configuration.kafka;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "spring.kafka.producer")
public class KafkaProducerProperties {
    @NotEmpty
    private String bootstrapServers;
    @NotEmpty
    private String keySerializer;
    @NotEmpty
    private String valueSerializer;
    private int retries;
    @NotEmpty
    private String acks;
    private boolean addTypeHeaders;
}
