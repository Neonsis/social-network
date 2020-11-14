package org.neonsis.socialnetwork.rest.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class PostResponse {

    private String content;
    private UserResponse author;
    private Date createdAt;
}
