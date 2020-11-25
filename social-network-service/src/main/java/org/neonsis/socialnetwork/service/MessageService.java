package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.model.domain.chat.ConversationId;
import org.neonsis.socialnetwork.model.dto.chat.MessageCreateDto;
import org.neonsis.socialnetwork.model.dto.chat.MessageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {

    MessageDto save(MessageCreateDto messageCreateDto);

    Page<MessageDto> findConversationMessages(Long recipientId, Pageable pageable);
}
