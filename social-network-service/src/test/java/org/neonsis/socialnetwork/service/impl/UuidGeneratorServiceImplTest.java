package org.neonsis.socialnetwork.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neonsis.socialnetwork.service.UuidGeneratorService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UuidGeneratorServiceImplTest {

    private UuidGeneratorService uuidGeneratorService;

    @BeforeEach
    void setup() {
        this.uuidGeneratorService = new UuidGeneratorServiceImpl();
    }

    @Test
    public void testGenerateUuid_whenCall_shouldReturnUuid() {
        String uuid = uuidGeneratorService.generateUuid();

        assertNotNull(uuid);
    }
}