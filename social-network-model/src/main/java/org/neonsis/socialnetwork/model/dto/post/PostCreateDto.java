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
    @NotBlank(message = "{config.data.validation.post_content_not_null}")
    private String content;
}
