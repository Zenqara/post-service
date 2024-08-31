package dkcorp.post_service.publisher;

import dkcorp.post_service.event.post.PostEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostEventPublisher implements MessagePublisher<PostEvent> {
    private final KafkaTemplate<String, PostEvent> kafkaTemplate;
    @Value("${kafka.topic.name}")
    private String postTopic;

    @Override
    public void publish(PostEvent event) {
        kafkaTemplate.send(postTopic, event);
    }
}
