package org.neonsis.socialnetwork.security.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Role.
 *
 * @author neonsis
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role {

    /**
     * The {@code serialVersionUID}.
     */
    private static final long serialVersionUID = -9038839708758180171L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true, insertable = false)
    private Long id;

    /**
     * The name of this record.
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 60, nullable = false)
    private RoleName name;
}
