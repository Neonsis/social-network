package org.neonsis.socialnetwork.rest.model.response;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.user.Gender;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileResponse {

    private LocalDate birthday;
    private Gender gender;
    private String about;
    private String country;
    private String city;
}
