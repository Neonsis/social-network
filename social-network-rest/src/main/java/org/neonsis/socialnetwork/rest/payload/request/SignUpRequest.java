package org.neonsis.socialnetwork.rest.payload.request;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.rest.validation.annotation.UniqueEmail;

@Getter
@Setter
public class SignUpRequest {

    private String firstName;
    private String lastName;
    @UniqueEmail
    private String email;
    private String password;
}
