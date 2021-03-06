DROP TABLE ufsite.user_role;
DROP TABLE ufsite.users;
DROP TABLE ufsite.tokens;
DROP TABLE ufsite.roles;

CREATE TABLE ufsite.tokens
(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    token VARCHAR(255),
    date TIMESTAMP
);
CREATE TABLE ufsite.users
(
    id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    token INT,
    username VARCHAR(255) NOT NULL UNIQUE,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled  BIT          NOT NULL,
    ban      BIT          NOT NULL,
    FOREIGN KEY (token) REFERENCES ufsite.tokens (id)
);
CREATE UNIQUE INDEX username_index ON ufsite.users (username, email);

CREATE TABLE ufsite.roles
(
    id   INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(30) NOT NULL UNIQUE
);
CREATE TABLE ufsite.user_role
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES ufsite.users (id),
    FOREIGN KEY (role_id) REFERENCES ufsite.roles (id),

    UNIQUE (user_id, role_id)
);


INSERT INTO ufsite.roles(role)
VALUES ('USER');
INSERT INTO ufsite.roles(role)
VALUES ('MODER');
INSERT INTO ufsite.roles(role)
VALUES ('ADMIN')