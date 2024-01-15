# TiTi Backend

## Links

| name     | url           |
|----------|---------------|
| dev docs | (To be added) |

## Getting Started

### Development Environment

- [JDK 21](https://openjdk.org/projects/jdk/21/)
- [Spring Boot 3.1.4](https://spring.io/blog/2023/09/21/spring-boot-3-1-4-available-now/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa/)
    - [Hibernate 6.2.17.Final](https://hibernate.org/orm/releases/6.2/)
- [Spring Security 6.1.4](https://docs.spring.io/spring-security/reference/6.1/index.html)
- [Swagger 3.0](https://swagger.io/specification/)
- [JUnit 5.9.3](https://junit.org/junit5/docs/5.9.3/release-notes/)
- [MySQL 8.0](https://dev.mysql.com/doc/relnotes/mysql/8.0/en/)
- [Gradle 8.5](https://docs.gradle.org/8.5/release-notes.html)

### Setting local environment

```
$ docker-compose up && docker-compose rm -fsv
```

## Project Configuration

### Hexagonal Architecture

![hexagonal_architecture.png](hexagonal_architecture.png)
```
├── 📂titi-adapter     ▶️ Adapter module that implements specific operations that go outside the system
│     ├── 📂 in
│     └── 📂 out
├── 📂titi-application ▶️ A module that is responsible for domain access and business logic and provides in and out ports
│     ├── 📂 in
│     └── 📂 out
└── 📂titi-domain      ▶️ Domain Module
```
### TiTi Architecture

```
└──🔹titi-backend
      ├──📂.github ▶️ Github Template
      ├──📂sql ▶️ TITI Local DB Schema Management
      ├──📂src/main/java/com/titi
      │     ├── 📂auth ▶️ Authentication/Authorization Module
      │     │     ├── 📂adapter
      │     │     │     ├── 📂in
      │     │     │     │     ├── 📂security
      │     │     │     │     │     ├── 📂authentication
      │     │     │     │     │     │     └── 📂jwt
      │     │     │     │     │     ├── 📂config
      │     │     │     │     │     ├── 📂constant
      │     │     │     │     │     └── 📂matcher
      │     │     │     │     ├── 📂web
      │     │     │     │     └── 📂internal
      │     │     │     └── 📂out
      │     │     │     │     ├── 📂cache
      │     │     │     │     │     └── 📂redis
      │     │     │     │     │     │     ├── 📂entity
      │     │     │     │     │     │     └── 📂repository
      │     │     │     │     └── 📂persistence
      │     │     │     │     │     └── 📂jpa
      │     │     │     │     │     │     ├── 📂entity
      │     │     │     │     │     │     └── 📂repository
      │     │     ├── 📂application
      │     │     │     ├── 📂service
      │     │     │     ├── 📂in
      │     │     │     ├── 📂out
      │     │     │     └── 📂util
      │     │     └── 📂domain
      │     ├── 📂common ▶️ Common Module
      │     │     ├── 📂constant
      │     │     ├── 📂dto
      │     │     └── 📂exception
      │     ├── 📂crypto ▶️ Encryption/decryption Library
      │     │     ├── 📂constant
      │     │     ├── 📂exception
      │     │     └── 📂uitl
      │     └── 📂infrastructure ▶️ Infrastructure Configuration Package
      │     │     ├── 📂cache
      │     │     │     └── 📂redis
      │     │     │     │     └── 📂config
      │     │     └── 📂persistence
      │     │     │     ├── 📂config
      │     │     │     └── 📂entity
      ├──⚙️.editorconfig
      ├──📄.gitattributes
      ├──📄.gitignore
      ├──🐘build.gradle
      ├──🐳docker-compose.yml ▶️ Script for configuring MySQL local environment
      ├──📄README.md
      ├──🐘settings.gradle
      └──📜titi_formatter.xml ▶️ TiTi Java Code Formatter
```
