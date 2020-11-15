package org.neonsis.socialnetwork.rest.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class PostResponse {

    private String id;
    private String content;
    private UserResponse author;
    private Date createdAt;
    @JsonProperty(value = "isLiked")
    private Boolean isLiked;
    private Integer countLike;
}
