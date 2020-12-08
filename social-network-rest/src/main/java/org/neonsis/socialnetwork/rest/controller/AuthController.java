package org.neonsis.socialnetwork.rest.controller;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.model.dto.user.LoginDto;
import org.neonsis.socialnetwork.model.dto.user.RegistrationDto;
import org.neonsis.socialnetwork.rest.model.response.TokenResponse;
import org.neonsis.socialnetwork.security.JwtTokenProvider;
import org.neonsis.socialnetwork.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ProfileService profileService;

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginDto) {
        try {
            String email = loginDto.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, loginDto.getPassword()));
            String token = jwtTokenProvider.generateToken(email);
            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setToken(token);

            return ResponseEntity.ok(tokenResponse);
        } catch (AuthenticationException | EntityNotFoundException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse registerUser(@Valid @RequestBody RegistrationDto registrationDto) {
        profileService.signUp(registrationDto);
        String token = jwtTokenProvider.generateToken(registrationDto.getEmail());

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(token);

        return tokenResponse;
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
