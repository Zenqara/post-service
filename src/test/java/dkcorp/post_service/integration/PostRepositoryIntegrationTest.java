package dkcorp.post_service.integration;

import dkcorp.post_service.entity.Post;
import dkcorp.post_service.repository.PostRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostRepositoryIntegrationTest extends IntegrationEnvironment {
    @Autowired
    private PostRepository postRepository;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @Test
    void testSavePost() {
        Post post = Post.builder()
                .title("Sample Post")
                .content("This is a test content.")
                .authorId(1L)
                .build();

        Post savedPost = postRepository.save(post);

        assertThat(savedPost.getId()).isNotNull();
        assertThat(savedPost.getTitle()).isEqualTo("Sample Post");
        assertThat(savedPost.getContent()).isEqualTo("This is a test content.");
        assertThat(savedPost.getAuthorId()).isEqualTo(1L);
        assertThat(savedPost.getCreatedAt()).isNotNull();
        assertThat(savedPost.getUpdatedAt()).isNotNull();
    }

    @Test
    void testFindPostById() {
        Post post = Post.builder()
                .title("Another Post")
                .content("Another test content.")
                .authorId(2L)
                .build();
        Post savedPost = postRepository.save(post);

        Optional<Post> retrievedPost = postRepository.findById(savedPost.getId());

        assertThat(retrievedPost).isPresent();
        assertThat(retrievedPost.get().getTitle()).isEqualTo("Another Post");
        assertThat(retrievedPost.get().getContent()).isEqualTo("Another test content.");
        assertThat(retrievedPost.get().getAuthorId()).isEqualTo(2L);
    }

    @Test
    void testUpdatePost() {
        Post post = Post.builder()
                .title("Initial Title")
                .content("Initial content.")
                .authorId(3L)
                .build();
        Post savedPost = postRepository.save(post);

        savedPost.setTitle("Updated Title");
        savedPost.setContent("Updated content.");
        Post updatedPost = postRepository.save(savedPost);

        assertThat(updatedPost.getTitle()).isEqualTo("Updated Title");
        assertThat(updatedPost.getContent()).isEqualTo("Updated content.");
        assertThat(updatedPost.getUpdatedAt()).isAfter(savedPost.getCreatedAt());
    }

    @Test
    void testDeletePost() {
        Post post = Post.builder()
                .title("Post to be deleted")
                .content("Content of post to be deleted.")
                .authorId(4L)
                .build();
        Post savedPost = postRepository.save(post);

        postRepository.delete(savedPost);

        Optional<Post> deletedPost = postRepository.findById(savedPost.getId());
        assertThat(deletedPost).isNotPresent();
    }
}
