package org.neonsis.socialnetwork.model.domain.user;

import org.junit.jupiter.api.Test;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntityBuilderTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link ProfileBuilderTest} Unit Test.
 *
 * @author neonsis
 */
class ProfileBuilderTest extends AbstractBaseEntityBuilderTest<Profile.ProfileBuilder> {

    @Override
    protected Profile.ProfileBuilder buildTestEntityBuilder() {
        return Profile.builder();
    }

    /**
     * Test method for
     * {@link Profile.ProfileBuilder#gender(Gender)}.
     */
    @Test
    public void testGender() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().gender(null));

        Gender gender = Gender.MALE;
        assertEquals(gender, this.getEntityBuilder().gender(gender).build().getGender());
    }

    /**
     * Test method for
     * {@link Profile.ProfileBuilder#birthday(java.time.LocalDate)}.
     */
    @Test
    public void testBirthday() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().birthday(null));

        LocalDate birthday = LocalDate.now();
        assertEquals(birthday, this.getEntityBuilder().birthday(birthday).build().getBirthday());
    }

    /**
     * Test method for
     * {@link Profile.ProfileBuilder#about(String)}.
     */
    @Test
    public void testAbout() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().about(null));

        String about = "TEST";
        assertEquals(about, this.getEntityBuilder().about(about).build().getAbout());
    }

    /**
     * Test method for
     * {@link Profile.ProfileBuilder#country(String)}.
     */
    @Test
    public void testCountry() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().country(null));

        String country = "TEST";
        assertEquals(country, this.getEntityBuilder().country(country).build().getCountry());
    }

    /**
     * Test method for
     * {@link Profile.ProfileBuilder#city(String)}.
     */
    @Test
    public void testCity() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().city(null));

        String city = "TEST";
        assertEquals(city, this.getEntityBuilder().city(city).build().getCity());
    }

    /**
     * Test method for
     * {@link Profile.ProfileBuilder#user(User)}.
     */
    @Test
    public void testUser() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().user(null));

        User user = new User();
        user.setEmail("test@mail.ru");
        assertEquals(user.getEmail(), this.getEntityBuilder().user(user).build().getUser().getEmail());
    }
}