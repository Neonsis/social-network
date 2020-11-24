package org.neonsis.socialnetwork.model.domain.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ConversationId implements Serializable {

    private static final long serialVersionUID = -18563883524075695L;

    @Column(name = "user_one_id")
    private Long userOneId;

    @Column(name = "user_two_id")
    private Long userTwoId;

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

    public static ConversationIdBuilder builder() {
        return new ConversationIdBuilder();
    }

    public static class ConversationIdBuilder {

        private final ConversationId conversationId;

        public ConversationIdBuilder() {
            this.conversationId = new ConversationId();
        }

        public ConversationId build() {
            return conversationId;
        }

        public ConversationId.ConversationIdBuilder userOneId(Long id) {
            conversationId.setUserOneId(id);
            return this;
        }

        public ConversationId.ConversationIdBuilder userTwoId(Long id) {
            conversationId.setUserTwoId(id);
            return this;
        }
    }
}
