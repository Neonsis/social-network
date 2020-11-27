package org.neonsis.socialnetwork.model.dto.community;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.community.Community;
import org.neonsis.socialnetwork.model.dto.base.AbstractBaseDto;
import org.neonsis.socialnetwork.model.dto.post.PostDto;
import org.neonsis.socialnetwork.model.dto.user.ImageDto;
import org.neonsis.socialnetwork.model.dto.user.UserDto;

import java.util.List;

/**
 * CommunityDto. {@link Community}
 *
 * @author neonsis
 */
@Getter
@Setter
public class CommunityDto extends AbstractBaseDto {

    /**
     * The name of this community.
     */
    private String title;

    /**
     * The creator of this community.
     */
    private UserDto moderator;

    /**
     * The avatar of this community.
     */
    private ImageDto avatar;

    /**
     * The published posts of this community.
     */
    private List<PostDto> posts;

    /**
     * Followers of this community.
     */
    private List<UserDto> followers;
}
