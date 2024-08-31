package dkcorp.post_service.event.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostEvent {
    private PostEventType eventType;
    private Long postId;
    private Long actorId;
    private Instant performedAt;
}
