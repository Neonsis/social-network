package org.neonsis.socialnetwork.service.impl;

import org.neonsis.socialnetwork.service.UuidGeneratorService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UuidGeneratorServiceImpl implements UuidGeneratorService {

    @Override
    public String generateUuid() {
        return UUID.randomUUID().toString();
    }
}
