package org.neonsis.socialnetwork.model.domain.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.user.User;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Conversation is stored messages between two users. It is established when one of the user send first message.
 *
 * @author neonsis
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "conversation")
public class Conversation implements Serializable {

    /**
     * The {@code serialVersionUID}.
     */
    private static final long serialVersionUID = -4713992279638447750L;

    /**
     * The Unique Identifier (primary key) of this record.
     */
    @EmbeddedId
    private ConversationId id;

    /**
     * The first user in the conversation.
     */
    @MapsId("user_one_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User userOne;

    /**
     * The second user in the conversation.
     */
    @MapsId("user_two_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User userTwo;

    /**
     * Get a new {@link ConversationBuilder}.
     *
     * @return a new {@link ConversationBuilder}.
     */
    public static ConversationBuilder builder() {
        return new ConversationBuilder();
    }

    /**
     * A functional programming {@link ConversationBuilder} builder.
     *
     * @author neonsis
     */
    public static class ConversationBuilder {

        /**
         * The conversation currently being built.
         */
        private final Conversation conversation;

        /**
         * Create a {@link Conversation.ConversationBuilder} with no initial property.
         */
        public ConversationBuilder() {
            this.conversation = new Conversation();
        }

        /**
         * Build the conversation prepared by the builder.
         *
         * @return a new conversation as prepared by the builder.
         */
        public Conversation build() {
            return conversation;
        }


        /**
         * Set the id and return the builder.
         *
         * @param conversationId the id of the conversation being built.
         * @return the builder.
         * @see Conversation#setId(ConversationId)
         */
        public Conversation.ConversationBuilder id(ConversationId conversationId) {
            conversation.setId(conversationId);
            return this;
        }

        /**
         * Set the user one and return the builder.
         *
         * @param userOne the first user of the conversation being built.
         * @return the builder.
         * @see Conversation#setUserOne(User)
         */
        public Conversation.ConversationBuilder userOne(User userOne) {
            conversation.setUserOne(userOne);
            return this;
        }

        /**
         * Set the user two and return the builder.
         *
         * @param userTwo the second user of the conversation being built.
         * @return the builder.
         * @see Conversation#setUserTwo(User)
         */
        public Conversation.ConversationBuilder userTwo(User userTwo) {
            conversation.setUserTwo(userTwo);
            return this;
        }
    }
}
