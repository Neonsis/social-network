package org.neonsis.socialnetwork.persistence.repository;

import org.junit.jupiter.api.Test;
import org.neonsis.socialnetwork.model.domain.user.Friendship;
import org.neonsis.socialnetwork.model.domain.user.Profile;
import org.neonsis.socialnetwork.model.domain.user.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link FriendshipRepositoryTest} Integration Test.
 *
 * @author neonsis
 */
@DataJpaTest
@Sql("/test-data.sql")
class FriendshipRepositoryTest {

    /**
     * The tested repository.
     */
    @Autowired
    private FriendshipRepository friendshipRepository;

    /**
     * Test method for {@link FriendshipRepository#findFriendship(Long, Long)}.
     */
    @Test
    public void testFindFriendshipExists() {
        Optional<Friendship> friendship = friendshipRepository.findFriendship(1L, 2L);

        assertTrue(friendship.isPresent());
        Friendship actual = friendship.get();
        assertEquals(Status.FRIEND, actual.getStatus());
    }

    /**
     * Test method for {@link FriendshipRepository#findFriendship(Long, Long)}.
     */
    @Test
    public void testFindFriendshipNotExists() {
        Optional<Friendship> friendship = friendshipRepository.findFriendship(1L, 4L);

        assertTrue(friendship.isEmpty());
    }

    /**
     * Test method for {@link FriendshipRepository#findFriends(Long, String, Pageable)}.
     */
    @Test
    public void testFindFriends() {
        Page<Profile> friends = friendshipRepository.findFriends(1L, "", null);
        Page<Profile> friends2 = friendshipRepository.findFriends(2L, "", null);
        Page<Profile> friends3 = friendshipRepository.findFriends(3L, "", null);

        assertEquals(1, friends.getSize());
        assertEquals(1, friends2.getSize());
        assertEquals(0, friends3.getSize());
    }

    /**
     * Test method for {@link FriendshipRepository#findFriends(Long, String, Pageable)}.
     */
    @Test
    public void testFindFriendsWithSearch() {
        Page<Profile> friends = friendshipRepository.findFriends(1L, "Test2", null);
        Page<Profile> friends2 = friendshipRepository.findFriends(2L, "", null);
        Page<Profile> friends3 = friendshipRepository.findFriends(3L, "Test3", null);

        assertEquals(1, friends.getSize());
        assertEquals(1, friends2.getSize());
        assertEquals(0, friends3.getSize());
    }

    /**
     * Test method for {@link FriendshipRepository#findFollowers(Long, Pageable)}.
     */
    @Test
    public void testFindFollowers() {
        Page<Profile> friends = friendshipRepository.findFollowers(1L, null);
        Page<Profile> friends2 = friendshipRepository.findFollowers(2L, null);
        Page<Profile> friends3 = friendshipRepository.findFollowers(3L, null);

        assertEquals(0, friends.getSize());
        assertEquals(0, friends2.getSize());
        assertEquals(1, friends3.getSize());
    }
}