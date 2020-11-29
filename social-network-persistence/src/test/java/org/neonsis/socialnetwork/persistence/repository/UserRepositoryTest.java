package org.neonsis.socialnetwork.persistence.repository;

import org.junit.jupiter.api.Test;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link UserRepositoryTest} Integration Test.
 *
 * @author neonsis
 */
@DataJpaTest
@Sql("/test-data.sql")
class UserRepositoryTest {

    private static final String EMAIL = "test@gmail.com";

    /**
     * The tested repository.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Test method for {@link UserRepository#findByEmail(String)}.
     */
    @Test
    public void testFindByEmail() {
        Optional<User> byEmail = userRepository.findByEmail(EMAIL);

        assertTrue(byEmail.isPresent());
        assertEquals(EMAIL, byEmail.get().getEmail());
    }

    /**
     * Test method for {@link UserRepository#existsByEmail(String)}.
     */
    @Test
    public void testExistsByEmail() {
        boolean isExists = userRepository.existsByEmail(EMAIL);

        assertTrue(isExists);
    }
}