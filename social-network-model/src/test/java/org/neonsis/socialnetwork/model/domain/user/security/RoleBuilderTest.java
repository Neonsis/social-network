package org.neonsis.socialnetwork.model.domain.user.security;

import org.junit.jupiter.api.Test;
import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntityBuilderTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link RoleBuilderTest} Unit Test.
 *
 * @author neonsis
 */
class RoleBuilderTest extends AbstractBaseEntityBuilderTest<Role.RoleBuilder> {

    @Override
    protected Role.RoleBuilder buildTestEntityBuilder() {
        return Role.builder();
    }

    /**
     * Test method for
     * {@link Role.RoleBuilder#name(RoleName)}.
     */
    @Test
    public void testName() {
        assertEquals(this.getEntityBuilder(), this.getEntityBuilder().name(null));

        RoleName roleName = RoleName.ROLE_USER;
        assertEquals(roleName, this.getEntityBuilder().name(roleName).build().getName());
    }
}