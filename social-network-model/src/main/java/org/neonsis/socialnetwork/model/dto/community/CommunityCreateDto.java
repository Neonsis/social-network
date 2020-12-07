package org.neonsis.socialnetwork.model.dto.community;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntity;
import org.neonsis.socialnetwork.model.domain.community.Community;

import javax.validation.constraints.NotBlank;

/**
 * CommunityCreateDto. {@link Community}
 *
 * @author neonsis
 */
@Getter
@Setter
public class CommunityCreateDto extends AbstractBaseEntity {

    /**
     * The name of this community.
     */
    @NotBlank(message = "{config.data.validation.community_title_not_blank}")
    private String title;
}
