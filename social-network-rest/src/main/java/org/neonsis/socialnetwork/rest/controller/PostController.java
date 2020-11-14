package org.neonsis.socialnetwork.rest.controller;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.model.dto.PageDto;
import org.neonsis.socialnetwork.model.dto.PostDto;
import org.neonsis.socialnetwork.rest.payload.mapper.PostRestMapper;
import org.neonsis.socialnetwork.rest.payload.request.PostCreateRequest;
import org.neonsis.socialnetwork.rest.payload.response.PostResponse;
import org.neonsis.socialnetwork.rest.security.CurrentUser;
import org.neonsis.socialnetwork.rest.security.UserPrincipal;
import org.neonsis.socialnetwork.service.PostService;
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
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRestMapper postRestMapper;

    @GetMapping("/{userId}")
    public ResponseEntity<PageDto<PostResponse>> getUserPosts(
            @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            }) Pageable pageable,
            @PathVariable Long userId) {
        PageDto<PostDto> userPosts = postService.getUserPosts(userId, pageable);
        return new ResponseEntity<>(postRestMapper.pageDtoPostDtoToPageDtoPostResponse(userPosts), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostCreateRequest postCreateRequest, @CurrentUser UserPrincipal userPrincipal) {
        PostDto postDto = postRestMapper.postCreateRequestToPostDto(postCreateRequest);
        PostDto createdPost = postService.create(userPrincipal.getId(), postDto);
        return new ResponseEntity<>(postRestMapper.postDtoToPostResponse(createdPost), HttpStatus.CREATED);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<HttpStatus> deletePost(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long postId) {
        postService.delete(postId, userPrincipal.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
