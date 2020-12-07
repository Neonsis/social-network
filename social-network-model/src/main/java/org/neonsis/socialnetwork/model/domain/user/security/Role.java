package org.neonsis.socialnetwork.model.domain.user.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntity;

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
public class Role extends AbstractBaseEntity {

    /**
     * The {@code serialVersionUID}.
     */
    private static final long serialVersionUID = -9038839708758180171L;

    /**
     * The name of this record.
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 60, nullable = false)
    private RoleName name;

    /**
     * Get a new {@link RoleBuilder}.
     *
     * @return a new {@link RoleBuilder}.
     */
    public static RoleBuilder builder() {
        return new RoleBuilder();
    }

    /**
     * A functional programming {@link RoleBuilder} builder.
     *
     * @author neonsis
     */
    public static class RoleBuilder extends AbstractBaseEntity.Builder<Role> {

        @Override
        protected Role buildEntity() {
            return new Role();
        }

        /**
         * Set the name and return the builder.
         *
         * @param name the name of your record being built.
         * @return the builder.
         * @see Role#setName(RoleName)
         */
        public RoleBuilder name(RoleName name) {
            this.getEntity().setName(name);
            return this;
        }
    }
}
