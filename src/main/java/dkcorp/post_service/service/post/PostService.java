package dkcorp.post_service.service.post;

import dkcorp.post_service.dto.post.PostCreateDto;
import dkcorp.post_service.dto.post.PostDto;
import dkcorp.post_service.dto.post.PostUpdateDto;

import java.util.List;

public interface PostService {
    List<PostDto> findAll();

    PostDto findPostById(Long postId);

    PostDto createPost(PostCreateDto postCreateDto);

    PostDto updatePost(Long postId, PostUpdateDto postUpdateDto);

    void deletePost(Long postId);
}
