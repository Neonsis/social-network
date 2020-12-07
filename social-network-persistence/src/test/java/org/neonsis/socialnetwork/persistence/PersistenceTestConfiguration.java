package org.neonsis.socialnetwork.persistence;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main PersistenceTestConfiguration.
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@EntityScan(basePackages = "org.neonsis.socialnetwork.model.domain")
@ComponentScan(basePackages = "org.neonsis.socialnetwork.persistence")
public class PersistenceTestConfiguration {
}
