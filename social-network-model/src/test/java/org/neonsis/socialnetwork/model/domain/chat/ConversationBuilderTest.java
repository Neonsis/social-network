package org.neonsis.socialnetwork.model.domain.chat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neonsis.socialnetwork.model.domain.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * {@link ConversationBuilderTest} Unit Test.
 *
 * @author neonsis
 */
class ConversationBuilderTest {

    /**
     * The tested entity builder.
     */
    private Conversation.ConversationBuilder conversationBuilder;

    /**
     * @throws java.lang.Exception If test initialization crashes.
     */
    @BeforeEach
    public void setUp() throws Exception {
        this.conversationBuilder = Conversation.builder();

        assertNotNull(conversationBuilder, "The tested conversation builder cannot be null!");
    }

    /**
     * @throws java.lang.Exception If test clean up crashes.
     */
    @AfterEach
    public void tearDown() throws Exception {
        this.conversationBuilder = null;
    }

    /**
     * Test method for {@link Conversation.ConversationBuilder#build()}.
     */
    @Test
    public void testBuild() {
        assertNotNull(conversationBuilder.build());
    }

    /**
     * Test method for {@link Conversation.ConversationBuilder#id(ConversationId)}.
     */
    @Test
    public void testId() {
        assertEquals(conversationBuilder, conversationBuilder.id(null));

        ConversationId conversationId = new ConversationId(1L, 2L);
        assertEquals(conversationId, conversationBuilder.id(conversationId).build().getId());
    }

    /**
     * Test method for {@link Conversation.ConversationBuilder#userOne(User)}.
     */
    @Test
    public void testUserOne() {
        assertEquals(conversationBuilder, conversationBuilder.userOne(null));

        User userOne = User.builder().id(1L).build();
        assertEquals(userOne.getId(), conversationBuilder.userOne(userOne).build().getUserOne().getId());
    }

    /**
     * Test method for {@link Conversation.ConversationBuilder#userTwo(User)}.
     */
    @Test
    public void testUserTwo() {
        assertEquals(conversationBuilder, conversationBuilder.userTwo(null));

        User userTwo = User.builder().id(1L).build();
        assertEquals(userTwo.getId(), conversationBuilder.userOne(userTwo).build().getUserOne().getId());
    }
}