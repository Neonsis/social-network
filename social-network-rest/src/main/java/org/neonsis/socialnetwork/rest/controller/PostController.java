package org.neonsis.socialnetwork.rest.controller;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.model.dto.post.CommentCreateDto;
import org.neonsis.socialnetwork.model.dto.post.CommentDto;
import org.neonsis.socialnetwork.model.dto.post.PostCreateDto;
import org.neonsis.socialnetwork.model.dto.post.PostDto;
import org.neonsis.socialnetwork.rest.model.mapper.RestMapper;
import org.neonsis.socialnetwork.rest.model.response.CommentResponse;
import org.neonsis.socialnetwork.rest.model.response.PostResponse;
import org.neonsis.socialnetwork.service.CommentService;
import org.neonsis.socialnetwork.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.neonsis.socialnetwork.rest.util.AppConstants.DEFAULT_PAGE_NUMBER;
import static org.neonsis.socialnetwork.rest.util.AppConstants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final RestMapper restMapper;
    private final CommentService commentService;

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<Page<PostResponse>> getUserPosts(
            @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            }) Pageable pageable,
            @PathVariable Long userId) {
        Page<PostDto> userPosts = postService.getUserPosts(userId, pageable);
        Page<PostResponse> map = userPosts.map(restMapper::postDtoToPostResponse);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/users/feed")
    public ResponseEntity<Page<PostResponse>> getFeedPosts(
            @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            }) Pageable pageable
    ) {
        return ResponseEntity.ok(postService.getFeedPosts(pageable).map(restMapper::postDtoToPostResponse));
    }

    @PostMapping("/posts")
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostCreateDto postCreateDto) {
        PostDto createdPost = postService.create(postCreateDto);
        return ResponseEntity.ok(restMapper.postDtoToPostResponse(createdPost));
    }

    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<HttpStatus> likePost(@PathVariable Long postId) {
        postService.likePost(postId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/posts/{postId}/unlike")
    public ResponseEntity<HttpStatus> unlikePost(@PathVariable Long postId) {
        postService.unlikePost(postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable Long postId) {
        postService.delete(postId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/posts/{postId}/comment")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Long postId,
            @RequestBody CommentCreateDto commentCreateDto
    ) {
        commentCreateDto.setPostId(postId);
        CommentDto createdComment = commentService.create(commentCreateDto);
        return new ResponseEntity<>(restMapper.commentDtoToCommentResponse(createdComment), HttpStatus.CREATED);
    }
}
