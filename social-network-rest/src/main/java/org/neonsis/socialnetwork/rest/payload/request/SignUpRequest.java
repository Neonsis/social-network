package org.neonsis.socialnetwork.rest.payload.request;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.user.Gender;
import org.neonsis.socialnetwork.rest.validation.annotation.UniqueEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
public class SignUpRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @UniqueEmail
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    private String password;

    @NotNull
    private Gender gender;

    @NotNull
    private LocalDate birthday;
}
