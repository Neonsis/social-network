package org.neonsis.socialnetwork.persistence.repository;

import org.junit.jupiter.api.Test;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmail_whenExists_shouldReturnUser() {
        User user = createUser();
        userRepository.save(user);
        String expected = "test@gmail.ru";

        Optional<User> byEmail = userRepository.findByEmail(expected);

        assertTrue(byEmail.isPresent());
        assertEquals(expected, byEmail.get().getEmail());
    }

    @Test
    public void testExistsByEmail_whenExists_shouldReturnUser() {
        User user = createUser();
        userRepository.save(user);
        String expected = "test@gmail.ru";

        Optional<User> byEmail = userRepository.findByEmail(expected);

        assertTrue(byEmail.isPresent());
        assertEquals(expected, byEmail.get().getEmail());
    }

    public User createUser() {
        User user = new User();
        user.setEmail("test@gmail.ru");
        user.setFirstName("Andrey");
        user.setLastName("Vinel");
        user.setEncryptedPassword("P4ssword");
        return user;
    }

}