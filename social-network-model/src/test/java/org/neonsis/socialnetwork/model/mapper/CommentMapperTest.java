package org.neonsis.socialnetwork.model.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.neonsis.socialnetwork.model.domain.post.Comment;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.dto.base.AbstractBaseDto;
import org.neonsis.socialnetwork.model.dto.post.CommentDto;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.neonsis.socialnetwork.model.mapper.MapperDtoAssertUtilTest.*;

/**
 * {@link CommentMapperTest} Unit Test.
 *
 * @author neonsis
 */
class CommentMapperTest {

    /**
     * The tested mapper.
     */
    private final CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    /**
     * Test method to check correct mapping of {@link CommentDto#content}
     */
    @Test
    public void testCommentToDtoContent() {
        Comment comment = Comment.builder()
                .content("CONTENT")
                .build();

        CommentDto expected = commentMapper.commentToDto(comment);

        assertNotNull(expected.getContent());
        assertEquals(comment.getContent(), expected.getContent());
    }

    /**
     * Test method to check correct mapping of {@link CommentDto#user}
     */
    @Test
    public void testCommentToDtoUser() {
        User user = createUser();

        Comment expected = Comment.builder()
                .user(user)
                .build();

        CommentDto actual = commentMapper.commentToDto(expected);

        assertTrue(isEqualToUser(actual.getUser(), expected.getUser()));
    }

    /**
     * Test method to check correct mapping of {@link AbstractBaseDto} fields
     */
    @Test
    public void testCommentToDtoBaseDto() {
        Comment expected = Comment.builder()
                .id(1L)
                .build();

        expected.setCreatedAt(Date.from(Instant.now()));
        expected.setUpdatedAt(Date.from(Instant.now()));

        CommentDto actual = commentMapper.commentToDto(expected);

        assertTrue(isEqualToAbstractDto(actual, expected));
    }

}