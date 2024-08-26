package dkcorp.post_service.controller;

import dkcorp.post_service.dto.post.PostCreateDto;
import dkcorp.post_service.dto.post.PostDto;
import dkcorp.post_service.dto.post.PostUpdateDto;
import dkcorp.post_service.service.post.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {
    @InjectMocks
    private PostController postController;

    @Mock
    private PostService postService;


    @Test
    void findAllPosts_shouldReturnListOfPostDtos() {
        List<PostDto> postDtos = List.of(
                PostDto.builder().id(1L).authorId(33L).title("My FIRST post").content("This is the content of my FIRST post").build(),
                PostDto.builder().id(2L).authorId(33L).title("My SECOND post").content("This is the content of my SECOND post").build()
        );

        when(postService.findAll()).thenReturn(postDtos);

        List<PostDto> result = postController.findAllPosts();
        assertEquals(postDtos, result);
    }

    @Test
    void findPostById_shouldReturnPostDto() {
        Long postId = 1L;
        PostDto post = PostDto.builder().id(1L).authorId(33L).title("My FIRST post").content("This is the content of my FIRST post").build();

        when(postService.findPostById(postId)).thenReturn(post);

        PostDto result = postController.findPostById(postId);
        assertEquals(post, result);
    }

    @Test
    void createPost_shouldReturnCreatedPostDto() {
        PostCreateDto newPost = PostCreateDto.builder()
                .authorId(33L).title("My FIRST post")
                .content("This is the content of my FIRST post").build();

        PostDto createdPost = PostDto.builder()
                .id(1L).authorId(33L).title("My FIRST post")
                .content("This is the content of my FIRST post").build();

        when(postService.createPost(any(PostCreateDto.class))).thenReturn(createdPost);

        PostDto result = postController.createPost(newPost);
        assertEquals(createdPost, result);
    }

    @Test
    void updatePost_shouldReturnUpdatedPostDto() {
        Long postId = 1L;
        PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                .title("My updated post")
                .content("This is my updated content").build();

        PostDto updatedPost = PostDto.builder()
                .id(1L).authorId(33L).title("My updated post")
                .content("This is my updated content").build();

        when(postService.updatePost(eq(postId), any(PostUpdateDto.class))).thenReturn(updatedPost);

        PostDto result = postController.updatePost(postId, postUpdateDto);
        assertEquals(updatedPost, result);
    }

    @Test
    void deletePost_shouldDeletePost() {
        Long postId = 1L;

        doNothing().when(postService).deletePost(postId);

        assertDoesNotThrow(() -> postController.deletePost(postId));

        verify(postService, times(1)).deletePost(postId);
    }
}
