package org.neonsis.socialnetwork.model.dto.user;

import lombok.*;
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
@Builder
public class ProfileDto extends AbstractBaseDto {

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
