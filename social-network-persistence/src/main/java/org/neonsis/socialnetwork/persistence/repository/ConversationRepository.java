package org.neonsis.socialnetwork.persistence.repository;

import org.neonsis.socialnetwork.model.domain.chat.Conversation;
import org.neonsis.socialnetwork.model.domain.chat.ConversationId;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, ConversationId> {

    @Query("SELECT c FROM Conversation c WHERE " +
            "(c.id.userOneId = :userOneId AND c.id.userTwoId = :userTwoId)" +
            "OR" +
            "(c.id.userOneId = :userTwoId AND c.id.userTwoId = :userOneId)")
    Optional<Conversation> findConversationByUsersId(Long userOneId, Long userTwoId);

    @Query("SELECT u FROM User u " +
            " WHERE u.id IN (SELECT CASE" +
            " WHEN c.userOne.id = :userId THEN c.userTwo.id" +
            " ELSE c.userOne.id END" +
            " FROM Conversation c" +
            " WHERE c.userOne.id = :userId OR c.userTwo.id = :userId)"
    )
    Page<User> findUserConversations(Long userId, Pageable pageable);
}
