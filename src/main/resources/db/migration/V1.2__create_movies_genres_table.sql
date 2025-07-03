ALTER TABLE movies
DROP COLUMN if exists genres,
DROP COLUMN if exists release_country;

create  table if not exists movies_genres (
movie_id                           bigint        NOT NULL ,
genre_id                            int           NOT NULL,
PRIMARY KEY (movie_id, genre_id),
FOREIGN KEY (movie_id) REFERENCES movies(id),
FOREIGN KEY (genre_id) REFERENCES genres(id)
)