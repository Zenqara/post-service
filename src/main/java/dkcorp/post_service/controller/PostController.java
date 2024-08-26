package dkcorp.post_service.controller;

import dkcorp.post_service.dto.post.PostCreateDto;
import dkcorp.post_service.dto.post.PostDto;
import dkcorp.post_service.dto.post.PostUpdateDto;
import dkcorp.post_service.service.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Validated
@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all posts", description = "Returns a list of all posts")
    public List<PostDto> findAllPosts() {
        return postService.findAll();
    }

    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find post by ID", description = "Returns a single post by ID")
    public PostDto findPostById(@PathVariable @NotNull Long postId) {
        return postService.findPostById(postId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new post", description = "Creates a new post and returns the created post")
    public PostDto createPost(@RequestBody @Valid PostCreateDto postCreateDto) {
        return postService.createPost(postCreateDto);
    }

    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an existing post", description = "Updates an existing post by ID")
    public PostDto updatePost(@PathVariable @NotNull Long postId, @RequestBody @Valid PostUpdateDto postUpdateDto) {
        return postService.updatePost(postId, postUpdateDto);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a post", description = "Deletes a post by ID")
    public void deletePost(@PathVariable @NotNull Long postId) {
        postService.deletePost(postId);
    }
}
