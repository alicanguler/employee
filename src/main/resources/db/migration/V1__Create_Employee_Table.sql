
CREATE TABLE employees (
                           id UUID  NOT NULL,
                           FIRST_NAME VARCHAR(255),
                           LAST_NAME VARCHAR(255),
                           EMAIL VARCHAR(255) UNIQUE,
                           BIRTHDAY DATE,
                           HOBBIES VARCHAR(1024),
                           PRIMARY KEY (id)
);

CREATE INDEX idx_employees_email ON employees (EMAIL);