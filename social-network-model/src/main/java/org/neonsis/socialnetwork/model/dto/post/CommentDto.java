package org.neonsis.socialnetwork.model.dto.post;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.post.Comment;
import org.neonsis.socialnetwork.model.dto.base.AbstractBaseDto;
import org.neonsis.socialnetwork.model.dto.user.UserDto;

/**
 * CommentDto. {@link Comment}
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
    private PostDto post;

    /**
     * The user who created this comment.
     */
    private UserDto user;
}
