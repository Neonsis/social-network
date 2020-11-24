package org.neonsis.socialnetwork.model.dto.chat;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MessageCreateDto {

    @NotNull(message = "{config.data.validation.message_recipient_id_not_null=}")
    private Long recipientId;

    @NotBlank(message = "{config.data.validation.message_content_not_null}")
    private String content;
}
