package org.neonsis.socialnetwork.rest.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Getter
@Setter
public class PostResponse {

    private String id;

    private String content;

    private ProfileResponse author;

    private CommunityResponse community;

    private Date createdAt;

    @JsonProperty(value = "isLiked")
    private Boolean isLiked;

    private Integer countLike;

    private List<CommentResponse> comments;
}
