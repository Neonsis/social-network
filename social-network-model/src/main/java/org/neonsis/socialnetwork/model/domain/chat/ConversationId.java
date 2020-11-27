package org.neonsis.socialnetwork.model.domain.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * The Unique Identifier (primary key) of the {@link Conversation}.
 *
 * @author neonsis
 */
@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ConversationId implements Serializable {

    /**
     * The {@code serialVersionUID}.
     */
    private static final long serialVersionUID = -18563883524075695L;

    /**
     * The first user id in the conversation.
     */
    @Column(name = "user_one_id", nullable = false, updatable = false)
    private Long userOneId;

    /**
     * The second user id in the conversation.
     */
    @Column(name = "user_two_id", nullable = false, updatable = false)
    private Long userTwoId;

    @Override
    public String toString() {
        return "ConversationId{" +
                "userOneId=" + userOneId +
                ", userTwoId=" + userTwoId +
                '}';
    }

    /**
     * Check objects equality by {@link #userOneId} and {@link #userTwoId}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversationId that = (ConversationId) o;
        return Objects.equals(userOneId, that.userOneId) &&
                Objects.equals(userTwoId, that.userTwoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userOneId, userTwoId);
    }

    /**
     * Get a new {@link ConversationIdBuilder}.
     *
     * @return a new {@link ConversationIdBuilder}.
     */
    public static ConversationIdBuilder builder() {
        return new ConversationIdBuilder();
    }

    /**
     * A functional programming {@link ConversationIdBuilder} builder.
     *
     * @author neonsis
     */
    public static class ConversationIdBuilder {

        /**
         * The conversationId currently being built.
         */
        private final ConversationId conversationId;

        /**
         * Create a {@link ConversationId.ConversationIdBuilder} with no initial property.
         */
        public ConversationIdBuilder() {
            this.conversationId = new ConversationId();
        }

        /**
         * Build the conversation id prepared by the builder.
         *
         * @return a new conversation id as prepared by the builder.
         */
        public ConversationId build() {
            return conversationId;
        }

        /**
         * Set the user one id and return the builder.
         *
         * @param id the first user id of the conversation id being built.
         * @return the builder.
         * @see ConversationId#setUserOneId(Long)
         */
        public ConversationId.ConversationIdBuilder userOneId(Long id) {
            conversationId.setUserOneId(id);
            return this;
        }

        /**
         * Set the user two id and return the builder.
         *
         * @param id the second user id of the conversation id being built.
         * @return the builder.
         * @see ConversationId#setUserTwoId(Long)
         */
        public ConversationId.ConversationIdBuilder userTwoId(Long id) {
            conversationId.setUserTwoId(id);
            return this;
        }
    }
}
