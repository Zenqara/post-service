package dkcorp.post_service.service.post;

import dkcorp.post_service.dto.post.PostCreateDto;
import dkcorp.post_service.dto.post.PostDto;
import dkcorp.post_service.dto.post.PostUpdateDto;
import dkcorp.post_service.entity.Post;
import dkcorp.post_service.exception.NotFoundException;
import dkcorp.post_service.mapper.PostMapper;
import dkcorp.post_service.repository.PostRepository;
import dkcorp.post_service.validator.post.PostValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostValidator postValidator;

    @Mock
    private PostMapper postMapper;

    @Test
    void findAll_shouldReturnListOfPostDtos() {
        List<Post> users = List.of(
                Post.builder().id(1L).authorId(33L).title("My FIRST post").content("This is content of my FIRST post").build(),
                Post.builder().id(2L).authorId(33L).title("My SECOND post").content("This is content of my SECOND post").build()
        );

        List<PostDto> userDtos = List.of(
                PostDto.builder().id(1L).authorId(33L).title("My FIRST post").content("This is content of my FIRST post").build(),
                PostDto.builder().id(2L).authorId(33L).title("My SECOND post").content("This is content of my SECOND post").build()
        );

        when(postRepository.findAll()).thenReturn(users);
        when(postMapper.toDtoList(users)).thenReturn(userDtos);

        List<PostDto> result = postService.findAll();

        assertEquals(userDtos, result);
    }

    @Test
    void findById_shouldReturnPostDto() {
        Long postId = 1L;
        Post post = Post.builder()
                .id(postId)
                .authorId(33L)
                .title("My FIRST post")
                .content("This is content of my FIRST post")
                .build();

        PostDto postDto = PostDto.builder()
                .id(postId)
                .authorId(33L)
                .title("My FIRST post")
                .content("This is content of my FIRST post")
                .build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postMapper.entityToDto(post)).thenReturn(postDto);

        PostDto result = postService.findPostById(postId);

        assertEquals(postDto, result);
    }

    @Test
    void createPost_shouldReturnCreatedPostDto_whenAuthorIsValid() {
        PostCreateDto postCreateDto = PostCreateDto.builder()
                .authorId(33L)
                .title("My FIRST post")
                .content("This is content of my FIRST post")
                .build();

        Post newPost = Post.builder()
                .id(1L)
                .authorId(postCreateDto.getAuthorId())
                .title(postCreateDto.getTitle())
                .content(postCreateDto.getContent())
                .build();

        PostDto createdPostDto = PostDto.builder()
                .id(1L)
                .authorId(newPost.getAuthorId())
                .title(newPost.getTitle())
                .content(newPost.getContent())
                .build();

        doNothing().when(postValidator).validateAuthor(postCreateDto.getAuthorId());
        when(postMapper.createDtoToEntity(postCreateDto)).thenReturn(newPost);
        when(postRepository.save(newPost)).thenReturn(newPost);
        when(postMapper.entityToDto(newPost)).thenReturn(createdPostDto);

        PostDto result = postService.createPost(postCreateDto);

        assertEquals(createdPostDto, result);
        verify(postValidator, times(1)).validateAuthor(postCreateDto.getAuthorId());
    }

    @Test
    void updatePost_shouldReturnUpdatedPostDto() {
        Long postId = 1L;
        PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                .title("My UPDATED post")
                .content("This is updated content of my post")
                .build();

        Post existingPost = Post.builder()
                .id(postId)
                .authorId(33L)
                .title("My FIRST post")
                .content("This is content of my FIRST post")
                .build();

        PostDto updatedPostDto = PostDto.builder()
                .id(postId)
                .authorId(existingPost.getAuthorId())
                .title(postUpdateDto.getTitle())
                .content(postUpdateDto.getContent())
                .build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));

        postMapper.updatePostFromUpdateDto(postUpdateDto, existingPost);

        when(postRepository.save(existingPost)).thenReturn(existingPost);
        when(postMapper.entityToDto(existingPost)).thenReturn(updatedPostDto);

        PostDto result = postService.updatePost(postId, postUpdateDto);

        assertEquals(updatedPostDto, result);
    }

    @Test
    void deletePost_shouldDeletePost() {
        Long postId = 1L;
        Post post = Post.builder()
                .id(postId)
                .authorId(33L)
                .title("My FIRST post")
                .content("This is content of my FIRST post")
                .build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        postService.deletePost(postId);

        verify(postRepository, times(1)).delete(post);
    }

    @Test
    void deletePost_shouldThrowExceptionWhenPostNotFound() {
        Long postId = 1L;

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> postService.deletePost(postId));

        verify(postRepository, times(0)).delete(any());
    }
}
