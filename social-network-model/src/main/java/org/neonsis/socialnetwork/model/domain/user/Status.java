package org.neonsis.socialnetwork.model.domain.user;

/**
 * The status of the friendship.
 *
 * @author neonsis
 */
public enum Status {
    /**
     * Inviter who send invitation to another user, but the invited user doesn't accept this friendship yet.
     */
    FOLLOWER,
    /**
     * Inviter who send invitation to another user, and the invited user accept this friendship.
     */
    FRIEND
}
