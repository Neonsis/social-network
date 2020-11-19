package org.neonsis.socialnetwork.persistence.repository;

import org.junit.jupiter.api.Test;
import org.neonsis.socialnetwork.model.domain.user.Friendship;
import org.neonsis.socialnetwork.model.domain.user.Status;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class FriendshipRepositoryTest {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Test
    public void testFindFriendshipExists() {
        Optional<Friendship> friendship = friendshipRepository.findFriendship(1L, 2L);

        assertTrue(friendship.isPresent());
        Friendship actual = friendship.get();
        assertEquals(Status.FRIEND, actual.getStatus());
    }

    @Test
    public void testFindFriendshipNotExists() {
        Optional<Friendship> friendship = friendshipRepository.findFriendship(1L, 4L);

        assertTrue(friendship.isEmpty());
    }

    @Test
    public void testFindFriends() {
        Page<User> friends = friendshipRepository.findFriends(1L, null);
        Page<User> friends2 = friendshipRepository.findFriends(2L, null);
        Page<User> friends3 = friendshipRepository.findFriends(3L, null);

        assertEquals(1, friends.getSize());
        assertEquals(1, friends2.getSize());
        assertEquals(0, friends3.getSize());
    }

    @Test
    public void testFindFollowers() {
        Page<User> friends = friendshipRepository.findFollowers(1L, null);
        Page<User> friends2 = friendshipRepository.findFollowers(2L, null);
        Page<User> friends3 = friendshipRepository.findFollowers(3L, null);

        assertEquals(0, friends.getSize());
        assertEquals(0, friends2.getSize());
        assertEquals(1, friends3.getSize());
    }
}