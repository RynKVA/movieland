-- Genre

create sequence if not exists genres_id_sequence start 1 increment by 50;

create  table if not exists genres (
id                            int              PRIMARY KEY DEFAULT nextval('genres_id_sequence'),
name                          text             NOT NULL UNIQUE
)