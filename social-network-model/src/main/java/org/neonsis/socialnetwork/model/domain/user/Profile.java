package org.neonsis.socialnetwork.model.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Profile - additional information about {@link User}.
 *
 * @author neonsis
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "profile")
public class Profile extends AbstractBaseEntity {

    /**
     * The {@code serialVersionUID}.
     */
    private static final long serialVersionUID = 4644116734879167994L;

    /**
     * The user gender.
     */
    @Column(name = "gender")
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    /**
     * The user birthday.
     */
    @Column(name = "birthday")
    private LocalDate birthday;

    /**
     * The additional information about user.
     */
    @Column(name = "about", columnDefinition = "TEXT")
    private String about;

    /**
     * The country where the user is from
     */
    @Column(name = "country")
    private String country;

    /**
     * The city where the user is from
     */
    @Column(name = "city")
    private String city;

    /**
     * The user who owns this profile
     */
    @MapsId
    @JoinColumn(name = "id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User user;

    /**
     * Get a new {@link ProfileBuilder}.
     *
     * @return a new {@link ProfileBuilder}.
     */
    public static ProfileBuilder builder() {
        return new ProfileBuilder();
    }

    /**
     * A functional programming {@link ProfileBuilder} builder.
     *
     * @author neonsis
     */
    public static class ProfileBuilder extends AbstractBaseEntity.Builder<Profile> {

        @Override
        protected Profile buildEntity() {
            return new Profile();
        }

        /**
         * Set the user gender and return the builder.
         *
         * @param gender the gender of the user being built.
         * @return the builder.
         * @see Profile#setGender(Gender)
         */
        public ProfileBuilder gender(Gender gender) {
            this.getEntity().setGender(gender);
            return this;
        }

        /**
         * Set the user birthday and return the builder.
         *
         * @param birthday the birthday of the user being built.
         * @return the builder.
         * @see Profile#setBirthday(LocalDate)
         */
        public ProfileBuilder birthday(LocalDate birthday) {
            this.getEntity().setBirthday(birthday);
            return this;
        }

        /**
         * Set the user about and return the builder.
         *
         * @param about the about of the user being built.
         * @return the builder.
         * @see Profile#setAbout(String)
         */
        public ProfileBuilder about(String about) {
            this.getEntity().setAbout(about);
            return this;
        }

        /**
         * Set the user country and return the builder.
         *
         * @param country the country of the user being built.
         * @return the builder.
         * @see Profile#setCountry(String)
         */
        public ProfileBuilder country(String country) {
            this.getEntity().setCountry(country);
            return this;
        }

        /**
         * Set the user city and return the builder.
         *
         * @param city the city of the user being built.
         * @return the builder.
         * @see Profile#setCity(String)
         */
        public ProfileBuilder city(String city) {
            this.getEntity().setCity(city);
            return this;
        }

        /**
         * Set the user and return the builder.
         *
         * @param user the user of the profile being built.
         * @return the builder.
         * @see Profile#setUser(User)
         */
        public ProfileBuilder user(User user) {
            this.getEntity().setUser(user);
            return this;
        }
    }
}
