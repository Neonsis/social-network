package org.neonsis.socialnetwork.model.domain.community;

import org.junit.jupiter.api.Test;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntityBuilderTest;
import org.neonsis.socialnetwork.model.domain.user.Image;
import org.neonsis.socialnetwork.model.domain.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link CommunityBuilderTest} Unit Test.
 *
 * @author neonsis
 */
class CommunityBuilderTest extends AbstractBaseEntityBuilderTest<Community.CommunityBuilder> {

    @Override
    protected Community.CommunityBuilder buildTestEntityBuilder() {
        return Community.builder();
    }

    /**
     * Test method for
     * {@link Community.CommunityBuilder#title(String)}.
     */
    @Test
    public void testTitle() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().title(null));

        String title = "Title";
        assertEquals(title, this.getEntityBuilder().title(title).build().getTitle());
    }

    /**
     * Test method for
     * {@link Community.CommunityBuilder#moderator(User)}.
     */
    @Test
    public void testModerator() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().moderator(null));

        User moderator = User.builder().id(1L).build();
        assertEquals(moderator.getId(), this.getEntityBuilder().moderator(moderator).build().getModerator().getId());
    }

    /**
     * Test method for
     * {@link Community.CommunityBuilder#avatar(Image)}.
     */
    @Test
    public void testAvatar() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().moderator(null));

        Image avatar = Image.builder().id(1L).build();
        assertEquals(avatar.getId(), this.getEntityBuilder().avatar(avatar).build().getAvatar().getId());
    }
}