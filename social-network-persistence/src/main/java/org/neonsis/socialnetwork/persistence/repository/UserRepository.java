package org.neonsis.socialnetwork.persistence.repository;

import org.neonsis.socialnetwork.model.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
