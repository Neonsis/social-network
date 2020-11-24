package org.neonsis.socialnetwork.model.domain.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntity;
import org.neonsis.socialnetwork.model.domain.user.User;

import javax.persistence.*;

/**
 * Message that user send to other user.
 *
 * @author neonsis
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "chat_message")
public class Message extends AbstractBaseEntity {

    /**
     * The {@code serialVersionUID}.
     */
    private static final long serialVersionUID = 6416334226530105859L;

    /**
     * The conversation to which this message belongs to.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE})
    private Conversation conversation;

    /**
     * The user who sent this message.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE})
    private User sender;

    /**
     * The content of this message.
     */
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    /**
     * A functional programming {@link MessageBuilder} builder.
     *
     * @author neonsis
     */
    public static class MessageBuilder extends AbstractBaseEntity.Builder<Message> {

        @Override
        protected Message buildEntity() {
            return new Message();
        }

        /**
         * Set the conversation and return the builder.
         *
         * @param conversation the conversation of the message being built.
         * @return the builder.
         * @see Message#setConversation(Conversation)
         */
        public MessageBuilder conversation(Conversation conversation) {
            this.getEntity().setConversation(conversation);
            return this;
        }

        /**
         * Set the sender and return the builder.
         *
         * @param sender the sender of the message being built.
         * @return the builder.
         * @see Message#setSender(User)
         */
        public MessageBuilder sender(User sender) {
            this.getEntity().setSender(sender);
            return this;
        }

        /**
         * Set the message content and return the builder.
         *
         * @param content the content of the message being built.
         * @return the builder.
         * @see Message#setContent(String)
         */
        public MessageBuilder content(String content) {
            this.getEntity().setContent(content);
            return this;
        }
    }
}
