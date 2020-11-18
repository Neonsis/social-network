package org.neonsis.socialnetwork.model.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.neonsis.socialnetwork.model.domain.post.Comment;
import org.neonsis.socialnetwork.model.dto.CommentDto;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "post.id", target = "postId")
    CommentDto commentToCommentDto(Comment comment);

    Comment commentDtoToComment(CommentDto comment);
}
