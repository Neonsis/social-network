package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.model.domain.post.Comment;
import org.neonsis.socialnetwork.model.dto.post.CommentCreateDto;
import org.neonsis.socialnetwork.model.dto.post.CommentDto;

/**
 * {@link Comment} service interface.
 *
 * @author neonsis
 */
public interface CommentService {

    /**
     * Add a new comment to the post.
     *
     * @param commentDto the comment object describing the comment to add.
     * @param postId the id of the post to which we want add the comment.
     *
     * @return the saved comment.
     *
     * @throws EntityNotFoundException if the post not found.
     */
    CommentDto addCommentToPost(CommentCreateDto commentDto, Long postId);


    /**
     * Delete a comment.
     *
     * @param commentId the id of the comment.
     *
     * @throws EntityNotFoundException if the comment not found.
     */
    void deleteById(Long commentId);
}
