package org.neonsis.socialnetwork.model.dto.post;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.dto.base.AbstractBaseDto;

/**
 * CommentDto.
 *
 * @author neonsis
 */
@Getter
@Setter
public class CommentDto extends AbstractBaseDto {

    /**
     * The comment's content.
     */
    private String content;

    /**
     * The post to which this comment related.
     */
    private PostDto postDto;

    /**
     * The user who created this comment.
     */
    private UserDto user;
}
