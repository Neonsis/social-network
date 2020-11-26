package org.neonsis.socialnetwork.model.domain.user;

import org.junit.jupiter.api.Test;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntityBuilderTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link UserBuilderTest} Unit Test.
 *
 * @author neonsis
 */
public class UserBuilderTest extends AbstractBaseEntityBuilderTest<User.UserBuilder> {

    @Override
    protected User.UserBuilder buildTestEntityBuilder() {
        return User.builder();
    }

    /**
     * Test method for
     * {@link User.UserBuilder#email(String)}.
     */
    @Test
    public void testEmail() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().email(null));

        String email = "test@mail.ru";
        assertEquals(email, this.getEntityBuilder().email(email).build().getEmail());
    }

    /**
     * Test method for
     * {@link User.UserBuilder#firstName(String)}.
     */
    @Test
    public void testFirstName() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().firstName(null));

        String firstName = "TEST";
        assertEquals(firstName, this.getEntityBuilder().firstName(firstName).build().getFirstName());
    }

    /**
     * Test method for
     * {@link User.UserBuilder#lastName(String)}.
     */
    @Test
    public void testLastName() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().lastName(null));

        String lastName = "TEST";
        assertEquals(lastName, this.getEntityBuilder().lastName(lastName).build().getLastName());
    }

    /**
     * Test method for
     * {@link User.UserBuilder#password(String)}.
     */
    @Test
    public void testPassword() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().password(null));

        String password = "TEST";
        assertEquals(password, this.getEntityBuilder().password(password).build().getEncryptedPassword());
    }

    /**
     * Test method for
     * {@link User.UserBuilder#avatar(Image)}.
     */
    @Test
    public void testAvatar() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().avatar(null));

        Image avatar = new Image();
        avatar.setId(1L);
        assertEquals(avatar.getId(), this.getEntityBuilder().avatar(avatar).build().getAvatar().getId());
    }
}