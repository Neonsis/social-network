package org.neonsis.socialnetwork.rest.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.user.Gender;
import org.neonsis.socialnetwork.model.dto.user.ImageDto;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileDetailsResponse {

    private String id;
    private String firstName;
    private String lastName;
    private ImageDto avatar;
    private LocalDate birthday;
    private Gender gender;
    private String about;
    private String country;
    private String city;
    @JsonProperty(value = "isFriend")
    private boolean isFriend;
    @JsonProperty(value = "isFollower")
    private boolean isFollower;
    @JsonProperty(value = "isPendingFriendship")
    private boolean isPendingFriendship;
}
