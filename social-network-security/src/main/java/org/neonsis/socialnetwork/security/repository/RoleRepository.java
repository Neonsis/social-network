package org.neonsis.socialnetwork.security.repository;

import org.neonsis.socialnetwork.security.model.domain.Role;
import org.neonsis.socialnetwork.security.model.domain.RoleName;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(RoleName roleName);
}
