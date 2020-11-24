package org.neonsis.socialnetwork.model.dto.chat;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.dto.user.UserDto;

import java.util.Date;

@Getter
@Setter
public class MessageDto {

    private Long id;

    private String content;

    private ConversationDto conversationDto;

    private UserDto sender;

    private Date createdAt;
}
