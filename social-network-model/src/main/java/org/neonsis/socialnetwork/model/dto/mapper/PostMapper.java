package org.neonsis.socialnetwork.model.dto.mapper;

import org.mapstruct.Mapper;
import org.neonsis.socialnetwork.model.domain.post.Post;
import org.neonsis.socialnetwork.model.dto.post.PostDto;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostDto postToPostDto(Post post);
}
