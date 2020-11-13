package org.neonsis.socialnetwork.rest.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProfileRequest {

    @NotBlank
    private String about;
    @NotBlank
    private String country;
    @NotBlank
    private String city;
}
