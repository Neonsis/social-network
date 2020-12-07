package org.neonsis.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.model.domain.chat.Conversation;
import org.neonsis.socialnetwork.model.domain.chat.ConversationId;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.mapper.UserMapper;
import org.neonsis.socialnetwork.model.dto.user.UserDto;
import org.neonsis.socialnetwork.persistence.repository.ConversationRepository;
import org.neonsis.socialnetwork.persistence.repository.UserRepository;
import org.neonsis.socialnetwork.service.ConversationService;
import org.neonsis.socialnetwork.service.security.AuthenticationFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    private final AuthenticationFacade authenticationFacade;

    private final UserMapper userMapper;

    public Optional<ConversationId> findConversationId(Long recipientId, boolean createIfNotExist) {
        User recipient = userRepository.findById(recipientId)
                .orElseThrow(() -> new EntityNotFoundException("User not found by id: " + recipientId));
        Long userId = authenticationFacade.getLoggedInUserId();

        return conversationRepository
                .findConversationByUsersId(userId, recipient.getId())
                .map(Conversation::getId)
                .or(() -> {
                    if (!createIfNotExist) {
                        return Optional.empty();
                    }

                    Conversation conversation = Conversation.builder()
                            .id(
                                    ConversationId.builder()
                                            .userOneId(userId)
                                            .userTwoId(recipient.getId())
                                            .build())
                            .build();

                    conversationRepository.save(conversation);

                    return Optional.of(conversation.getId());
                });
    }

    @Override
    public Page<UserDto> findLoggedInUserConversations(Pageable pageable) {
        Page<User> userConversations
                = conversationRepository.findUserConversations(authenticationFacade.getLoggedInUserId(), pageable);

        return userConversations.map(userMapper::userToDto);
    }
}
