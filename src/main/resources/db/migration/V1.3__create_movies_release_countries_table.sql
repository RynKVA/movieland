
create sequence if not exists release_countries_id_sequence start 1 increment by 50;
create  table if not exists release_countries (
id                            int              PRIMARY KEY DEFAULT nextval('release_countries_id_sequence'),
name                          text             NOT NULL UNIQUE
);

create  table if not exists movies_release_countries (
movie_id                      bigint        NOT NULL,
release_country_id             int        NOT NULL,
PRIMARY KEY (movie_id, release_country_id),
FOREIGN KEY (movie_id) REFERENCES movies(id),
FOREIGN KEY (release_country_id) REFERENCES release_countries(id)
)