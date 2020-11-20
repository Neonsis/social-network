INSERT INTO `role` (`name`)
values ('ROLE_USER');

INSERT INTO `user` (`id`, `email`, `encrypted_password`, `first_name`, `last_name`, `avatar_url`, `created_at`)
VALUES (1, 'vinel02@mail.ru', '$2y$12$6It8ZIVYcGW01BLy3dsueuu24zrS74Wby4htxfga7tyo6FchcgOe6', 'Андрей', 'Кайзер',
        'https://sun2.beltelecom-by-minsk.userapi.com/impf/c857624/v857624289/879cd/w56m944IjV4.jpg?size=100x0&quality=96&crop=7,181,1422,1422&sign=73b0a27d7e9f5ec6a921b042922529ed&ava=1',
        '2020-09-20');

INSERT INTO `profile` (`id`, `gender`, `birthday`, `created_at`)
VALUES (1, 0, '2002-09-29', '2020-09-20');

INSERT INTO `user` (`id`, `email`, `encrypted_password`, `first_name`, `last_name`, `avatar_url`, `created_at`)
VALUES (2, 'vinel.work03@mail.ru', '$2y$12$6It8ZIVYcGW01BLy3dsueuu24zrS74Wby4htxfga7tyo6FchcgOe6', 'Maria', 'Tséva',
        'https://sun1.beltelecom-by-minsk.userapi.com/impg/QXN0FV2-by5gX4J2uoql1MQEurtVTLEYAxoDfw/eOg0RHUp000.jpg?size=100x0&quality=96&crop=0,89,960,960&sign=066d2b4a82d7176eecf63d67a98b453b&ava=1',
        '2020-10-20');

INSERT INTO `profile` (`id`, `gender`, `birthday`, `created_at`)
VALUES (2, 1, '2001-01-01', '2020-10-20');

INSERT INTO `user` (`id`, `email`, `encrypted_password`, `first_name`, `last_name`, `avatar_url`, `created_at`)
VALUES (3, 'test@mail.ru', '$2y$12$6It8ZIVYcGW01BLy3dsueuu24zrS74Wby4htxfga7tyo6FchcgOe6', 'Дмитрий', 'Пилищик',
        'https://sun2.beltelecom-by-minsk.userapi.com/impf/NCeE7Ho0Cb1ZbAPcgih4kPRUMuSW6MGgHdCzUg/Uylm5MV9L5c.jpg?size=100x0&quality=96&crop=135,60,674,674&sign=c6ffba7c47085b7464c7dd21b361b62a&ava=1',
        '2020-10-20');

INSERT INTO `profile` (`id`, `gender`, `birthday`, `created_at`)
VALUES (3, 0, '2002-10-31', '2020-10-20');

INSERT INTO `user` (`id`, `email`, `encrypted_password`, `first_name`, `last_name`, `avatar_url`, `created_at`)
VALUES (4, 'daniilPronevich@mail.ru', '$2y$12$6It8ZIVYcGW01BLy3dsueuu24zrS74Wby4htxfga7tyo6FchcgOe6', 'Даниил',
        'Проневич',
        'https://sun1.beltelecom-by-minsk.userapi.com/impg/jwD4egFQkBqkBMJ6SeReYQS0htKNMMMp-OrlEA/uCSiK8vNabg.jpg?size=100x0&quality=96&crop=0,1,2159,2159&sign=5d972672b59abd974e17181748c47a17&ava=1',
        '2020-10-20');

INSERT INTO `profile` (`id`, `gender`, `birthday`, `created_at`)
VALUES (4, 0, '2002-11-14', '2020-10-20');

INSERT INTO `user` (`id`, `email`, `encrypted_password`, `first_name`, `last_name`, `avatar_url`, `created_at`)
VALUES (5, 'kirillVatskevich@mail.ru', '$2y$12$6It8ZIVYcGW01BLy3dsueuu24zrS74Wby4htxfga7tyo6FchcgOe6', 'Кирилл',
        'Вацкевич',
        'https://sun1.beltelecom-by-minsk.userapi.com/impf/c849124/v849124835/1432ea/hAtGJxtZZG4.jpg?size=100x0&quality=96&crop=2,2,453,453&sign=ecc65325f4b64ceb486f3a1a0754a8d6&ava=1',
        '2020-10-01');

INSERT INTO `profile` (`id`, `gender`, `birthday`, `created_at`)
VALUES (5, 0, '2002-10-01', '2020-10-20');

