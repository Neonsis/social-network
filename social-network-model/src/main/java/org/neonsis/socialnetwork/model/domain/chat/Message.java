package org.neonsis.socialnetwork.model.domain.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntity;
import org.neonsis.socialnetwork.model.domain.user.User;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "chat_message")
public class Message extends AbstractBaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE})
    private Conversation conversation;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE})
    private User sender;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;
}
