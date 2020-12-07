package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.model.domain.chat.Conversation;
import org.neonsis.socialnetwork.model.domain.chat.ConversationId;
import org.neonsis.socialnetwork.model.dto.user.ProfileDto;
import org.neonsis.socialnetwork.model.dto.user.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * {@link Conversation} service interface.
 *
 * @author neonsis
 */
public interface ConversationService {

    /**
     * Find a conversation by id.
     *
     * @param recipientId      the id of the recipient.
     * @param createIfNotExist should we create conversation if it not exists.
     *
     * @return optional of conversation id.
     */
    Optional<ConversationId> findConversationId(Long recipientId, boolean createIfNotExist);

    /**
     * Find logged in user conversations.
     *
     * @param pageable paging conditions.
     *
     * @return a page of users with whom logged in user has a conversation.
     */
    Page<ProfileDto> findLoggedInUserConversations(Pageable pageable);
}
