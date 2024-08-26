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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostValidator postValidator;
    private final PostMapper postMapper;
    private final PostRepository postRepository;

    @Override
    public List<PostDto> findAll() {
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
        return postMapper.entityToDto(postRepository.save(post));
    }

    @Override
    public PostDto updatePost(Long postId, PostUpdateDto postUpdateDto) {
        Post post = findById(postId);
        postMapper.updatePostFromUpdateDto(postUpdateDto, post);
        return postMapper.entityToDto(postRepository.save(post));
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.delete(findById(postId));
    }

    private Post findById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new NotFoundException(String.format("Post with id=%d not found", postId)));
    }
}
