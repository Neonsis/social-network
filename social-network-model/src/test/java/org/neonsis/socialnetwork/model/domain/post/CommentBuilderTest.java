package org.neonsis.socialnetwork.model.domain.post;

import org.junit.jupiter.api.Test;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntityBuilderTest;
import org.neonsis.socialnetwork.model.domain.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * {@link CommentBuilderTest} Unit Test.
 *
 * @author neonsis
 */
class CommentBuilderTest extends AbstractBaseEntityBuilderTest<Comment.CommentBuilder> {

    @Override
    protected Comment.CommentBuilder buildTestEntityBuilder() {
        return Comment.builder();
    }

    /**
     * Test method for
     * {@link Comment.CommentBuilder#content(java.lang.String)}.
     */
    @Test
    public void testContent() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().content(null));

        String content = "TEST";
        assertEquals(content, this.getEntityBuilder().content(content).build().getContent());
    }

    /**
     * Test method for
     * {@link Comment.CommentBuilder#user(User))}.
     */
    @Test
    public void testUser() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().user(null));

        User user = new User();
        user.setEmail("test@mail.ru");
        assertEquals(user.getEmail(), this.getEntityBuilder().user(user).build().getUser().getEmail());
    }

    /**
     * Test method for
     * {@link Comment.CommentBuilder#post(Post)}.
     */
    @Test
    public void testPost() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().post(null));

        Post post = new Post();
        post.setContent("TEST");
        assertEquals(post.getContent(), this.getEntityBuilder().post(post).build().getPost().getContent());
    }
}