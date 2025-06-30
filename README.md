# spring-boot-demo
This is a Maven project that explores concepts from Spring Boot 3 for properly implementing respositories using Spring Data JPA, Specifications, Projections with DTOs and DB versioning using flyway

- Requirements
  - You need to set up env variables for connecting to your MySQL, variables are referenced in `application.yaml` 

  1. `MYSQL_URL` - recommended to use: `jdbc:mysql://localhost:3306/store?createDatabaseIfNotExist=true`
  2. `MYSQL_USER` - your MySQL user
  3. `MYSQL_PASSWORD` - your MySQL password