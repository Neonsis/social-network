package org.neonsis.socialnetwork.model.dto.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateDto {

    /**
     * The comment's content.
     */
    private String content;

    /**
     * The post id to which this comment is related.
     */
    private Long postId;
}
