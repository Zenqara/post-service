package dkcorp.post_service.service.post;

import dkcorp.post_service.entity.Post;
import dkcorp.post_service.event.post.PostEvent;
import dkcorp.post_service.factory.PostEventFactory;
import dkcorp.post_service.publisher.PostEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostEventService {

    private final PostEventFactory postEventFactory;
    private final PostEventPublisher postEventPublisher;

    public void publishPostCreatedEvent(Post post) {
        PostEvent event = postEventFactory.createPostCreatedEvent(post);
        postEventPublisher.publish(event);
    }

    public void publishPostUpdatedEvent(Post post) {
        PostEvent event = postEventFactory.createPostUpdatedEvent(post);
        postEventPublisher.publish(event);
    }
}
