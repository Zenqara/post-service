package dkcorp.post_service.service.post;

import dkcorp.post_service.dto.post.PostDto;
import dkcorp.post_service.dto.post.PostModifyDto;

import java.util.List;

public interface PostService {
    List<PostDto> findAll();

    PostDto findPostById(Long postId);

    PostDto createPost(PostModifyDto postModifyDto);

    PostDto updatePost(PostModifyDto postModifyDto);

    void deletePost(Long postId);
}
