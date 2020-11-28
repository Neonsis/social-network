package org.neonsis.socialnetwork.model.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.neonsis.socialnetwork.model.domain.chat.Conversation;
import org.neonsis.socialnetwork.model.domain.chat.ConversationId;
import org.neonsis.socialnetwork.model.domain.chat.Message;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.dto.base.AbstractBaseDto;
import org.neonsis.socialnetwork.model.dto.chat.MessageDto;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.neonsis.socialnetwork.model.mapper.MapperDtoAssertUtilTest.*;

/**
 * {@link ChatMapperTest} Unit Test.
 *
 * @author neonsis
 */
class ChatMapperTest {

    /**
     * The tested mapper.
     */
    private final ChatMapper chatMapper = Mappers.getMapper(ChatMapper.class);

    /**
     * Test method to check correct mapping of {@link MessageDto#content}
     */
    @Test
    public void testMessageToDtoContent() {
        Message expected = Message.builder()
                .content("CONTENT")
                .build();

        MessageDto actual = chatMapper.messageToDto(expected);

        assertNotNull(actual.getContent());
        assertEquals(expected.getContent(), actual.getContent());
    }

    /**
     * Test method to check correct mapping of {@link MessageDto#conversation
     */
    @Test
    public void testMessageToDtoConversation() {
        User userOne = createUser();

        User userTwo = createUser();

        Conversation conversation = Conversation.builder()
                .id(new ConversationId(1L, 2L))
                .userOne(userOne)
                .userTwo(userTwo)
                .build();

        Message expected = Message.builder()
                .conversation(conversation)
                .build();


        MessageDto actual = chatMapper.messageToDto(expected);

        assertTrue(isEqualToConversation(actual.getConversation(), conversation));
    }

    /**
     * Test method to check correct mapping of {@link MessageDto#s}ender
     */
    @Test
    public void testMessageToDtoSender() {
        User userOne = createUser();

        Message expected = Message.builder()
                .sender(userOne)
                .build();

        MessageDto actual = chatMapper.messageToDto(expected);

        assertTrue(isEqualToUser(actual.getSender(), userOne));
    }

    /**
     * Test method to check correct mapping of {@link AbstractBaseDto} fields
     */
    @Test
    public void testMessageToDtoBaseDto() {
        Message message = Message.builder()
                .id(1L)
                .build();

        message.setCreatedAt(Date.from(Instant.now()));
        message.setUpdatedAt(Date.from(Instant.now()));

        MessageDto expected = chatMapper.messageToDto(message);

        assertTrue(isEqualToAbstractDto(expected, message));
    }
}