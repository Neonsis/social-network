package org.neonsis.socialnetwork.model.dto;

import lombok.*;

import java.util.Date;

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
}
