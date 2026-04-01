# Backend - Spring Boot

## Run
1. Create MySQL database or let Spring create it automatically.
2. Open `src/main/resources/application.properties`.
3. Set your MySQL password in `spring.datasource.password`.
4. Run:
   ```bash
   mvn spring-boot:run
   ```

## Default port
- Backend: `8080`

## Main APIs
- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/users`
- `GET /api/skills`
- `GET /api/drives`
- `GET /api/applications`
- `GET /api/dashboard`
