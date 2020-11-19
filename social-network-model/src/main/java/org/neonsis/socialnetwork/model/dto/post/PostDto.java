package org.neonsis.socialnetwork.model.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.dto.base.AbstractBaseDto;
import org.neonsis.socialnetwork.model.dto.user.UserDto;

import java.util.Set;


/**
 * PostDto.
 *
 * @author neonsis
 */
@Getter
@Setter
@Builder
public class PostDto extends AbstractBaseDto {

    /**
     * The post's content.
     */
    private String content;

    /**
     * The author of this post.
     */
    private UserDto author;

    /**
     * If the logged in user liked this post.
     */
    private Boolean isLiked;

    /**
     * The count of likes on this post.
     */
    private Integer countLike;

    /**
     * Comments that users writes to this post.
     */
    private Set<CommentDto> comments;
}
