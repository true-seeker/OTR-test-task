--changeset liquibase:1
CREATE TABLE branch
(
    id    SERIAL PRIMARY KEY,
    title VARCHAR(100)
);

--changeset liquibase:2
CREATE TABLE position
(
    id    SERIAL PRIMARY KEY,
    title VARCHAR(30)
);

--changeset liquibase:3
CREATE TABLE employee
(
    id          SERIAL PRIMARY KEY,
    manager_id  INTEGER REFERENCES employee (id),
    position_id INTEGER REFERENCES position (id) NOT NULL,
    full_name   VARCHAR(50)                      NOT NULL,
    branch_id   INTEGER REFERENCES branch (id)   NOT NULL
);

--changeset liquibase:4
CREATE TABLE task
(
    id          SERIAL PRIMARY KEY,
    priority    SMALLINT                     NOT NULL,
    description VARCHAR(200)                 NOT NULL,
    employee_id INT REFERENCES employee (id) NOT NULL
);

--changeset liquibase:5
INSERT INTO branch(title)
VALUES ('Отдел разработки'),
       ('Отдел кадров'),
       ('Отдел тестирования'),
       ('Отдел SMM');

--changeset liquibase:6
INSERT INTO position(title)
VALUES ('Младший разработчик'),
       ('Разработчик'),
       ('Страший разработчик'),
       ('Руководитель разработки'),
       ('HR'),
       ('Тестировшик'),
       ('Копирайтер');

--changeset liquibase:7
INSERT INTO employee(manager_id, position_id, full_name, branch_id)
VALUES (NULL, 3, 'Иванов Иван Иванович', 1),
       (1, 1, 'Петров Пётр Петрович', 1),
       (NULL, 4, 'Дмитриев Дмитрий Дмитриевич', 1),
       (3, 4, 'Кириллов Кирилл Кириллович', 3);

--changeset liquibase:8
INSERT INTO task(priority, description, employee_id)
VALUES (2, 'Рефакторинг', 2),
       (4, 'Добавление фичи', 1),
       (10, 'Фикс бага', 3),
       (5, 'Тест фичи', 4);
