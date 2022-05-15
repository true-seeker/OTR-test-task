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
