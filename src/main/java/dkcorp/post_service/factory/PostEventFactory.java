package dkcorp.post_service.factory;

import dkcorp.post_service.entity.Post;
import dkcorp.post_service.event.post.PostEvent;
import dkcorp.post_service.event.post.PostEventType;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class PostEventFactory {
    public PostEvent createPostCreatedEvent(Post post) {
        return PostEvent.builder()
                .eventType(PostEventType.POST_CREATED)
                .postId(post.getId())
                .actorId(post.getAuthorId())
                .performedAt(Instant.now())
                .build();
    }

    public PostEvent createPostUpdatedEvent(Post post) {
        return PostEvent.builder()
                .eventType(PostEventType.POST_UPDATED)
                .postId(post.getId())
                .actorId(post.getAuthorId())
                .performedAt(Instant.now())
                .build();
    }
}
