package org.neonsis.socialnetwork.model.dto.chat;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.chat.ConversationId;
import org.neonsis.socialnetwork.model.dto.user.UserDto;

@Getter
@Setter
public class ConversationDto {

    private ConversationId conversationId;

    private UserDto userOne;

    private UserDto userTwo;
}
