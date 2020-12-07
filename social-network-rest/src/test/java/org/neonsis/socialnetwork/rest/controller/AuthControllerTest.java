package org.neonsis.socialnetwork.rest.controller;

import org.neonsis.socialnetwork.model.dto.user.UserDto;
import org.neonsis.socialnetwork.service.UserService;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/*
@WebMvcTest(value = AuthController.class, excludeFilters = {@ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = AuditingConfig.class)})
@Import(SpringSecurityTestConfig.class)
class AuthControllerTest {

    @MockBean
    private UserService userService;

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
        userDto.setFirstName("Andrey");
        userDto.setLastName("Vinel");
        userDto.setAvatarUrl(Image.builder());

        when(userService.findByEmail("test@gmail.com")).thenReturn(userDto);
        when(jwtTokenProvider.generateToken("test@gmail.com")).thenReturn("TOKEN");

        mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginDto)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is(userDto.getId().toString())))
                .andExpect(jsonPath("$.firstName", is(userDto.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(userDto.getLastName())))
                .andExpect(jsonPath("$.avatarUrl", is(userDto.getAvatarUrl())))
                .andExpect(jsonPath("$.email", is(userDto.getEmail())))
                .andExpect(jsonPath("$.token", is("TOKEN")));

        ArgumentCaptor<UsernamePasswordAuthenticationToken> argument = ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);
        verify(userService, times(1)).findByEmail("test@gmail.com");
        verify(jwtTokenProvider, times(1)).generateToken("test@gmail.com");
        verify(authenticationManager, times(1)).authenticate(argument.capture());

        UsernamePasswordAuthenticationToken actual = argument.getValue();

        assertEquals("test@gmail.com", (String) actual.getPrincipal());
        assertEquals("P4assword", (String) actual.getCredentials());
    }

   */
/* @Test
    @DisplayName("POST /auth/signup success")
    public void testSignUpSuccess() throws Exception {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setEmail("test@gmail.com");
        registrationDto.setPassword("P4assword");
        registrationDto.setFirstName("Andrey");
        registrationDto.setLastName("Vinel");
        registrationDto.setBirthday(LocalDate.of(2002, 9, 29));
        registrationDto.setGender(Gender.MALE);

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@gmail.com");
        userDto.setFirstName("Andrey");
        userDto.setLastName("Vinel");

        when(userService.signUp(registrationDto)).thenReturn(userDto);
        when(jwtTokenProvider.generateToken("test@gmail.com")).thenReturn("TOKEN");

        String s = asJsonString(registrationDto);
        System.out.println(s);

        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(registrationDto)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is(userDto.getId().toString())))
                .andExpect(jsonPath("$.firstName", is(userDto.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(userDto.getLastName())))
                .andExpect(jsonPath("$.avatarUrl").value(IsNull.nullValue()))
                .andExpect(jsonPath("$.email", is(userDto.getEmail())))
                .andExpect(jsonPath("$.token", is("TOKEN")));
    }
*//*


    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}*/
