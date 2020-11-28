package org.neonsis.socialnetwork.model.dto.chat;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.chat.Message;
import org.neonsis.socialnetwork.model.dto.base.AbstractBaseDto;
import org.neonsis.socialnetwork.model.dto.user.UserDto;

/**
 * MessageDto. {@link Message}
 *
 * @author neonsis
 */
@Getter
@Setter
public class MessageDto extends AbstractBaseDto {

    /**
     * The content of this message.
     */
    private String content;

    /**
     * The conversation to which this message belongs to.
     */
    private ConversationDto conversation;

    /**
     * The user who sent this message.
     */
    private UserDto sender;
}
