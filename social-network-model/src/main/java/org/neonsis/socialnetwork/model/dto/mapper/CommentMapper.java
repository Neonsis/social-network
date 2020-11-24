package org.neonsis.socialnetwork.model.dto.mapper;

import org.mapstruct.Mapper;
import org.neonsis.socialnetwork.model.domain.post.Comment;
import org.neonsis.socialnetwork.model.dto.post.CommentDto;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDto commentToDto(Comment comment);
}
