create table users (
    id VARCHAR(256) NOT NULL PRIMARY KEY,
    username VARCHAR(256) NOT NULL,
    password VARCHAR(256) NOT NULL,
    enabled boolean
);

create table authorities (
    id  VARCHAR(256) REFERENCES users(id) ON DELETE CASCADE,
    authority VARCHAR(256) NOT NULL,
    PRIMARY KEY (id, authority)
);