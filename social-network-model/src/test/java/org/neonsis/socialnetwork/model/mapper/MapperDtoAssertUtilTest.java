package org.neonsis.socialnetwork.model.mapper;

import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntity;
import org.neonsis.socialnetwork.model.domain.chat.Conversation;
import org.neonsis.socialnetwork.model.domain.community.Community;
import org.neonsis.socialnetwork.model.domain.post.Comment;
import org.neonsis.socialnetwork.model.domain.post.Post;
import org.neonsis.socialnetwork.model.domain.user.Image;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.dto.base.AbstractBaseDto;
import org.neonsis.socialnetwork.model.dto.chat.ConversationDto;
import org.neonsis.socialnetwork.model.dto.community.CommunityDto;
import org.neonsis.socialnetwork.model.dto.post.CommentDto;
import org.neonsis.socialnetwork.model.dto.user.ImageDto;
import org.neonsis.socialnetwork.model.dto.user.UserDto;

import java.time.Instant;
import java.util.Date;

/**
 * {@link MapperDtoAssertUtilTest} utility class for mappers tests.
 *
 * @author neonsis
 */
public class MapperDtoAssertUtilTest {

    /**
     * Check {@link Community} and {@link CommunityDto} mapping, by comparing all fields
     * in {@link CommunityDto} and {@link AbstractBaseDto}.
     *
     * @param communityDto the comparable dto.
     * @param community    the comparable entity.
     * @return true - if all fields are equal, and false if not.
     */
    public static boolean isEqualToCommunity(CommunityDto communityDto, Community community) {
        return isEqualToAbstractDto(communityDto, community)
                && communityDto.getTitle().equals(community.getTitle())
                && isEqualToUser(communityDto.getModerator(), community.getModerator())
                && isEqualToImage(communityDto.getAvatar(), community.getAvatar());
    }

    /**
     * Check {@link Comment} and {@link CommentDto} mapping, by comparing all fields
     * in {@link CommentDto} and {@link AbstractBaseDto}.
     *
     * @param commentDto the comparable dto.
     * @param comment    the comparable entity.
     * @return true - if all fields are equal, and false if not.
     */
    public static boolean isEqualToComment(CommentDto commentDto, Comment comment) {
        return isEqualToAbstractDto(commentDto, comment)
                && commentDto.getContent().equals(comment.getContent())
                && isEqualToUser(commentDto.getUser(), comment.getUser());
    }

    /**
     * Check {@link ConversationDto} and {@link Conversation} mapping, by comparing all fields
     * in {@link ConversationDto}.
     *
     * @param conversationDto the comparable dto.
     * @param conversation    the comparable entity.
     * @return true - if all fields are equal, and false if not.
     */
    public static boolean isEqualToConversation(ConversationDto conversationDto, Conversation conversation) {
        return conversationDto.getId().equals(conversation.getId())
                && isEqualToUser(conversationDto.getUserOne(), conversation.getUserOne())
                && isEqualToUser(conversationDto.getUserTwo(), conversation.getUserTwo());
    }

    /**
     * Check {@link UserDto} and {@link User} mapping, by comparing all fields
     * in {@link UserDto}.
     *
     * @param userDto the comparable dto.
     * @param user    the comparable entity.
     * @return true - if all fields are equal, and false if not.
     */
    public static boolean isEqualToUser(UserDto userDto, User user) {
        return isEqualToAbstractDto(userDto, user)
                && isEqualToImage(userDto.getAvatar(), user.getAvatar())
                && userDto.getEmail().equals(user.getEmail())
                && userDto.getEncryptedPassword().equals(user.getEncryptedPassword())
                && userDto.getFirstName().equals(user.getFirstName())
                && userDto.getLastName().equals(user.getLastName());
    }

    /**
     * Check {@link ImageDto} and {@link Image} mapping, by comparing all fields
     * in {@link ImageDto}.
     *
     * @param imageDto the comparable dto.
     * @param image    the comparable entity.
     * @return true - if all fields are equal, and false if not.
     */
    public static boolean isEqualToImage(ImageDto imageDto, Image image) {
        return isEqualToAbstractDto(imageDto, image)
                && imageDto.getImageId().equals(image.getImageId())
                && imageDto.getOriginalUrl().equals(image.getOriginalUrl());
    }

    /**
     * Check {@link AbstractBaseDto} and {@link AbstractBaseEntity} mapping, by comparing all fields
     * in {@link ImageDto}.
     *
     * @param dto    the comparable dto.
     * @param entity the comparable entity.
     * @return true - if all fields are equal, and false if not.
     */
    public static <T extends AbstractBaseDto, E extends AbstractBaseEntity> boolean isEqualToAbstractDto(T dto, E entity) {
        return dto.getId().equals(entity.getId())
                && dto.getCreatedAt().equals(entity.getCreatedAt())
                && dto.getUpdatedAt().equals(entity.getUpdatedAt());
    }

    /**
     * Create a comment entity with all not-null fields.
     *
     * @return the comment entity.
     */
    public static Comment createComment() {
        Comment comment = Comment.builder()
                .user(createUser())
                .content("CONTENT")
                .post(createPost())
                .id(1L)
                .build();

        comment.setCreatedAt(Date.from(Instant.now()));
        comment.setUpdatedAt(Date.from(Instant.now()));

        return comment;
    }

    /**
     * Create a post entity with all not-null fields.
     *
     * @return the post entity.
     */
    public static Post createPost() {
        Post post = Post.builder()
                .community(createCommunity())
                .content("CONTENT")
                .author(createUser())
                .id(1L)
                .build();

        post.setCreatedAt(Date.from(Instant.now()));
        post.setUpdatedAt(Date.from(Instant.now()));

        return post;
    }

    /**
     * Create a community entity with all not-null fields.
     *
     * @return the community entity.
     */
    public static Community createCommunity() {
        Community community = Community.builder()
                .title("TITLE")
                .avatar(createImage())
                .moderator(createUser())
                .id(1L)
                .build();

        community.setCreatedAt(Date.from(Instant.now()));
        community.setUpdatedAt(Date.from(Instant.now()));

        return community;
    }

    /**
     * Create user entity with all not-null fields.
     *
     * @return the user entity.
     */
    public static User createUser() {
        User user = User.builder()
                .email("test@mail.ru")
                .password("password")
                .firstName("FirstName")
                .lastName("LastName")
                .avatar(createImage())
                .id(1L)
                .build();

        user.setCreatedAt(Date.from(Instant.now()));
        user.setUpdatedAt(Date.from(Instant.now()));

        return user;
    }

    /**
     * Create an image entity with all not-null fields.
     *
     * @return the image entity.
     */
    public static Image createImage() {
        Image image = Image.builder()
                .imageId("ID")
                .originalUrl("URL")
                .id(1L)
                .build();

        image.setCreatedAt(Date.from(Instant.now()));
        image.setUpdatedAt(Date.from(Instant.now()));

        return image;
    }
}
