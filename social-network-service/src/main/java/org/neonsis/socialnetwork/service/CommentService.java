package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.model.dto.post.CommentCreateDto;
import org.neonsis.socialnetwork.model.dto.post.CommentDto;

public interface CommentService {

    CommentDto create(CommentCreateDto commentDto);
}
