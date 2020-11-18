package org.neonsis.socialnetwork.model.domain.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * {@link AbstractBaseEntity} Unit Test.
 *
 * @author neonsis
 */
public abstract class AbstractBaseEntityBuilderTest<
        T extends AbstractBaseEntity.Builder<? extends AbstractBaseEntity>> {

    /**
     * The tested entity builder.
     */
    private T entityBuilder = this.buildTestEntityBuilder();

    /**
     * Build an entity builder to test.
     *
     * @return an entity builder for tests.
     */
    abstract protected T buildTestEntityBuilder();

    /**
     * @throws java.lang.Exception If test initialization crashes.
     */
    @BeforeEach
    public void setUp() throws Exception {
        this.entityBuilder = this.buildTestEntityBuilder();

        assertNotNull(entityBuilder, "The tested entity builder cannot be null!");
    }

    /**
     * @throws java.lang.Exception If test clean up crashes.
     */
    @AfterEach
    public void tearDown() throws Exception {
        this.entityBuilder = null;
    }

    /**
     * Get the {@link #entityBuilder}.
     *
     * @return the {@link #entityBuilder}.
     */
    protected final T getEntityBuilder() {
        return entityBuilder;
    }

    /**
     * Test method for {@link AbstractBaseEntity.Builder#buildEntity()}.
     */
    @Test
    public void testBuildEntity() {
        assertNotNull(entityBuilder.buildEntity());
    }

    /**
     * Test method for {@link AbstractBaseEntity.Builder#getEntity()}.
     */
    @Test
    public void testGetEntity() {
        assertNotNull(entityBuilder.getEntity());
    }

    /**
     * Test method for {@link AbstractBaseEntity.Builder#build()}.
     */
    @Test
    public void testBuild() {
        assertNotNull(entityBuilder.build());

        assertEquals(entityBuilder.getEntity(), entityBuilder.build());
    }

    /**
     * Test method for {@link AbstractBaseEntity.Builder#id(Long)}.
     */
    @Test
    public void testId() {
        assertEquals(entityBuilder, entityBuilder.id(null));

        Long id = 1L;
        assertEquals(id, entityBuilder.id(id).build().getId());
    }

    /**
     * Test method for {@link AbstractBaseEntity.Builder#createdAt(java.util.Date)}.
     */
    @Test
    public void testCreatedAt() {
        assertEquals(entityBuilder, entityBuilder.createdAt(null));

        Date date = new Date();
        assertEquals(date, entityBuilder.createdAt(date).build().getCreatedAt());
    }

    /**
     * Test method for {@link AbstractBaseEntity.Builder#updatedAt(java.util.Date)}.
     */
    @Test
    public void testModifiedAt() {
        assertEquals(entityBuilder, entityBuilder.updatedAt(null));

        Date date = new Date();
        assertEquals(date, entityBuilder.updatedAt(date).build().getUpdatedAt());
    }
}