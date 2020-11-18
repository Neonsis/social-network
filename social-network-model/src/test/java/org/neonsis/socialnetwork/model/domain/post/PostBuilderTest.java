package org.neonsis.socialnetwork.model.domain.post;

import org.junit.jupiter.api.Test;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntityBuilderTest;
import org.neonsis.socialnetwork.model.domain.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link PostBuilderTest} Unit Test.
 *
 * @author neonsis
 */
public class PostBuilderTest extends AbstractBaseEntityBuilderTest<Post.PostBuilder> {

    @Override
    protected Post.PostBuilder buildTestEntityBuilder() {
        return Post.builder();
    }

    /**
     * Test method for
     * {@link Post.PostBuilder#content(String)}.
     */
    @Test
    public void testContent() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().content(null));

        String content = "TEST";
        assertEquals(content, this.getEntityBuilder().content(content).build().getContent());
    }

    /**
     * Test method for
     * {@link Post.PostBuilder#author(User))}.
     */
    @Test
    public void testAuthor() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().author(null));

        User author = new User();
        author.setEmail("test@mail.ru");
        assertEquals(author.getEmail(), this.getEntityBuilder().author(author).build().getAuthor().getEmail());
    }
}