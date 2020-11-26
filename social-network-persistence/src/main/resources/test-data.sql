INSERT INTO `user` (`id`, `email`, `encrypted_password`, `first_name`, `last_name`)
VALUES (1, 'test@gmail.com', 'P4assowrd', 'Test', 'Test');

INSERT INTO `user` (`id`, `email`, `encrypted_password`, `first_name`, `last_name`)
VALUES (2, 'test2@gmail.com', 'P4assowrd', 'Test2', 'Test2');

INSERT INTO `user` (`id`, `email`, `encrypted_password`, `first_name`, `last_name`)
VALUES (3, 'test3@gmail.com', 'P4assowrd', 'Test3', 'Test3');

INSERT INTO `post` (`id`, `content`)
VALUES (1, 'TEST');

INSERT INTO `post_user` (`post_id`, `user_id`)
VALUES (1, 1);

INSERT INTO `post_like` (`user_id`, `post_id`)
VALUES (1, 1);

INSERT INTO `friendship` (`invited_id`, `inviter_id`, `status`)
VALUES (1, 2, 1);

INSERT INTO `friendship` (`invited_id`, `inviter_id`, `status`)
VALUES (3, 1, 0)