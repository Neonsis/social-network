package org.neonsis.socialnetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "org.neonsis.socialnetwork")
public class SocialNetworkStarter {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SocialNetworkStarter.class);
    }
}
