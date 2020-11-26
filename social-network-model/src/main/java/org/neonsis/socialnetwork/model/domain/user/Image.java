package org.neonsis.socialnetwork.model.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "image")
public class Image extends AbstractBaseEntity {

    @Column(name = "imageId", nullable = false, unique = true)
    private String imageId;

    @Column(name = "original_url", nullable = false, updatable = false)
    private String originalUrl;
}
