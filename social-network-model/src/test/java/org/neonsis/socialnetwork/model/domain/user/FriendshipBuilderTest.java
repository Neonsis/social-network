package org.neonsis.socialnetwork.model.domain.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * {@link FriendshipBuilderTest} Unit Test.
 *
 * @author neonsis
 */
public class FriendshipBuilderTest {

    /**
     * The tested entity builder.
     */
    private Friendship.FriendshipBuilder friendshipBuilder;

    /**
     * @throws java.lang.Exception If test initialization crashes.
     */
    @BeforeEach
    public void setUp() throws Exception {
        this.friendshipBuilder = Friendship.builder();

        assertNotNull(friendshipBuilder, "The tested friendship builder cannot be null!");
    }

    /**
     * @throws java.lang.Exception If test clean up crashes.
     */
    @AfterEach
    public void tearDown() throws Exception {
        this.friendshipBuilder = null;
    }

    /**
     * Test method for {@link Friendship.FriendshipBuilder#build()}.
     */
    @Test
    public void testBuild() {
        assertNotNull(friendshipBuilder.build());
    }

    /**
     * Test method for {@link Friendship.FriendshipBuilder#id(FriendshipId)}.
     */
    @Test
    public void testId() {
        assertEquals(friendshipBuilder, friendshipBuilder.id(null));

        FriendshipId friendshipId = new FriendshipId(1L, 2L);
        assertEquals(friendshipId, friendshipBuilder.id(friendshipId).build().getId());
    }

    /**
     * Test method for
     * {@link Friendship.FriendshipBuilder#inviter(User))}.
     */
    @Test
    public void testInviter() {
        assertEquals(friendshipBuilder, friendshipBuilder.inviter(null));

        User user = new User();
        user.setEmail("test@mail.ru");
        assertEquals(user.getEmail(), friendshipBuilder.inviter(user).build().getInviter().getEmail());
    }

    /**
     * Test method for
     * {@link Friendship.FriendshipBuilder#invited(User))}.
     */
    @Test
    public void testInvited() {
        assertEquals(friendshipBuilder, friendshipBuilder.invited(null));

        User user = new User();
        user.setEmail("test@mail.ru");
        assertEquals(user.getEmail(), friendshipBuilder.invited(user).build().getInvited().getEmail());
    }

    /**
     * Test method for
     * {@link Friendship.FriendshipBuilder#status(Status))}.
     */
    @Test
    public void testStatus() {
        assertEquals(friendshipBuilder, friendshipBuilder.status(null));

        Status status = Status.FOLLOWER;
        assertEquals(status, friendshipBuilder.status(status).build().getStatus());
    }
}