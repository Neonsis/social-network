package org.neonsis.socialnetwork.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.user.Gender;
import org.neonsis.socialnetwork.model.dto.base.AbstractBaseDto;

import java.time.LocalDate;


/**
 * ProfileDto.
 *
 * @author neonsis
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto extends AbstractBaseDto {

    /**
     * The user's first name.
     */
    private String firstName;

    /**
     * The user's last name.
     */
    private String lastName;

    /**
     * The user's main avatar.
     */
    private ImageDto avatar;

    /**
     * The user's birthday.
     */
    private LocalDate birthday;

    /**
     * The user's gender.
     */
    private Gender gender;

    /**
     * The additional information about user.
     */
    private String about;

    /**
     * The country where the user is from
     */
    private String country;

    /**
     * The city where the user is from
     */
    private String city;
}
