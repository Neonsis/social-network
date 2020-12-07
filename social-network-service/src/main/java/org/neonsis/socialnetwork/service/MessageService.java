package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.model.domain.chat.Message;
import org.neonsis.socialnetwork.model.dto.chat.MessageCreateDto;
import org.neonsis.socialnetwork.model.dto.chat.MessageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * {@link Message service interface.
 *
 * @author neonsis
 */
public interface MessageService {

    /**
     * Create a new message.
     *
     * @param messageCreateDto a message object describing the message to create.
     *
     * @return the new message.
     */
    MessageDto save(MessageCreateDto messageCreateDto);

    /**
     * Find messages related to the certain conversation.
     *
     * @param recipientId a message object describing the message to create.
     * @param pageable    paging conditions.
     *
     * @return a page of messages.
     */
    Page<MessageDto> findConversationMessages(Long recipientId, Pageable pageable);
}
