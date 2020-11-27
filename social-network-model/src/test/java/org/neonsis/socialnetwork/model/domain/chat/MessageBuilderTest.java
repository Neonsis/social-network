package org.neonsis.socialnetwork.model.domain.chat;

import org.junit.jupiter.api.Test;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntityBuilderTest;
import org.neonsis.socialnetwork.model.domain.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link MessageBuilderTest} Unit Test.
 *
 * @author neonsis
 */
class MessageBuilderTest extends AbstractBaseEntityBuilderTest<Message.MessageBuilder> {

    @Override
    protected Message.MessageBuilder buildTestEntityBuilder() {
        return Message.builder();
    }

    /**
     * Test method for
     * {@link Message.MessageBuilder#conversation(Conversation)}.
     */
    @Test
    public void testConversation() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().conversation(null));

        Conversation conversation = Conversation.builder().id(new ConversationId(1L, 2L)).build();
        assertEquals(conversation.getId(), this.getEntityBuilder().conversation(conversation).build().getConversation().getId());
    }

    /**
     * Test method for
     * {@link Message.MessageBuilder#setSender(User)}.
     */
    @Test
    public void testSender() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().sender(null));

        User sender = User.builder().id(1L).build();
        assertEquals(sender.getId(), this.getEntityBuilder().sender(sender).build().getSender().getId());
    }

    /**
     * Test method for
     * {@link Message.MessageBuilder#setContent(String)}.
     */
    @Test
    public void testContent() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().content(null));

        String content = "content";
        assertEquals(content, this.getEntityBuilder().content(content).build().getContent());
    }
}