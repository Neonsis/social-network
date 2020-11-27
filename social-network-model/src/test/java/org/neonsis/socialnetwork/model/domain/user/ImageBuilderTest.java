package org.neonsis.socialnetwork.model.domain.user;

import org.junit.jupiter.api.Test;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntityBuilderTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link ImageBuilderTest} Unit Test.
 *
 * @author neonsis
 */
class ImageBuilderTest extends AbstractBaseEntityBuilderTest<Image.ImageBuilder> {

    @Override
    protected Image.ImageBuilder buildTestEntityBuilder() {
        return Image.builder();
    }

    /**
     * Test method for
     * {@link Image.ImageBuilder#imageId(String)}.
     */
    @Test
    public void testImageId() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().imageId(null));

        String imageId = "TEST";
        assertEquals(imageId, this.getEntityBuilder().imageId(imageId).build().getImageId());
    }

    /**
     * Test method for
     * {@link Image.ImageBuilder#imageId(String)}.
     */
    @Test
    public void testOriginalUrl() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().originalUrl(null));

        String url = "TEST";
        assertEquals(url, this.getEntityBuilder().originalUrl(url).build().getOriginalUrl());
    }
}