package org.neonsis.socialnetwork.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String uuid;
    private String email;
    private String password;
    private String encryptedPassword;
    private String firstName;
    private String lastName;
    private String avatarUrl;
}
