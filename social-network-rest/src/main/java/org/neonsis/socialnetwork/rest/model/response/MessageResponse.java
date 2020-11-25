package org.neonsis.socialnetwork.rest.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessageResponse {

    private String id;
    private String content;
    private Date createdAt;
    private UserResponse sender;
}
