package org.neonsis.socialnetwork.model.domain.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.user.User;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author neonsis
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "conversation")
public class Conversation implements Serializable {

    private static final long serialVersionUID = -4713992279638447750L;

    @EmbeddedId
    private ConversationId id;

    @MapsId("user_one_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User userOne;

    @MapsId("user_two_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User userTwo;

    public static class ConversationBuilder {

        private final Conversation conversation;

        public ConversationBuilder() {
            this.conversation = new Conversation();
        }

        public Conversation build() {
            return conversation;
        }

        public Conversation.ConversationBuilder id(ConversationId conversationId) {
            conversation.setId(conversationId);
            return this;
        }

        public Conversation.ConversationBuilder userOne(User userOne) {
            conversation.setUserOne(userOne);
            return this;
        }

        public Conversation.ConversationBuilder userTwo(User userTwo) {
            conversation.setUserTwo(userTwo);
            return this;
        }
    }
}
