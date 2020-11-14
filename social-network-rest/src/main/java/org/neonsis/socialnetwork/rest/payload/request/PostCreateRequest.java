package org.neonsis.socialnetwork.rest.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PostCreateRequest {

    @NotBlank
    private String content;
}
