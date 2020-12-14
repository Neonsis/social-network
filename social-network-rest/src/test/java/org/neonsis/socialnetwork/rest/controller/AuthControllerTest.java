package org.neonsis.socialnetwork.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.neonsis.socialnetwork.model.dto.user.LoginDto;
import org.neonsis.socialnetwork.rest.SpringSecurityTextConfig;
import org.neonsis.socialnetwork.rest.config.AuditingConfig;
import org.neonsis.socialnetwork.security.JwtTokenProvider;
import org.neonsis.socialnetwork.security.model.dto.UserDto;
import org.neonsis.socialnetwork.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockBean(ProfileService.class)
@Import(SpringSecurityTextConfig.class)
@WebMvcTest(value = AuthController.class, excludeFilters = {@ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = AuditingConfig.class)})
class AuthControllerTest {

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("POST /auth/signin success")
    public void testSignInSuccess() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@gmail.com");
        loginDto.setPassword("P4assword");
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@gmail.com");

        when(jwtTokenProvider.generateToken("test@gmail.com")).thenReturn("TOKEN");

        mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginDto)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.token", is("TOKEN")));

        ArgumentCaptor<UsernamePasswordAuthenticationToken> argument = ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);
        verify(jwtTokenProvider, times(1)).generateToken("test@gmail.com");
        verify(authenticationManager, times(1)).authenticate(argument.capture());

        UsernamePasswordAuthenticationToken actual = argument.getValue();

        assertEquals("test@gmail.com", actual.getPrincipal());
        assertEquals("P4assword", actual.getCredentials());
    }


    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
