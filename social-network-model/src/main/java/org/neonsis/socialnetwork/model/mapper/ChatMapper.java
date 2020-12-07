package org.neonsis.socialnetwork.model.mapper;

import org.mapstruct.Mapper;
import org.neonsis.socialnetwork.model.domain.chat.Message;
import org.neonsis.socialnetwork.model.dto.chat.MessageDto;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    MessageDto messageToDto(Message message);
}
