package dkcorp.post_service.mapper;

import dkcorp.post_service.dto.post.PostCreateDto;
import dkcorp.post_service.dto.post.PostDto;
import dkcorp.post_service.dto.post.PostUpdateDto;
import dkcorp.post_service.entity.Post;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PostMapperTest {
    private final PostMapper postMapper = Mappers.getMapper(PostMapper.class);

    @Test
    void createDtoToEntity_shouldConvertPostCreateDtoToPost() {
        PostCreateDto postCreateDto = PostCreateDto.builder()
                .authorId(33L)
                .title("My FIRST post")
                .content("This is content of my FIRST post")
                .build();

        Post post = postMapper.createDtoToEntity(postCreateDto);

        assertEquals(postCreateDto.getAuthorId(), post.getAuthorId());
        assertEquals(postCreateDto.getTitle(), post.getTitle());
        assertEquals(postCreateDto.getContent(), post.getContent());
    }

    @Test
    void entityToDto_shouldConvertPostToPostDto() {
        Post post = Post.builder()
                .id(1L)
                .authorId(33L)
                .title("My FIRST post")
                .content("This is content of my FIRST post")
                .build();

        PostDto postDto = postMapper.entityToDto(post);

        assertEquals(post.getId(), postDto.getId());
        assertEquals(post.getAuthorId(), postDto.getAuthorId());
        assertEquals(post.getTitle(), postDto.getTitle());
        assertEquals(post.getContent(), postDto.getContent());
    }

    @Test
    void toDtoList_shouldConvertListOfPostsToListOfPostDtos() {
        List<Post> posts = List.of(
                Post.builder().id(1L).authorId(33L).title("My FIRST post").content("This is content of my FIRST post").build(),
                Post.builder().id(2L).authorId(33L).title("My SECOND post").content("This is content of my SECOND post").build()
        );

        List<PostDto> postDtos = postMapper.toDtoList(posts);

        assertEquals(posts.size(), postDtos.size());
        for (int i = 0; i < posts.size(); i++) {
            assertEquals(posts.get(i).getId(), postDtos.get(i).getId());
            assertEquals(posts.get(i).getAuthorId(), postDtos.get(i).getAuthorId());
            assertEquals(posts.get(i).getTitle(), postDtos.get(i).getTitle());
            assertEquals(posts.get(i).getContent(), postDtos.get(i).getContent());
        }
    }

    @Test
    void updatePostFromUpdateDto_shouldUpdateExistingPost() {
        Post existingPost = Post.builder()
                .id(1L)
                .authorId(33L)
                .title("My FIRST post")
                .content("This is content of my FIRST post")
                .build();

        PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                .title("My UPDATED post")
                .content("This is updated content of my post")
                .build();

        postMapper.updatePostFromUpdateDto(postUpdateDto, existingPost);

        assertEquals(postUpdateDto.getTitle(), existingPost.getTitle());
        assertEquals(postUpdateDto.getContent(), existingPost.getContent());
    }

    @Test
    void mapNullPostToDtoTest() {
        PostDto postDto = postMapper.entityToDto(null);
        assertNull(postDto);
    }

    @Test
    void mapNullCreateDtoToEntityTest() {
        Post post = postMapper.createDtoToEntity(null);
        assertNull(post);
    }

    @Test
    void mapNullPostListToDtoListTest() {
        List<PostDto> postDtoList = postMapper.toDtoList(null);
        assertNull(postDtoList);
    }

    @Test
    void mapNullUpdateDtoToExistingPostTest() {
        Post existingPost = Post.builder()
                .id(1L)
                .authorId(33L)
                .title("My FIRST post")
                .content("This is content of my FIRST post")
                .build();

        postMapper.updatePostFromUpdateDto(null, existingPost);

        assertEquals(1L, existingPost.getId());
        assertEquals(33L, existingPost.getAuthorId());
        assertEquals("My FIRST post", existingPost.getTitle());
        assertEquals("This is content of my FIRST post", existingPost.getContent());
    }
}
