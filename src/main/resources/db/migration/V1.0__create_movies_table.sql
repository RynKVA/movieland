-- Movies

create sequence if not exists movies_id_sequence start 1 increment 1;
create  table if not exists movies (
id                            bigint              PRIMARY KEY DEFAULT nextval('movies_id_sequence'),
name_native                   text,
name_russian                  text,
year_of_release               int,
release_country               text,
genres                        integer[],
description                   varchar(1000),
rating                        FLOAT(53),
price                         FLOAT(53),
poster                        varchar(255)
)