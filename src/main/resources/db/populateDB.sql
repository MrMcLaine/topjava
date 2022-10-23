DELETE
FROM user_roles;
DELETE
FROM meals_table;
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

INSERT INTO meals_table(user_id, date_time, description, calories)
VALUES (100000,date '2020-01-30' + time '10:00', 'Завтрак', '500'),
       (100000,date '2020-01-30' + time '13:00', 'Обед', '1000'),
       (100000,date '2020-01-30' + time '20:00', 'Ужин', '500'),
       (100000,date '2020-01-31' + time '00:00', 'Еда на граничное значение', '100'),
       (100000,date '2020-01-31' + time '10:00', 'Завтрак', '1000'),
       (100000,date '2020-01-31' + time '13:00', 'Обед', '500'),
       (100000,date '2020-01-31' + time '20:00', 'Ужин', '410')

