package org.neonsis.socialnetwork.model.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.neonsis.socialnetwork.model.domain.community.Community;
import org.neonsis.socialnetwork.model.domain.post.Comment;
import org.neonsis.socialnetwork.model.domain.post.Post;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.dto.base.AbstractBaseDto;
import org.neonsis.socialnetwork.model.dto.post.PostDto;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.neonsis.socialnetwork.model.mapper.MapperDtoAssertUtilTest.*;

/**
 * {@link PostMapperTest} Unit Test.
 *
 * @author neonsis
 */
class PostMapperTest {

    /**
     * The tested mapper.
     */
    private final PostMapper postMapper = Mappers.getMapper(PostMapper.class);

    /**
     * Test method to check correct mapping of {@link PostDto#content
     */
    @Test
    public void testPostToDtoContent() {
        Post expected = Post.builder()
                .content("CONTENT")
                .build();

        PostDto actual = postMapper.postToDto(expected);

        assertNotNull(actual.getContent());
        assertEquals(expected.getContent(), actual.getContent());
    }

    /**
     * Test method to check correct mapping of {@link PostDto#author
     */
    @Test
    public void testPostToDtoAuthor() {
        User author = createUser();

        Post expected = Post.builder()
                .author(author)
                .build();

        PostDto actual = postMapper.postToDto(expected);

        assertTrue(isEqualToUser(actual.getAuthor(), expected.getAuthor()));
    }

    /**
     * Test method to check correct mapping of {@link PostDto#community
     */
    @Test
    public void testPostToDtoCommunity() {
        Community community = createCommunity();

        Post expected = Post.builder()
                .community(community)
                .build();

        PostDto actual = postMapper.postToDto(expected);

        assertTrue(isEqualToCommunity(actual.getCommunity(), expected.getCommunity()));
    }

    /**
     * Test method to check correct mapping of {@link PostDto#countLike
     */
    @Test
    public void testPostToDtoCountyLike() {
        Post expected = new Post();
        expected.setCountLike(12);

        PostDto actual = postMapper.postToDto(expected);

        assertEquals(actual.getCountLike(), expected.getCountLike());
    }

    /**
     * Test method to check correct mapping of {@link PostDto#comments
     */
    @Test
    public void testPostToDtoComments() {
        Comment comment = createComment();

        Post expected = new Post();
        expected.setComments(List.of(comment));

        PostDto actual = postMapper.postToDto(expected);

        assertTrue(isEqualToComment(actual.getComments().get(0), expected.getComments().get(0)));
    }

    /**
     * Test method to check correct mapping of {@link AbstractBaseDto} fields
     */
    @Test
    public void testPostToDtoBaseDto() {
        Post expected = Post.builder()
                .id(1L)
                .build();

        expected.setCreatedAt(Date.from(Instant.now()));
        expected.setUpdatedAt(Date.from(Instant.now()));

        PostDto actual = postMapper.postToDto(expected);

        assertTrue(isEqualToAbstractDto(actual, expected));
    }
}