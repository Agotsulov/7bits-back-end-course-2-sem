CREATE TABLE task (
                    id varchar PRIMARY KEY,
                    name varchar NOT NULL,
                    status varchar DEFAULT 'inbox',
                    createAt varchar NOT NULL
);
