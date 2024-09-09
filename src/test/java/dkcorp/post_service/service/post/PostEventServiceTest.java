package dkcorp.post_service.service.post;

import dkcorp.post_service.entity.Post;
import dkcorp.post_service.event.PostEvent;
import dkcorp.post_service.factory.PostEventFactory;
import dkcorp.post_service.publisher.PostEventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostEventServiceTest {

    @Mock
    private PostEventPublisher postEventPublisher;

    @Mock
    private PostEventFactory postEventFactory;

    @InjectMocks
    private PostEventService postEventService;

    private Post post;
    private PostEvent postCreatedEvent;
    private PostEvent postUpdatedEvent;

    @BeforeEach
    void setUp() {
        post = Post.builder()
                .id(1L)
                .authorId(33L)
                .title("My FIRST post")
                .content("This is content of my FIRST post")
                .build();

        postCreatedEvent = new PostEvent();
        postUpdatedEvent = new PostEvent();
    }

    @Test
    void publishPostCreatedEvent_shouldCallPublisherWithCreatedEvent() {
        when(postEventFactory.createPostCreatedEvent(post)).thenReturn(postCreatedEvent);

        postEventService.publishPostCreatedEvent(post);

        verify(postEventFactory, times(1)).createPostCreatedEvent(post);
        verify(postEventPublisher, times(1)).publish(postCreatedEvent);
    }

    @Test
    void publishPostUpdatedEvent_shouldCallPublisherWithUpdatedEvent() {
        when(postEventFactory.createPostUpdatedEvent(post)).thenReturn(postUpdatedEvent);

        postEventService.publishPostUpdatedEvent(post);

        verify(postEventFactory, times(1)).createPostUpdatedEvent(post);
        verify(postEventPublisher, times(1)).publish(postUpdatedEvent);
    }
}
