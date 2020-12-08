package org.neonsis.socialnetwork.rest.controller;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.model.dto.chat.MessageCreateDto;
import org.neonsis.socialnetwork.model.dto.chat.MessageDto;
import org.neonsis.socialnetwork.model.dto.user.ProfileDto;
import org.neonsis.socialnetwork.rest.model.mapper.RestMapper;
import org.neonsis.socialnetwork.rest.model.response.MessageResponse;
import org.neonsis.socialnetwork.rest.model.response.ProfileResponse;
import org.neonsis.socialnetwork.service.ConversationService;
import org.neonsis.socialnetwork.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.neonsis.socialnetwork.rest.util.AppConstants.DEFAULT_PAGE_NUMBER;
import static org.neonsis.socialnetwork.rest.util.AppConstants.DEFAULT_PAGE_SIZE;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    private final MessageService messageService;
    private final ConversationService conversationService;

    private final RestMapper restMapper;

    @MessageMapping("/chat")
    public void processMessage(@Payload MessageCreateDto chatMessage) {
        MessageDto saved = messageService.save(chatMessage);

        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId().toString(),
                "/queue/messages",
                restMapper.messageDtoToResponse(saved)
        );
    }

    @GetMapping("/messages/{recipientId}")
    public Page<MessageResponse> getConversationMessages(
            @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "id", direction = Sort.Direction.DESC)
            }) Pageable pageable,
            @PathVariable Long recipientId
    ) {
        Page<MessageDto> messages = messageService.findConversationMessages(recipientId, pageable);

        return messages.map(restMapper::messageDtoToResponse);
    }

    @GetMapping("/me/conversations")
    public Page<ProfileResponse> getUserConversations(
            @PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable
    ) {
        Page<ProfileDto> conversations = conversationService.findLoggedInUserConversations(pageable);
        return conversations.map(restMapper::profileDtoToResponse);
    }
}
