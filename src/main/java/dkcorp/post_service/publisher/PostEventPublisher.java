package dkcorp.post_service.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dkcorp.post_service.event.PostEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostEventPublisher implements MessagePublisher<PostEvent> {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(PostEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("post-events", message);

            log.info("Published post event to Kafka - {}: {}", "post-events", message);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error serializing post event", e);
        }
    }
}
