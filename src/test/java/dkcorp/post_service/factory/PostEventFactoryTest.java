package dkcorp.post_service.factory;

import dkcorp.post_service.entity.Post;
import dkcorp.post_service.event.EventType;
import dkcorp.post_service.event.PostEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PostEventFactoryTest {
    private PostEventFactory postEventFactory;
    private Post post;

    @BeforeEach
    void setUp() {
        postEventFactory = new PostEventFactory();
        post = Post.builder()
                .id(1L)
                .authorId(33L)
                .title("My FIRST post")
                .content("This is content of my FIRST post")
                .createdAt(LocalDateTime.of(2020, 4, 1, 10, 3))
                .updatedAt(LocalDateTime.of(2024, 9, 7, 9, 44))
                .build();
    }

    @Test
    void createPostCreatedEvent_shouldReturnPostEventWithCreatedType() {
        PostEvent createdEvent = postEventFactory.createPostCreatedEvent(post);

        assertNotNull(createdEvent, "PostEvent should not be null");
        assertEquals(post.getId(), createdEvent.getPostId());
        assertEquals(post.getAuthorId(), createdEvent.getActorId());
        assertEquals(post.getTitle(), createdEvent.getTitle());
        assertEquals(post.getContent(), createdEvent.getContent());
        assertEquals(EventType.CREATED, createdEvent.getEventType());
        assertEquals(post.getCreatedAt(), createdEvent.getTimestamp(), "Timestamp should match createdAt");
    }

    @Test
    void createPostUpdatedEvent_shouldReturnPostEventWithUpdatedType() {
        PostEvent updatedEvent = postEventFactory.createPostUpdatedEvent(post);

        assertNotNull(updatedEvent, "PostEvent should not be null");
        assertEquals(post.getId(), updatedEvent.getPostId());
        assertEquals(post.getAuthorId(), updatedEvent.getActorId());
        assertEquals(post.getTitle(), updatedEvent.getTitle());
        assertEquals(post.getContent(), updatedEvent.getContent());
        assertEquals(EventType.UPDATED, updatedEvent.getEventType());
        assertEquals(post.getUpdatedAt(), updatedEvent.getTimestamp(), "Timestamp should match updatedAt");
    }
}
