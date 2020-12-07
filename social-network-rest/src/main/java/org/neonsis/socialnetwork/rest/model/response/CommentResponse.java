package org.neonsis.socialnetwork.rest.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentResponse {

    private String id;
    private String content;
    private String postId;
    private UserResponse user;
    private Date createdAt;
}
