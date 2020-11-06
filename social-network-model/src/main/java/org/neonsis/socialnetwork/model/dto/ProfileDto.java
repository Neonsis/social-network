package org.neonsis.socialnetwork.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {

    private Long id;
    private LocalDateTime birthday;
    private String about;
    private String country;
    private String city;
}
