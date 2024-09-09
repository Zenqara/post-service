package dkcorp.post_service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostEvent {
    private Long postId;
    private Long actorId;
    private String title;
    private String content;
    private EventType eventType;
    private LocalDateTime timestamp;
}
