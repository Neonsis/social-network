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
        UserDto andreyVinel = UserDto.builder()
                .avatarUrl("https://sun2.beltelecom-by-minsk.userapi.com/impf/c857624/v857624289/879cd/w56m944IjV4.jpg?size=100x0&quality=96&crop=7,181,1422,1422&sign=73b0a27d7e9f5ec6a921b042922529ed&ava=1")
                .email("vinel02@mail.ru")
                .firstName("Андрей")
                .lastName("Кайзер")
                .password("P4assowrd")
                .build();
        ProfileDto andreyVinelProfile = ProfileDto.builder()
                .gender(Gender.MALE)
                .birthday(LocalDate.of(2002, 9, 29))
                .build();

        UserDto andreyVinelDone = userService.signUp(andreyVinel, andreyVinelProfile);

        UserDto mariaTseva = UserDto.builder()
                .avatarUrl("https://sun1.beltelecom-by-minsk.userapi.com/impg/QXN0FV2-by5gX4J2uoql1MQEurtVTLEYAxoDfw/eOg0RHUp000.jpg?size=100x0&quality=96&crop=0,89,960,960&sign=066d2b4a82d7176eecf63d67a98b453b&ava=1")
                .email("vinel.work03@mail.ru")
                .firstName("Maria")
                .lastName("Tséva")
                .password("P4assowrd")
                .build();
        ProfileDto mariaTsevaProfile = ProfileDto.builder()
                .gender(Gender.FEMALE)
                .birthday(LocalDate.of(2001, 1, 1))
                .build();

        UserDto mariaTsevaDone = userService.signUp(mariaTseva, mariaTsevaProfile);

        UserDto dmitryPilishick = UserDto.builder()
                .avatarUrl("https://sun2.beltelecom-by-minsk.userapi.com/impf/NCeE7Ho0Cb1ZbAPcgih4kPRUMuSW6MGgHdCzUg/Uylm5MV9L5c.jpg?size=100x0&quality=96&crop=135,60,674,674&sign=c6ffba7c47085b7464c7dd21b361b62a&ava=1")
                .email("test@mail.ru")
                .firstName("Дмитрий")
                .lastName("Пилищик")
                .password("P4assowrd")
                .build();
        ProfileDto dmitryPilishickProfile = ProfileDto.builder()
                .gender(Gender.FEMALE)
                .birthday(LocalDate.of(2002, 8, 31))
                .build();

        UserDto dmitryPilishickDone = userService.signUp(dmitryPilishick, dmitryPilishickProfile);

        UserDto daniilPronevich = UserDto.builder()
                .avatarUrl("https://sun1.beltelecom-by-minsk.userapi.com/impg/jwD4egFQkBqkBMJ6SeReYQS0htKNMMMp-OrlEA/uCSiK8vNabg.jpg?size=100x0&quality=96&crop=0,1,2159,2159&sign=5d972672b59abd974e17181748c47a17&ava=1")
                .email("daniilPronevich@mail.ru")
                .firstName("Даниил")
                .lastName("Проневич")
                .password("P4assowrd")
                .build();
        ProfileDto daniilPronevichProfile = ProfileDto.builder()
                .gender(Gender.FEMALE)
                .birthday(LocalDate.of(2002, 11, 14))
                .build();

        UserDto daniilPronevichDone = userService.signUp(daniilPronevich, daniilPronevichProfile);

        UserDto kirillVatskevich = UserDto.builder()
                .avatarUrl("https://sun1.beltelecom-by-minsk.userapi.com/impf/c849124/v849124835/1432ea/hAtGJxtZZG4.jpg?size=100x0&quality=96&crop=2,2,453,453&sign=ecc65325f4b64ceb486f3a1a0754a8d6&ava=1")
                .email("kirillVatskevich@mail.ru")
                .firstName("Кирилл")
                .lastName("Вацкевич")
                .password("P4assowrd")
                .build();
        ProfileDto kirillVatskevichProfile = ProfileDto.builder()
                .gender(Gender.FEMALE)
                .birthday(LocalDate.of(2002, 10, 1))
                .build();

        UserDto kirillVatskevichDone = userService.signUp(kirillVatskevich, kirillVatskevichProfile);

        UserDto egorTerlizki = UserDto.builder()
                .avatarUrl("https://sun1.beltelecom-by-minsk.userapi.com/impg/wHH1CAShQJMfnCUVAbtP0Fvu3m168tT4DQXVPw/ECvsZlPrB3c.jpg?size=100x0&quality=96&crop=4,6,945,945&sign=30b902dc59ef4adcc00a15126316a89e&ava=1")
                .email("egorTerlizki@mail.ru")
                .firstName("Егор")
                .lastName("Терлецкий")
                .password("P4assowrd")
                .build();
        ProfileDto egorTerlizkiProfile = ProfileDto.builder()
                .gender(Gender.FEMALE)
                .birthday(LocalDate.of(2002, 4, 29))
                .build();

        UserDto egorTerlizkiDone = userService.signUp(egorTerlizki, egorTerlizkiProfile);

        UserDto vitaliiyRogach = UserDto.builder()
                .avatarUrl("https://sun2.beltelecom-by-minsk.userapi.com/impg/N7-dkR_ZADt-R-Sk8vsXbilpSdMNlhz6oAV6aA/cyPlfWHHnO4.jpg?size=100x0&quality=96&crop=4,431,844,844&sign=e3da054ec4a0c9d3e3846a5f48a18957&ava=1")
                .email("vitaliiyRogach@mail.ru")
                .firstName("Виталий")
                .lastName("Рогач")
                .password("P4assowrd")
                .build();
        ProfileDto vitaliiyRogachProfile = ProfileDto.builder()
                .gender(Gender.FEMALE)
                .birthday(LocalDate.of(2002, 9, 28))
                .build();

        UserDto vitaliiyRogachDone = userService.signUp(vitaliiyRogach, vitaliiyRogachProfile);

        UserDto danikDanikov = UserDto.builder()
                .avatarUrl("https://sun1.beltelecom-by-minsk.userapi.com/impg/c858028/v858028407/1700a9/jxB4WO5-OBE.jpg?size=100x0&quality=96&crop=244,0,556,556&sign=98b077294db9ef5ae763b96cec6789f9&ava=1")
                .email("danikDanikov@mail.ru")
                .firstName("Даник")
                .lastName("Даньков")
                .password("P4assowrd")
                .build();
        ProfileDto danikDanikovProfile = ProfileDto.builder()
                .gender(Gender.FEMALE)
                .birthday(LocalDate.of(2002, 9, 28))
                .build();

        UserDto danikDanikovDone = userService.signUp(danikDanikov, danikDanikovProfile);

        friendshipService.addToFriends(mariaTsevaDone.getId(), dmitryPilishickDone.getId());
        friendshipService.addToFriends(mariaTsevaDone.getId(), andreyVinelDone.getId());
        friendshipService.addToFriends(mariaTsevaDone.getId(), daniilPronevichDone.getId());
        friendshipService.addToFriends(mariaTsevaDone.getId(), kirillVatskevichDone.getId());

        friendshipService.addToFriends(andreyVinelDone.getId(), mariaTsevaDone.getId());
        friendshipService.addToFriends(andreyVinelDone.getId(), dmitryPilishickDone.getId());
        friendshipService.addToFriends(andreyVinelDone.getId(), daniilPronevichDone.getId());
        friendshipService.addToFriends(andreyVinelDone.getId(), kirillVatskevichDone.getId());
        friendshipService.addToFriends(andreyVinelDone.getId(), egorTerlizkiDone.getId());
        friendshipService.addToFriends(andreyVinelDone.getId(), vitaliiyRogachDone.getId());
        friendshipService.addToFriends(andreyVinelDone.getId(), danikDanikovDone.getId());

        friendshipService.addToFriends(dmitryPilishickDone.getId(), andreyVinelDone.getId());
        friendshipService.addToFriends(dmitryPilishickDone.getId(), mariaTsevaDone.getId());
        friendshipService.addToFriends(dmitryPilishickDone.getId(), daniilPronevichDone.getId());

        friendshipService.addToFriends(daniilPronevichDone.getId(), dmitryPilishickDone.getId());
        friendshipService.addToFriends(daniilPronevichDone.getId(), mariaTsevaDone.getId());
        friendshipService.addToFriends(daniilPronevichDone.getId(), andreyVinelDone.getId());

        friendshipService.addToFriends(kirillVatskevichDone.getId(), andreyVinelDone.getId());
        friendshipService.addToFriends(kirillVatskevichDone.getId(), mariaTsevaDone.getId());
        friendshipService.addToFriends(kirillVatskevichDone.getId(), egorTerlizkiDone.getId());
        friendshipService.addToFriends(kirillVatskevichDone.getId(), vitaliiyRogachDone.getId());
        friendshipService.addToFriends(kirillVatskevichDone.getId(), danikDanikovDone.getId());

        friendshipService.addToFriends(egorTerlizkiDone.getId(), kirillVatskevichDone.getId());
        friendshipService.addToFriends(egorTerlizkiDone.getId(), andreyVinelDone.getId());
        friendshipService.addToFriends(egorTerlizkiDone.getId(), vitaliiyRogachDone.getId());
        friendshipService.addToFriends(egorTerlizkiDone.getId(), danikDanikovDone.getId());

        friendshipService.addToFriends(vitaliiyRogachDone.getId(), andreyVinelDone.getId());
        friendshipService.addToFriends(vitaliiyRogachDone.getId(), egorTerlizkiDone.getId());
        friendshipService.addToFriends(vitaliiyRogachDone.getId(), kirillVatskevichDone.getId());
        friendshipService.addToFriends(vitaliiyRogachDone.getId(), danikDanikovDone.getId());

        friendshipService.addToFriends(danikDanikovDone.getId(), vitaliiyRogachDone.getId());
        friendshipService.addToFriends(danikDanikovDone.getId(), egorTerlizkiDone.getId());
        friendshipService.addToFriends(danikDanikovDone.getId(), kirillVatskevichDone.getId());
        friendshipService.addToFriends(danikDanikovDone.getId(), andreyVinelDone.getId());

    }
}
