package org.neonsis.socialnetwork.model.dto;

import lombok.*;
import org.neonsis.socialnetwork.model.domain.user.Gender;

import java.time.LocalDate;

@Getter
@Setter
@Builder
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
