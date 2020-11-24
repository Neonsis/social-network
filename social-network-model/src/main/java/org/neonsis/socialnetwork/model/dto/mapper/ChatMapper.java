package org.neonsis.socialnetwork.model.dto.mapper;

import org.mapstruct.Mapper;
import org.neonsis.socialnetwork.model.domain.chat.Conversation;
import org.neonsis.socialnetwork.model.domain.chat.Message;
import org.neonsis.socialnetwork.model.dto.chat.ConversationDto;
import org.neonsis.socialnetwork.model.dto.chat.MessageDto;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    MessageDto messageToDto(Message message);
}
