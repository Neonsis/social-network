package org.neonsis.socialnetwork.model.dto.chat;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.chat.Conversation;
import org.neonsis.socialnetwork.model.domain.chat.ConversationId;
import org.neonsis.socialnetwork.model.dto.user.ProfileDto;
import org.neonsis.socialnetwork.model.dto.user.UserDto;

/**
 * ConversationDto. {@link Conversation}
 *
 * @author neonsis
 */
@Getter
@Setter
public class ConversationDto {

    /**
     * The Unique Identifier (primary key) of this record.
     */
    private ConversationId id;

    /**
     * The first user in the conversation.
     */
    private ProfileDto userOne;

    /**
     * The second user in the conversation.
     */
    private ProfileDto userTwo;
}
