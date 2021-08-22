## User Authentication Application created with Spring.

The app provides access for users to 4 different pages:

- /home - accessible to everyone
- /user - accessible to logged-in user with role `USER`
- /admin - accessible to logged-in user with role `ADMIN`
- /error - page displayed when users are not authorized to access a page

### Classes and their usage:

- `MvcConfiguration` - `WebMvcConfigurer` implementation used to set up the view controllers
  exposing the pages mentioned above
- `SecurityConfiguration` - `WebSecurityConfigurerAdapter` subclass used to secure and set
  permissions for the pages
- `User` - entity object containing all the data for a given user
- `CustomUserDetailsService` - `UserDetailsService` implementation that provides the `UserDetails`
  for the given userName. This service will find the user by using the `UserRepository`
- `CustomUserDetails` - `UserDetails` implementation that represents the response returned by the
  service mentioned above
- `CustomAccessDeniedHandler` - `AccessDeniedHandler` implementation that handles the situations
  when a user is trying to access a page with no permission

### Users data

The users data is saved in a table called `users` on a local postgresql database with the access
details being displayed in the `application.properties` file. The table and some user entries could
be created with the following SQL script:

```sql
DROP TABLE IF EXISTS public.users;
CREATE TABLE public.users
(
  id SERIAL PRIMARY KEY,
  user_name VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  active BOOLEAN NOT NULL,
  age INTEGER NOT NULL,
  address VARCHAR(255) NOT NULL,
  roles VARCHAR(255) NOT NULL
)

INSERT INTO users (user_name, password, active, age, address, roles)
VALUES('iulian', 'password', TRUE, 25, 'Bucuresti, Strada X, Nr. Y', 'ROLE_ADMIN'),
      ('tom', 'password', TRUE, 34, 'Bucuresti, Strada A, Nr. B', 'ROLE_USER'),
      ('arthur', 'password', TRUE, 14, 'Bucuresti, Strada O, Nr. P', 'ROLE_HACKER'),
      ('john', 'password', FALSE, 14, 'Bucuresti, Strada H, Nr. G', 'ROLE_USER');
```
