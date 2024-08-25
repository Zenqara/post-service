package dkcorp.post_service.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateDto {
    @NotBlank(message = "Author id is mandatory")
    private Long authorId;
    @NotBlank(message = "Title must not be blank. Example: My First Post")
    @Size(min = 1, max = 32, message = "Title must be between 1 and 32 characters")
    private String title;
    @NotBlank(message = "Content must not be blank. Example: This is the content of my post")
    @Size(min = 1, max = 4096, message = "Content must be between 1 and 4096 characters")
    private String content;
}
