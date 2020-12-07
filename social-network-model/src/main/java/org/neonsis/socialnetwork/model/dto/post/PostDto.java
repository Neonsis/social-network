package org.neonsis.socialnetwork.model.dto.post;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.post.Post;
import org.neonsis.socialnetwork.model.dto.base.AbstractBaseDto;
import org.neonsis.socialnetwork.model.dto.community.CommunityDto;
import org.neonsis.socialnetwork.model.dto.user.ProfileDto;

import java.util.List;


/**
 * PostDto. {@link Post}
 *
 * @author neonsis
 */
@Getter
@Setter
public class PostDto extends AbstractBaseDto {

    /**
     * The post's content.
     */
    private String content;

    /**
     * The author of this post.
     */
    private ProfileDto author;

    /**
     * The community which made this post
     */
    private CommunityDto community;

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
    private List<CommentDto> comments;
}
