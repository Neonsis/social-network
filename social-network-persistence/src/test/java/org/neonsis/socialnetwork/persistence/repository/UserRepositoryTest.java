package org.neonsis.socialnetwork.persistence.repository;

import org.junit.jupiter.api.Test;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmail() {
        String expected = "test@gmail.com";

        Optional<User> byEmail = userRepository.findByEmail(expected);

        assertTrue(byEmail.isPresent());
        assertEquals(expected, byEmail.get().getEmail());
    }

    @Test
    public void testExistsByEmail() {
        String expected = "test@gmail.com";

        Optional<User> byEmail = userRepository.findByEmail(expected);

        assertTrue(byEmail.isPresent());
        assertEquals(expected, byEmail.get().getEmail());
    }
}