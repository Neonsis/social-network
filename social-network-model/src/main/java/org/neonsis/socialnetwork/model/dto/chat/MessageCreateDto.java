package org.neonsis.socialnetwork.model.dto.chat;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.chat.Message;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * MessageCreateDto. {@link Message}
 *
 * @author neonsis
 */
@Getter
@Setter
public class MessageCreateDto {

    /**
     * The user id to which this message is sent to
     */
    @NotNull(message = "{config.data.validation.message_recipient_id_not_null=}")
    private Long recipientId;

    /**
     * The content of this message.
     */
    @NotBlank(message = "{config.data.validation.message_content_not_null}")
    private String content;
}
