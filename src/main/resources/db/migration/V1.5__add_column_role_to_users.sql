alter table users add column if not exists "role" varchar(50) not null default 'USER';
alter table public.users alter column "password" TYPE VARCHAR(256);