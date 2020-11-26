INSERT INTO `role` (`name`)
values ('ROLE_USER');

INSERT INTO `user` (`id`, `email`, `encrypted_password`, `first_name`, `last_name`, `created_at`)
VALUES (1, 'vinel02@mail.ru', '$2y$12$6It8ZIVYcGW01BLy3dsueuu24zrS74Wby4htxfga7tyo6FchcgOe6', 'Андрей', 'Кайзер',
        '2020-09-20');

INSERT INTO `profile` (`id`, `gender`, `birthday`, `created_at`)
VALUES (1, 0, '2002-09-29', '2020-09-20');

INSERT INTO `user` (`id`, `email`, `encrypted_password`, `first_name`, `last_name`, `created_at`)
VALUES (2, 'vinel.work03@mail.ru', '$2y$12$6It8ZIVYcGW01BLy3dsueuu24zrS74Wby4htxfga7tyo6FchcgOe6', 'Maria', 'Tséva',
        '2020-10-20');

INSERT INTO `profile` (`id`, `gender`, `birthday`, `created_at`)
VALUES (2, 1, '2001-01-01', '2020-10-20');

INSERT INTO `user` (`id`, `email`, `encrypted_password`, `first_name`, `last_name`, `created_at`)
VALUES (3, 'test@mail.ru', '$2y$12$6It8ZIVYcGW01BLy3dsueuu24zrS74Wby4htxfga7tyo6FchcgOe6', 'Дмитрий', 'Пилищик',
        '2020-10-20');

INSERT INTO `profile` (`id`, `gender`, `birthday`, `created_at`)
VALUES (3, 0, '2002-10-31', '2020-10-20');

INSERT INTO `user` (`id`, `email`, `encrypted_password`, `first_name`, `last_name`, `created_at`)
VALUES (4, 'daniilPronevich@mail.ru', '$2y$12$6It8ZIVYcGW01BLy3dsueuu24zrS74Wby4htxfga7tyo6FchcgOe6', 'Даниил',
        'Проневич',
        '2020-10-20');

INSERT INTO `profile` (`id`, `gender`, `birthday`, `created_at`)
VALUES (4, 0, '2002-11-14', '2020-10-20');

INSERT INTO `user` (`id`, `email`, `encrypted_password`, `first_name`, `last_name`, `created_at`)
VALUES (5, 'kirillVatskevich@mail.ru', '$2y$12$6It8ZIVYcGW01BLy3dsueuu24zrS74Wby4htxfga7tyo6FchcgOe6', 'Кирилл',
        'Вацкевич',
        '2020-10-01');

INSERT INTO `profile` (`id`, `gender`, `birthday`, `created_at`)
VALUES (5, 0, '2002-10-01', '2020-10-20');

INSERT INTO `user` (`id`, `email`, `encrypted_password`, `first_name`, `last_name`, `created_at`)
VALUES (6, 'egorTerlizki@mail.ru', '$2y$12$6It8ZIVYcGW01BLy3dsueuu24zrS74Wby4htxfga7tyo6FchcgOe6', 'Егор', 'Терлецкий',
        '2020-10-01');

INSERT INTO `profile` (`id`, `gender`, `birthday`, `created_at`)
VALUES (6, 0, '2002-04-29', '2020-10-20');

INSERT INTO `user` (`id`, `email`, `encrypted_password`, `first_name`, `last_name`, `created_at`)
VALUES (7, 'vitaliiyRogach@mail.ru', '$2y$12$6It8ZIVYcGW01BLy3dsueuu24zrS74Wby4htxfga7tyo6FchcgOe6', 'Виталий', 'Рогач',
        '2020-10-01');

INSERT INTO `profile` (`id`, `gender`, `birthday`, `created_at`)
VALUES (7, 0, '2002-09-28', '2020-10-20');

INSERT INTO `user` (`id`, `email`, `encrypted_password`, `first_name`, `last_name`, `created_at`)
VALUES (8, 'danikDanikov@mail.ru', '$2y$12$6It8ZIVYcGW01BLy3dsueuu24zrS74Wby4htxfga7tyo6FchcgOe6', 'Даник', 'Даньков',
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
