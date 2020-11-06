package org.neonsis.socialnetwork.model.domain.user.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.base.BaseEntityAudit;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "role")
public class Role extends BaseEntityAudit implements Serializable {

    private static final long serialVersionUID = -9038839708758180171L;

    @Enumerated(EnumType.STRING)
    @Column(length = 60, nullable = false)
    private RoleName name;
}
