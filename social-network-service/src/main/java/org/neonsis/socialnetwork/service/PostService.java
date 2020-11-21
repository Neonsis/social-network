package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.model.dto.post.PostCreateDto;
import org.neonsis.socialnetwork.model.dto.post.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Page<PostDto> getUserPosts(Long userId, Pageable pageable);

    Page<PostDto> getFeedPosts(Pageable pageable);

    PostDto create(PostCreateDto postDto);

    void delete(Long postId);

    void likePost(Long postId);

    void unlikePost(Long postId);
}
