package org.neonsis.socialnetwork.rest.payload.request;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.user.Gender;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class ProfileUpdateRequest {

    @NotNull
    private LocalDate birthday;
    @NotNull
    private Gender gender;
    private String about;
    private String country;
    private String city;
}
