package org.neonsis.socialnetwork.model.dto.post;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Getter
@Setter
public class CommentCreateDto {

    /**
     * The comment's content.
     */
    @NotBlank(message = "{config.data.validation.comment_content_not_null}")
    private String content;

    /**
     * The post id to which this comment is related.
     */
    @Null
    private Long postId;
}
