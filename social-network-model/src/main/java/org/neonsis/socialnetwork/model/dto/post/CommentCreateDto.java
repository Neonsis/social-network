package org.neonsis.socialnetwork.model.dto.post;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.post.Comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

/**
 * CommentCreateDto. {@link Comment}
 *
 * @author neonsis
 */
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
