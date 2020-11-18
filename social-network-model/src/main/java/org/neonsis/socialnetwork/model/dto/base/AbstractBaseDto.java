package org.neonsis.socialnetwork.model.dto.base;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * AbstractBaseDto.
 *
 * @author neonsis
 * @see AbstractBaseEntity
 */
@Getter
@Setter
public class AbstractBaseDto implements Serializable {

    /**
     * The {@code serialVersionUID}.
     */
    private static final long serialVersionUID = -1620132505476863875L;

    /**
     * The Unique Identifier (primary key) of this record.
     */
    private Long id;

    /**
     * An auto-populating date/time stamp of when the record was created.
     */
    private Date createdAt;

    /**
     * An auto-populating date/time stamp of when the record was last updated.
     */
    private Date updatedAt;
}
