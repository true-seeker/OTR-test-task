-- changeset liquibase:1
CREATE TABLE employees
(
    id        SERIAL PRIMARY KEY,
    manager   CHAR(50),
    position  CHAR(20)  NOT NULL,
    full_name CHAR(50)  NOT NULL,
    branch    CHAR(100) NOT NULL
);

-- changeset liquibase:2
CREATE TABLE tasks
(
    id          SERIAL PRIMARY KEY,
    priority    SMALLINT                      NOT NULL,
    description CHAR(200)                     NOT NULL,
    employee_id INT REFERENCES employees (id) NOT NULL
);