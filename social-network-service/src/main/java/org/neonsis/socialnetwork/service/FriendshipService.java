package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.exception.InvalidWorkFlowException;
import org.neonsis.socialnetwork.model.domain.user.Friendship;
import org.neonsis.socialnetwork.model.domain.user.Profile;
import org.neonsis.socialnetwork.model.domain.user.Status;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.dto.user.ProfileDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * {@link Friendship} service interface.
 *
 * @author neonsis
 */
public interface FriendshipService {

    /**
     * Add a user to friends list.
     *
     * <p>
     * When logged in user add a new friend, this friendship is created and has the
     * status {@link Status#FOLLOWER}. Now logged in user is follower of user with id
     * {@code friendId}.
     * <p>
     * To accept the friendship this user should call {@link #addToFriends(Long)}
     * method to. After that the friendship updated with status {@link Status#FRIEND}.
     * </p>
     *
     * @param friendId the id of the future friend.
     *
     * @throws EntityNotFoundException  when the friend not found by id.
     * @throws InvalidWorkFlowException when logged in user add to friend yourself.
     * @throws InvalidWorkFlowException when user with id {@code friendId} already your friends.
     */
    void addToFriends(Long friendId);

    /**
     * Find friends of a user.
     *
     * @param userId   the id of the searched user.
     * @param search   parameter of friends {@link Profile#firstName} and {@link Profile#lastName}.
     * @param pageable paging conditions.
     *
     * @return a page of user friends.
     */
    Page<ProfileDto> findUserFriends(Long userId, String search, Pageable pageable);

    /**
     * Find followers of a user.
     *
     * @param userId   the id of the user.
     * @param pageable paging conditions.
     *
     * @return a page of user followers.
     */
    Page<ProfileDto> findUserFollowers(Long userId, Pageable pageable);

    /**
     * Delete a user friend.
     *
     * @param friendId the id of the friend.
     *
     * @throws EntityNotFoundException if the friend not found.
     */
    void deleteById(Long friendId);

    /**
     * Check if user is our friend.
     *
     * @param userId the id of the user.
     *
     * @return true - if is friend, and false if not.
     */
    boolean isUserFriend(Long userId);

    /**
     * Check if user is our follower.
     *
     * @param userId the id of the user.
     *
     * @return true - if is follower, and false if not.
     */
    boolean isFollower(Long userId);

    /**
     * Check if we already add user to friends, but waiting for an answer.
     *
     * @param userId the id of the user.
     *
     * @return true - if is waiting, and false if not.
     */
    boolean isPendingFriendship(Long userId);
}
