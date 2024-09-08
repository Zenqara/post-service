package dkcorp.post_service.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dkcorp.post_service.configuration.kafka.KafkaTopicProperties;
import dkcorp.post_service.event.EventType;
import dkcorp.post_service.event.PostEvent;
import org.apache.kafka.common.errors.SerializationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostEventPublisherTest {
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private KafkaTopicProperties properties;
    @InjectMocks
    private PostEventPublisher postEventPublisher;
    private PostEvent postEvent;

    @BeforeEach
    void setUp() {
        postEvent = PostEvent.builder()
                .postId(1L)
                .actorId(33L)
                .title("My FIRST post")
                .content("This is content of my FIRST post")
                .eventType(EventType.CREATED)
                .timestamp(LocalDateTime.of(2023, 9, 7, 10, 0))
                .build();
    }

    @Test
    void publish_shouldSendPostEventToKafka() throws JsonProcessingException {
        String serializedEvent = "{\"postId\":1,\"actorId\":33,\"title\":\"My FIRST post\",\"content\":\"This is content of my FIRST post\",\"eventType\":\"CREATED\",\"timestamp\":\"2023-09-07T10:00:00\"}";
        when(objectMapper.writeValueAsString(postEvent)).thenReturn(serializedEvent);
        when(properties.getName()).thenReturn("post-events");

        postEventPublisher.publish(postEvent);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate, times(1)).send(eq("post-events"), captor.capture());
        assertEquals(serializedEvent, captor.getValue());

        verify(objectMapper, times(1)).writeValueAsString(postEvent);
    }

    @Test
    void publish_shouldThrowSerializationException_whenJsonProcessingExceptionOccurs() throws JsonProcessingException {
        when(objectMapper.writeValueAsString(postEvent)).thenThrow(new JsonProcessingException("Error serializing post event") {
        });

        assertThrows(SerializationException.class, () -> postEventPublisher.publish(postEvent), "Error serializing post event");

        verify(kafkaTemplate, times(0)).send(anyString(), anyString());
    }
}

