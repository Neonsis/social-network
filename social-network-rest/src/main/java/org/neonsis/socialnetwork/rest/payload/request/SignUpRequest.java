package org.neonsis.socialnetwork.rest.payload.request;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.user.Gender;
import org.neonsis.socialnetwork.rest.validation.annotation.UniqueEmail;

import java.time.LocalDate;

@Getter
@Setter
public class SignUpRequest {

    private String firstName;
    private String lastName;
    @UniqueEmail
    private String email;
    private String password;
    private Gender gender;
    private LocalDate birthday;
}
