insert into movies (
  id, name_native, release_country, genres,
  year_of_release, rating, price, description, poster
)
values
  (1, 'The Shawshank Redemption', 'USA',
   ARRAY[1,2], 1994, 8.9, 345.67, 'Two imprisoned',
   'https://images-na.ssl-images-amazon.com/images/M/MV5BMTY4NzQ3NjA2OF5BMl5BanBnXkFtZTcwNTI0MjEyMQ@@._V1._SY209_CR0,0,140,209_.jpg'),

  (2, 'The Godfather', 'USA',
   ARRAY[3,4], 1972, 9.2, 150.00, 'Mafia boss',
   'https://images-na.ssl-images-amazon.com/images/M/MV5BMTY4NzQ3NjA2OF5BMl5BanBnXkFtZTcwNTI0MjEyMQ@@._V1._SY209_CR0,0,140,209_.jpg'),

  (3, 'Inception', 'USA',
   ARRAY[5], 2010, 8.8, 180.50, 'Dream layers',
   'https://images-na.ssl-images-amazon.com/images/M/MV5BMTY4NzQ3NjA2OF5BMl5BanBnXkFtZTcwNTI0MjEyMQ@@._V1._SY209_CR0,0,140,209_.jpg'),

  (4, 'Pulp Fiction', 'USA',
   ARRAY[1,5], 1994, 8.9, 100.55, 'Two imprisoned',
   'https://images-na.ssl-images-amazon.com/images/M/MV5BMTY4NzQ3NjA2OF5BMl5BanBnXkFtZTcwNTI0MjEyMQ@@._V1._SY209_CR0,0,140,209_.jpg');