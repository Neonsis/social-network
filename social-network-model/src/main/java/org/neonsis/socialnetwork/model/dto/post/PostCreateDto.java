package org.neonsis.socialnetwork.model.dto.post;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PostCreateDto {

    /**
     * The post's content.
     */
    @NotBlank
    private String content;
}
