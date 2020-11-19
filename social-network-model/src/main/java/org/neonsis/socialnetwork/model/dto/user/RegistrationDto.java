package org.neonsis.socialnetwork.model.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.user.Gender;

import java.time.LocalDate;

@Getter
@Setter
public class RegistrationDto {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Gender gender;

    private LocalDate birthday;
}
