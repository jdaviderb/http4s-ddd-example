[![Build Status](https://travis-ci.com/jdaviderb/http4s-ddd-example.svg?branch=master)](https://travis-ci.com/jdaviderb/http4s-ddd-example) [![Coverage Status](https://coveralls.io/repos/github/jdaviderb/http4s-ddd-example/badge.svg)](https://coveralls.io/github/jdaviderb/http4s-ddd-example) [![Maintainability](https://api.codeclimate.com/v1/badges/155a85357b745db2e5eb/maintainability)](https://codeclimate.com/github/jdaviderb/http4s-ddd-example/maintainability)

in this project I applied the lessons learned from [@codelyTv](https://github.com/CodelyTV), this project is a simple API with authorization using JWT


## 🐣 Setup

* `sbt flyWayMigrate` — run database migrations
* `SCALA_ENV=test sbt flyWayMigrate` — run database migrations for tests
* `sbt run` — run http4s server
* `sbt test` — test suite

**envs:**
```bash
  API_POSTGRES_DATABASE=XXX
  API_POSTGRES_USER=XXX
  API_POSTGRES_PASS=XXX
  API_JWT_SECRET=XXXXXXX

  # tests suite envs
  API_POSTGRES_TEST_DATABASE=XXX
  API_POSTGRES_TEST_USER=XXX
  API_POSTGRES_TEST_PASS=XXX
```
##  📜 Endpoints

- `POST /authenticate:` authentication `payload: (name: String)`
- `GET /tasks:` Returns all tasks.
- `POST /tasks:` creates a task `payload: (title: String, done: Boolean)`
- `GET /tasks/:id:` returns a task
- `PUT /tasks/:id` updates a task `payload: (title: String, done: Boolean)`
- `DELETE /tasks/:id `deletes a task


## Dependencies
- [http4s](https://github.com/http4s/http4s) - **A minimal, idiomatic Scala interface for HTTP**
- [doobie](https://github.com/tpolecat/doobie) - **Functional JDBC layer for Scala.**
- [jOOQ](https://github.com/jOOQ/jOOQ) - **jOOQ is the best way to write SQL in Java**
- [jwt-scala](https://github.com/pauldijou/jwt-scala) - **JWT support for Scala**


## 🤔Project structure

```
src
├── main
│   ├── resources
│   │   └── db
│   │       └── migration # → Migration files
│   │           └── V1__create_tasks_table.sql
│   └── scala
│       ├── bounded_contexts # → DDD Bounded contexts
│       │   ├── authentication
│       │   │   ├── application # → DDD application services (public access)
│       │   │   │   ├── AuthenticateUser.scala
│       │   │   │   └── DecodeToken.scala
│       │   │   └── domain
│       │   │       └── AuthenticationService.scala
│       │   ├── share # → DDD share kernel
│       │   │   ├── domain
│       │   │   │   └── UserEntity.scala
│       │   │   └── infrastructure
│       │   │       └── DoobieConnection.scala # → DATABASE CONNECTION
│       │   └── tasks
│       │       ├── application # → DDD application services (public access)
│       │       │   ├── CreateTaskApplicationService.scala
│       │       │   ├── DeleteTaskApplicationService.scala
│       │       │   ├── FilterTaskApplicationService.scala
│       │       │   ├── FindTaskApplicationService.scala
│       │       │   └── UpdateTaskApplicationService.scala
│       │       ├── domain
│       │       │   ├── TaskEntity.scala
│       │       │   └── TaskRepositoryContract.scala
│       │       └── infrestucture
│       │           └── TaskRepository.scala
│       ├── conf
│       │   └── Settings.scala # → APP CONFIGURATION
│       └── server # → http4s server
│           ├── Server.scala
│           └── services
│               ├── Services.scala
│               ├── authentication
│               │   ├── AuthenticateService.scala
│               │   └── TokenSerializer.scala
│               ├── helpers
│               │   └── Message.scala
│               ├── middleware
│               │   └── AuthMiddleware.scala # → api authorization
│               └── tasks
│                   ├── CreateTaskService.scala
│                   ├── DeleteTaskService.scala
│                   ├── GetTaskService.scala
│                   ├── ListTaskService.scala
│                   └── UpdateTaskService.scala
```
