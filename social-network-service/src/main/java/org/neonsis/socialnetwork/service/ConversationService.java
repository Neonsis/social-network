package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.model.domain.chat.ConversationId;
import org.neonsis.socialnetwork.model.dto.chat.ConversationDto;
import org.neonsis.socialnetwork.model.dto.user.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ConversationService {

    Optional<ConversationId> getConversationId(Long recipientId, boolean createIfNotExist);

    Page<UserDto> getLoggedInUserConversations(Pageable pageable);
}
