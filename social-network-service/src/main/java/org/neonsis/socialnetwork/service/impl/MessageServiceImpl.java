package org.neonsis.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.model.domain.chat.Conversation;
import org.neonsis.socialnetwork.model.domain.chat.ConversationId;
import org.neonsis.socialnetwork.model.domain.chat.Message;
import org.neonsis.socialnetwork.model.domain.user.Profile;
import org.neonsis.socialnetwork.model.dto.chat.MessageCreateDto;
import org.neonsis.socialnetwork.model.dto.chat.MessageDto;
import org.neonsis.socialnetwork.model.mapper.ChatMapper;
import org.neonsis.socialnetwork.persistence.repository.ConversationRepository;
import org.neonsis.socialnetwork.persistence.repository.MessageRepository;
import org.neonsis.socialnetwork.persistence.repository.ProfileRepository;
import org.neonsis.socialnetwork.service.ConversationService;
import org.neonsis.socialnetwork.service.MessageService;
import org.neonsis.socialnetwork.service.security.AuthenticationFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final ConversationService conversationService;

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final ProfileRepository profileRepository;

    private final AuthenticationFacade authenticationFacade;

    private final ChatMapper chatMapper;

    @Override
    @Transactional
    public MessageDto save(MessageCreateDto messageCreateDto) {
        ConversationId conversationId =
                conversationService.findConversationId(messageCreateDto.getRecipientId(), true).get(); // always exists

        Long loggedInUserId = authenticationFacade.getLoggedInUserId();
        Profile profile = profileRepository.findById(loggedInUserId)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found by id: " + loggedInUserId));

        Conversation conversation = conversationRepository.findById(conversationId).get();

        Message message = Message.builder()
                .sender(profile)
                .content(messageCreateDto.getContent())
                .conversation(conversation)
                .build();

        messageRepository.save(message);

        return chatMapper.messageToDto(message);
    }

    @Override
    public Page<MessageDto> findConversationMessages(Long recipientId, Pageable pageable) {
        Optional<ConversationId> conversationId = conversationService.findConversationId(recipientId, false);

        // If it's empty then logged in user never sent messages to the recipient
        if (conversationId.isEmpty()) {
            return new PageImpl<>(Collections.emptyList());
        }

        Page<Message> messages = messageRepository.findMessagesByConversationId(conversationId.get(), pageable);

        return messages.map(chatMapper::messageToDto);
    }
}
