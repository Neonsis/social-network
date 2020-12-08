insert into `user` (`id`, `email`, `encrypted_password`)
VALUES (1, 'test@gmail.com', 'P4assword');

insert into `user` (`id`, `email`,`encrypted_password`)
VALUES (2, 'test2@gmail.com','P4assword' );

insert into `user` (`id`, `email`,`encrypted_password`)
VALUES (3, 'test3@gmail.com','P4assword');

insert into `profile` (`id`, `first_name`, `last_name`)
VALUES (1, 'Test', 'Test');

insert into `profile` (`id`, `first_name`, `last_name`)
VALUES (2, 'Test2', 'Test2');

insert into `profile` (`id`, `first_name`, `last_name`)
VALUES (3, 'Test3', 'Test3');

insert into `community`(`id`, `title`, `moderator_id`)
VALUES (1, 'TEST', 1);

insert into `community_profile`(`community_id`, `profile_id`)
VALUES (1, 2);

insert into `post` (`id`, `content`)
VALUES (1, 'TEST');

insert into `post`(`id`, `content`)
VALUES (2, 'TEST2');

insert into `post_profile` (`post_id`, `profile_id`)
VALUES (1, 1);

insert into `post_community`(`post_id`, `community_id`)
VALUES (2, 1);

insert into `post_like` (`user_id`, `post_id`)
VALUES (1, 1);

insert into `friendship` (`invited_id`, `inviter_id`, `status`)
VALUES (1, 2, 1);

insert into `friendship` (`invited_id`, `inviter_id`, `status`)
VALUES (3, 1, 0)