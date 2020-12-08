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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.neonsis.socialnetwork.rest.util.AppConstants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    private final RestMapper restMapper;

    @GetMapping("/users/{userId}/posts")
    public Page<PostResponse> getUserPosts(
            @PageableDefault(size = DEFAULT_PAGE_SIZE)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            }) Pageable pageable,
            @PathVariable Long userId
    ) {
        Page<PostDto> userPosts = postService.findUserPosts(userId, pageable);

        return userPosts.map(restMapper::postDtoToResponse);
    }

    @GetMapping("/communities/{communityId}/posts")
    public Page<PostResponse> getCommunityPosts(
            @PageableDefault(size = DEFAULT_PAGE_SIZE)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            }) Pageable pageable,
            @PathVariable Long communityId
    ) {
        Page<PostDto> communityPosts = postService.findCommunityPosts(communityId, pageable);

        return communityPosts.map(restMapper::postDtoToResponse);
    }

    @GetMapping("/users/feed")
    public Page<PostResponse> getFeedPosts(
            @PageableDefault(size = DEFAULT_PAGE_SIZE)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            }) Pageable pageable
    ) {
        return postService.findFeedPosts(pageable).map(restMapper::postDtoToResponse);
    }

    @PostMapping("/users/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse createUserPost(@Valid @RequestBody PostCreateDto postCreateDto) {
        PostDto createdPost = postService.saveUserPost(postCreateDto);

        return restMapper.postDtoToResponse(createdPost);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/communities/{communityId}/posts")
    public PostResponse createCommunityPost(
            @PathVariable Long communityId,
            @Valid @RequestBody PostCreateDto postCreateDto
    ) {
        PostDto createdPost = postService.saveCommunityPost(postCreateDto, communityId);

        return restMapper.postDtoToResponse(createdPost);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/posts/{postId}/like")
    public void likePost(@PathVariable Long postId) {
        postService.like(postId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/posts/{postId}/unlike")
    public void unlikePost(@PathVariable Long postId) {
        postService.unlike(postId);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deleteById(postId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts/{postId}/comments")
    public CommentResponse addComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentCreateDto commentCreateDto
    ) {
        CommentDto createdComment = commentService.addCommentToPost(commentCreateDto, postId);

        return restMapper.commentDtoToResponse(createdComment);
    }
}
