package org.neonsis.socialnetwork.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.user.Gender;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {

    @NotBlank(message = "{user.first-name.not-empty}")
    private String firstName;

    @NotBlank(message = "{user.last-name.not-empty}")
    private String lastName;

    @Email(message = "{user.email.valid}")
    @NotBlank(message = "{user.email.not-empty}")
    private String email;

    @NotBlank(message = "{user.password.not-empty}")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "{user.password.valid}")
    private String password;

    @NotNull(message = "{user.gender.not-empty}")
    private Gender gender;

    @NotNull(message = "{user.birthday.not-empty}")
    private LocalDate birthday;
}
