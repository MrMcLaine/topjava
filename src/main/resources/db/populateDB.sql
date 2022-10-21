DELETE
FROM meals;
DELETE
FROM user_roles;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals(datetime, description, calories)
VALUES (date '2020-01-30' + time '10:00', 'Завтрак', '500'),
       (date '2020-01-30' + time '13:00', 'Обед', '1000'),
       (date '2020-01-30' + time '20:00', 'Ужин', '500'),
       (date '2020-01-31' + time '10:00', 'Еда на граничное значение', '100'),
       (date '2020-01-31' + time '10:00', 'Завтрак', '1000'),
       (date '2020-01-31' + time '13:00', 'Обед', '500'),
       (date '2020-01-31' + time '20:00', 'Ужин', '410')