INSERT INTO `user` (`id`, `email`, `encrypted_password`, `first_name`, `last_name`, `avatar_url`, `created_at`)
VALUES (6, 'egorTerlizki@mail.ru', '$2y$12$6It8ZIVYcGW01BLy3dsueuu24zrS74Wby4htxfga7tyo6FchcgOe6', 'Егор', 'Терлецкий',
        'https://sun1.beltelecom-by-minsk.userapi.com/impg/wHH1CAShQJMfnCUVAbtP0Fvu3m168tT4DQXVPw/ECvsZlPrB3c.jpg?size=100x0&quality=96&crop=4,6,945,945&sign=30b902dc59ef4adcc00a15126316a89e&ava=1',
        '2020-10-01');

INSERT INTO `profile` (`id`, `gender`, `birthday`, `created_at`)
VALUES (6, 0, '2002-04-29', '2020-10-20');

INSERT INTO `user` (`id`, `email`, `encrypted_password`, `first_name`, `last_name`, `avatar_url`, `created_at`)
VALUES (7, 'vitaliiyRogach@mail.ru', '$2y$12$6It8ZIVYcGW01BLy3dsueuu24zrS74Wby4htxfga7tyo6FchcgOe6', 'Виталий', 'Рогач',
        'https://sun2.beltelecom-by-minsk.userapi.com/impg/N7-dkR_ZADt-R-Sk8vsXbilpSdMNlhz6oAV6aA/cyPlfWHHnO4.jpg?size=100x0&quality=96&crop=4,431,844,844&sign=e3da054ec4a0c9d3e3846a5f48a18957&ava=1',
        '2020-10-01');

INSERT INTO `profile` (`id`, `gender`, `birthday`, `created_at`)
VALUES (7, 0, '2002-09-28', '2020-10-20');

INSERT INTO `user` (`id`, `email`, `encrypted_password`, `first_name`, `last_name`, `avatar_url`, `created_at`)
VALUES (8, 'danikDanikov@mail.ru', '$2y$12$6It8ZIVYcGW01BLy3dsueuu24zrS74Wby4htxfga7tyo6FchcgOe6', 'Даник', 'Даньков',
        'https://sun1.beltelecom-by-minsk.userapi.com/impg/c858028/v858028407/1700a9/jxB4WO5-OBE.jpg?size=100x0&quality=96&crop=244,0,556,556&sign=98b077294db9ef5ae763b96cec6789f9&ava=1',
        '2020-10-01');

/* Andrey Friends */
INSERT INTO `profile` (`id`, `gender`, `birthday`, `created_at`)
VALUES (8, 0, '2002-09-28', '2020-10-20');

INSERT INTO `friendship` (`inviter_id`, `invited_id`, `status`)
VALUES (1, 2, 1);

INSERT INTO `friendship` (`inviter_id`, `invited_id`, `status`)
VALUES (1, 3, 1);

INSERT INTO `friendship` (`inviter_id`, `invited_id`, `status`)
VALUES (1, 4, 1);

INSERT INTO `friendship` (`inviter_id`, `invited_id`, `status`)
VALUES (1, 5, 1);

INSERT INTO `friendship` (`inviter_id`, `invited_id`, `status`)
VALUES (1, 6, 1);

INSERT INTO `friendship` (`inviter_id`, `invited_id`, `status`)
VALUES (1, 7, 1);

INSERT INTO `friendship` (`inviter_id`, `invited_id`, `status`)
VALUES (1, 8, 1);

/* Maria Friends */
INSERT INTO `friendship` (`inviter_id`, `invited_id`, `status`)
VALUES (2, 3, 1);

INSERT INTO `friendship` (`inviter_id`, `invited_id`, `status`)
VALUES (2, 4, 1);

INSERT INTO `friendship` (`inviter_id`, `invited_id`, `status`)
VALUES (2, 5, 1);

/* Dmitry friends */
INSERT INTO `friendship` (`inviter_id`, `invited_id`, `status`)
VALUES (3, 4, 1);

/* Kirill friends */
INSERT INTO `friendship` (`inviter_id`, `invited_id`, `status`)
VALUES (5, 6, 1);

INSERT INTO `friendship` (`inviter_id`, `invited_id`, `status`)
VALUES (5, 7, 1);

INSERT INTO `friendship` (`inviter_id`, `invited_id`, `status`)
VALUES (5, 8, 1);

/* Egor friends */
INSERT INTO `friendship` (`inviter_id`, `invited_id`, `status`)
VALUES (6, 7, 1);

INSERT INTO `friendship` (`inviter_id`, `invited_id`, `status`)
VALUES (6, 8, 1);
