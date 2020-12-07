package org.neonsis.socialnetwork.persistence.repository;

import org.neonsis.socialnetwork.model.domain.user.security.Role;
import org.neonsis.socialnetwork.model.domain.user.security.RoleName;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(RoleName roleName);
}
