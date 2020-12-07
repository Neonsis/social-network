package org.neonsis.socialnetwork.persistence.repository;

import org.junit.jupiter.api.Test;
import org.neonsis.socialnetwork.model.domain.user.Role;
import org.neonsis.socialnetwork.model.domain.user.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link RoleRepositoryTest} Integration Test.
 *
 * @author neonsis
 */
@DataJpaTest
@Sql("/test-data.sql")
class RoleRepositoryTest {

    /**
     * The tested repository.
     */
    @Autowired
    private RoleRepository roleRepository;

    /**
     * Test method for {@link RoleRepository#findByName(RoleName)}.
     */
    @Test
    public void testFindByName() {
        Optional<Role> byName = roleRepository.findByName(RoleName.ROLE_USER);

        assertTrue(byName.isPresent());
        assertEquals(RoleName.ROLE_USER, byName.get().getName());
    }
}