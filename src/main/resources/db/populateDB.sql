DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (datetime, description, calories, user_id)
VALUES ('2020-10-18 01:00:00', 'User завтрак', 500, 100000),
       ('2020-10-18 11:00:00', 'User обед', 1000, 100000),
       ('2020-10-18 19:00:00', 'User ужин', 1000, 100000),
       ('2020-10-19 00:00:00', 'User Еда на граничное значение', 100, 100000),
       ('2020-10-19 11:00:00', 'User завтрак', 1000, 100000),
       ('2020-10-19 12:00:00', 'User обед', 500, 100000),
       ('2020-10-19 18:00:00', 'User ужин', 410, 100000),
       ('2020-10-19 09:00:00', 'Admin завтрак', 410, 100001),
       ('2020-10-19 11:30:00', 'Admin обед', 410, 100001),
       ('2020-10-19 18:30:00', 'Admin ужин', 410, 100001);
