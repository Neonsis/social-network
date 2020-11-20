package org.neonsis.socialnetwork.rest;

import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.domain.user.security.Role;
import org.neonsis.socialnetwork.model.domain.user.security.RoleName;
import org.neonsis.socialnetwork.rest.model.mapper.RestMapper;
import org.neonsis.socialnetwork.rest.model.mapper.RestMapperImpl;
import org.neonsis.socialnetwork.service.security.UserPrincipal;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;

@TestConfiguration
@ComponentScan("org.neonsis.socialnetwork.rest.security.filter")
public class SpringSecurityTextConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User user = User.builder()
                .password("P4assword")
                .email("test@gmail.com")
                .roles(Collections.singleton(Role.builder().name(RoleName.ROLE_USER).build()))
                .id(1L)
                .build();
        UserPrincipal basicActiveUser = UserPrincipal.create(user);

        return new InMemoryUserDetailsManager(Collections.singletonList(
                basicActiveUser
        ));
    }

    @Bean
    public RestMapper restMapper() {
        return new RestMapperImpl();
    }
}
