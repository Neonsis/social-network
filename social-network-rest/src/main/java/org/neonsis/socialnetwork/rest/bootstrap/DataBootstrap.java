package org.neonsis.socialnetwork.rest.bootstrap;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.model.domain.user.Gender;
import org.neonsis.socialnetwork.model.dto.ProfileDto;
import org.neonsis.socialnetwork.model.dto.UserDto;
import org.neonsis.socialnetwork.service.FriendshipService;
import org.neonsis.socialnetwork.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataBootstrap implements ApplicationRunner {

    private final UserService userService;
    private final FriendshipService friendshipService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        UserDto userDto = UserDto.builder()
                .email("vinel02@mail.ru")
                .firstName("Andrey")
                .lastName("Vinel")
                .password("P4assowrd")
                .build();
        ProfileDto profileDto = ProfileDto.builder()
                .gender(Gender.MALE)
                .birthday(LocalDate.of(2002, 9, 29))
                .build();

        UserDto newUser = userService.signUp(userDto, profileDto);

        UserDto userDto1 = UserDto.builder()
                .email("vinel.work03@mail.ru")
                .firstName("Maria")
                .lastName("Vinel")
                .password("P4assowrd")
                .build();
        ProfileDto profileDto1 = ProfileDto.builder()
                .gender(Gender.FEMALE)
                .birthday(LocalDate.of(2001, 1, 1))
                .build();

        UserDto newUser1 = userService.signUp(userDto1, profileDto1);

        UserDto userDto2 = UserDto.builder()
                .email("test@mail.ru")
                .firstName("Test")
                .lastName("Vinel")
                .password("P4assowrd")
                .build();
        ProfileDto profileDto2 = ProfileDto.builder()
                .gender(Gender.FEMALE)
                .birthday(LocalDate.of(2001, 2, 5))
                .build();

        UserDto newUser2 = userService.signUp(userDto2, profileDto2);

        friendshipService.addToFriends(newUser1.getId(), newUser2.getId());
        friendshipService.addToFriends(newUser.getId(), newUser1.getId());
        friendshipService.addToFriends(newUser.getId(), newUser2.getId());

        friendshipService.addToFriends(newUser1.getId(), newUser.getId());
        friendshipService.addToFriends(newUser2.getId(), newUser.getId());
    }
}
