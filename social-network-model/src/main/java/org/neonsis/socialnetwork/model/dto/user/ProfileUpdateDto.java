package org.neonsis.socialnetwork.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.user.Gender;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * ProfileUpdateDto.
 *
 * @author neonsis
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateDto {

    /**
     * The user's birthday.
     */
    @NotNull(message = "{config.data.validation.profile_birthday_not_null}")
    private LocalDate birthday;

    /**
     * The user's gender.
     */
    @NotNull(message = "{config.data.validation.profile_gender_not_null}")
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
