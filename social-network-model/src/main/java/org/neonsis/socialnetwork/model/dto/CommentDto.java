package org.neonsis.socialnetwork.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentDto {

    private Long id;
    private Long postId;
    private String content;
    private UserDto user;
    private Date createdAt;
}
