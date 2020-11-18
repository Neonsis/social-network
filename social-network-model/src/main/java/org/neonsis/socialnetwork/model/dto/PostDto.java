package org.neonsis.socialnetwork.model.dto;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private String id;
    private String content;
    private UserDto author;
    private Date createdAt;
    private Boolean isLiked;
    private Integer countLike;
    private Set<CommentDto> comments;
}
