package org.neonsis.socialnetwork.model.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.neonsis.socialnetwork.model.domain.community.Community;
import org.neonsis.socialnetwork.model.domain.user.Image;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.dto.base.AbstractBaseDto;
import org.neonsis.socialnetwork.model.dto.community.CommunityDto;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.neonsis.socialnetwork.model.mapper.MapperDtoAssertUtilTest.*;

/**
 * {@link CommunityMapperTest} Unit Test.
 *
 * @author neonsis
 */
class CommunityMapperTest {

    /**
     * The tested mapper.
     */
    private final CommunityMapper communityMapper = Mappers.getMapper(CommunityMapper.class);

    /**
     * Test method to check correct mapping of {@link CommunityDto#title
     */
    @Test
    public void testCommunityToDtoTitle() {
        Community expected = Community.builder()
                .title("TITLE")
                .build();

        CommunityDto actual = communityMapper.communityToDto(expected);

        assertNotNull(actual.getTitle());
        assertEquals(expected.getTitle(), actual.getTitle());
    }

    /**
     * Test method to check correct mapping of {@link CommunityDto#moderator
     */
    @Test
    public void testCommunityToDtoModerator() {
        User moderator = createUser();

        Community expected = Community.builder()
                .moderator(moderator)
                .build();

        CommunityDto actual = communityMapper.communityToDto(expected);

        assertTrue(isEqualToUser(actual.getModerator(), expected.getModerator()));
    }

    /**
     * Test method to check correct mapping of {@link CommunityDto#avatar
     */
    @Test
    public void testCommunityToDtoAvatar() {
        Image image = createImage();

        Community expected = Community.builder()
                .avatar(image)
                .build();

        CommunityDto actual = communityMapper.communityToDto(expected);

        assertTrue(isEqualToImage(actual.getAvatar(), expected.getAvatar()));
    }

    /**
     * Test method to check correct mapping of {@link AbstractBaseDto} fields
     */
    @Test
    public void testCommunityToDtoBaseDto() {
        Community expected = Community.builder()
                .id(1L)
                .build();

        expected.setCreatedAt(Date.from(Instant.now()));
        expected.setUpdatedAt(Date.from(Instant.now()));

        CommunityDto actual = communityMapper.communityToDto(expected);

        assertTrue(isEqualToAbstractDto(actual, expected));
    }
}