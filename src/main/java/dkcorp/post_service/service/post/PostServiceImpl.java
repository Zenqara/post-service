package dkcorp.post_service.service.post;

import dkcorp.post_service.dto.post.PostCreateDto;
import dkcorp.post_service.dto.post.PostDto;
import dkcorp.post_service.dto.post.PostUpdateDto;
import dkcorp.post_service.entity.Post;
import dkcorp.post_service.exception.NotFoundException;
import dkcorp.post_service.mapper.PostMapper;
import dkcorp.post_service.repository.PostRepository;
import dkcorp.post_service.validator.post.PostValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostValidator postValidator;
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public List<PostDto> findAll() {
        kafkaTemplate.send("post-events", "The find all method was called");

        log.info("Sending message to kafka topic");
        log.info("--------------------------------");

        return postMapper.toDtoList(postRepository.findAll());
    }

    @Override
    public PostDto findPostById(Long postId) {
        Post post = findById(postId);
        return postMapper.entityToDto(post);
    }

    @Override
    public PostDto createPost(PostCreateDto postCreateDto) {
        postValidator.validateAuthor(postCreateDto.getAuthorId());
        Post post = postMapper.createDtoToEntity(postCreateDto);
        Post savedPost = postRepository.save(post);
        return postMapper.entityToDto(savedPost);
    }

    @Override
    public PostDto updatePost(Long postId, PostUpdateDto postUpdateDto) {
        Post post = findById(postId);
        postMapper.updatePostFromUpdateDto(postUpdateDto, post);
        Post updatedPost = postRepository.save(post);
        return postMapper.entityToDto(updatedPost);
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.delete(findById(postId));
    }

    private Post findById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new NotFoundException(String.format("Post with id=%d not found", postId)));
    }
}
