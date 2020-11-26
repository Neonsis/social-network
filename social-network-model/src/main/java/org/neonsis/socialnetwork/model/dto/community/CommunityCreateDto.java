package org.neonsis.socialnetwork.model.dto.community;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntity;

@Getter
@Setter
public class CommunityCreateDto extends AbstractBaseEntity {

    private String title;
}
