package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.model.dto.PageDto;
import org.neonsis.socialnetwork.model.dto.post.PostCreateDto;
import org.neonsis.socialnetwork.model.dto.post.PostDto;
import org.springframework.data.domain.Pageable;

public interface PostService {

    PageDto<PostDto> getUserPosts(Long userId, Pageable pageable);

    PostDto create(PostCreateDto postDto);

    void delete(Long postId);

    void likePost(Long postId);

    void unlikePost(Long postId);
}
