package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.model.dto.PageDto;
import org.neonsis.socialnetwork.model.dto.PostDto;
import org.springframework.data.domain.Pageable;

public interface PostService {

    PageDto<PostDto> getUserPosts(Long userId, Long loggedInUserId, Pageable pageable);

    PostDto create(Long userId, PostDto postDto);

    void delete(Long postId, Long authorId);

    void likePost(Long postId, Long userId);

    void unlikePost(Long postId, Long userId);
}
