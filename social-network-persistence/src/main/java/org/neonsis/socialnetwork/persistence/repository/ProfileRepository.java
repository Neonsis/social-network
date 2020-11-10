package org.neonsis.socialnetwork.persistence.repository;

import org.neonsis.socialnetwork.model.domain.user.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<Profile, Long> {

    Optional<Profile> findById(Long id);
}
