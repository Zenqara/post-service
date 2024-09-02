package dkcorp.post_service.event;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostEvent {
    private Long postId;
    private Long actorId;
    private EventType eventType;
    private LocalDateTime performedAt;
}
