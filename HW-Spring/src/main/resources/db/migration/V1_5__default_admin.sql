INSERT INTO users (id, username, password, enabled)
VALUES ('admin', 'admin', 'admin', true);

INSERT INTO authorities (id, authority)
VALUES ('admin', 'USER');

INSERT INTO authorities (id, authority)
VALUES ('admin', 'ADMIN');