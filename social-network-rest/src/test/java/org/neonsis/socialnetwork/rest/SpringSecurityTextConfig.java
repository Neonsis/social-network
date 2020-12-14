package org.neonsis.socialnetwork.rest;

import org.neonsis.socialnetwork.rest.model.mapper.RestMapper;
import org.neonsis.socialnetwork.rest.model.mapper.RestMapperImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.neonsis.socialnetwork.security.model.domain.User;

import java.util.Collections;

@TestConfiguration
@ComponentScan("org.neonsis.socialnetwork.security.filter")
public class SpringSecurityTextConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User user = new User();
        user.setEncryptedPassword("P4assword");
        user.setEmail("test@gmail.com");
        user.setId(1L);

        return new InMemoryUserDetailsManager(Collections.singletonList(
                user
        ));
    }

    @Bean
    public RestMapper restMapper() {
        return new RestMapperImpl();
    }
}
