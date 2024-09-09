package dkcorp.post_service.factory;

import dkcorp.post_service.entity.Post;
import dkcorp.post_service.event.EventType;
import dkcorp.post_service.event.PostEvent;
import org.springframework.stereotype.Component;

@Component
public class PostEventFactory {
    public PostEvent createPostCreatedEvent(Post post) {
        return PostEvent.builder()
                .postId(post.getId())
                .actorId(post.getAuthorId())
                .title(post.getTitle())
                .content(post.getContent())
                .eventType(EventType.CREATED)
                .timestamp(post.getCreatedAt())
                .build();
    }

    public PostEvent createPostUpdatedEvent(Post post) {
        return PostEvent.builder()
                .postId(post.getId())
                .actorId(post.getAuthorId())
                .title(post.getTitle())
                .content(post.getContent())
                .eventType(EventType.UPDATED)
                .timestamp(post.getUpdatedAt())
                .build();
    }
}
