package org.neonsis.socialnetwork.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.user.Gender;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {

    private Long id;
    private LocalDate birthday;
    private Gender gender;
    private String about;
    private String country;
    private String city;
}
