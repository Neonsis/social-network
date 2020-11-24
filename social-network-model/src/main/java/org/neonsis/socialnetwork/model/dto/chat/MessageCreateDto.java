package org.neonsis.socialnetwork.model.dto.chat;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MessageCreateDto {

    @NotNull
    private Long recipientId;

    @NotBlank
    private String content;
}
