package org.neonsis.socialnetwork.model.domain.chat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * {@link ConversationIdBuilderTest} Unit Test.
 *
 * @author neonsis
 */
class ConversationIdBuilderTest {

    /**
     * The tested entity builder.
     */
    private ConversationId.ConversationIdBuilder conversationIdBuilder;

    /**
     * @throws java.lang.Exception If test initialization crashes.
     */
    @BeforeEach
    public void setUp() throws Exception {
        this.conversationIdBuilder = ConversationId.builder();

        assertNotNull(conversationIdBuilder, "The tested conversation id builder cannot be null!");
    }

    /**
     * @throws java.lang.Exception If test clean up crashes.
     */
    @AfterEach
    public void tearDown() throws Exception {
        this.conversationIdBuilder = null;
    }

    /**
     * Test method for {@link ConversationId.ConversationIdBuilder#build()}.
     */
    @Test
    public void testBuild() {
        assertNotNull(conversationIdBuilder.build());
    }

    /**
     * Test method for {@link ConversationId.ConversationIdBuilder#userOneId(Long)}.
     */
    @Test
    public void testUserOneId() {
        assertEquals(conversationIdBuilder, conversationIdBuilder.userOneId(null));

        Long userOneId = 1L;
        assertEquals(userOneId, conversationIdBuilder.userOneId(userOneId).build().getUserOneId());
    }

    /**
     * Test method for {@link ConversationId.ConversationIdBuilder#userTwoId(Long)}.
     */
    @Test
    public void testUserTwoId() {
        assertEquals(conversationIdBuilder, conversationIdBuilder.userTwoId(null));

        Long userTwoId = 1L;
        assertEquals(userTwoId, conversationIdBuilder.userTwoId(userTwoId).build().getUserTwoId());
    }
}