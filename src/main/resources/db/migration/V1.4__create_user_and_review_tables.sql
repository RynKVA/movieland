--Users

create sequence if not exists users_id_sequence start 1 increment by 50;
create table if not exists users (
    id                            bigint              PRIMARY KEY DEFAULT nextval('users_id_sequence'),
    password                      varchar(50)         NOT NULL UNIQUE,
    email                         varchar(100)        NOT NULL UNIQUE,
    username                      varchar(50)         NOT NULL
);

--Reviews

create sequence if not exists reviews_id_sequence start 1 increment by 50;
create table if not exists reviews (
    id                            bigint              PRIMARY KEY DEFAULT nextval('reviews_id_sequence'),
    user_id                       bigint              ,
    movie_id                      bigint              NOT NULL,
    text                          varchar(1000),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (movie_id) REFERENCES movies(id)
)