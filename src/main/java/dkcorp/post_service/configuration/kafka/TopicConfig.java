package dkcorp.post_service.configuration.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
public class TopicConfig {
    private final KafkaTopicProperties properties;

    @Bean
    public NewTopic postTopic() {
        return TopicBuilder.name(properties.getName()).build();
    }
}
