# spring-boot-demo
This is a Maven project that explores concepts from Spring Boot 3 for properly implementing respositories using Spring Data JPA, Specifications, Projections with DTOs and DB versioning using flyway

- Requirements
  - You need to set up env variables for connecting to your MySQL, variables are referenced in `application.yaml` 

  1. `MYSQL_URL` - recommended to use: `jdbc:mysql://localhost:3306/store?createDatabaseIfNotExist=true`
  2. `MYSQL_USER` - your MySQL user
  3. `MYSQL_PASSWORD` - your MySQL password

- (Optional) if you want to run individual goals from flyway (e.g. maven flyway:migrate) you need to manually update `flyway.conf` file to add:
  1. `FLYWAY_URL` - recommended to use: `jdbc:mysql://localhost:3306/store?createDatabaseIfNotExist=true`
  2. `FLYWAY_USER` - your MySQL user
  3. `FLYWAY_PASSWORD` - your MySQL password