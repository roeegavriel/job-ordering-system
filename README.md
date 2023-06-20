# Job Ordering System
By Roee Gavriel

## Prerequisites
 - Java 17
 - Docker

## how to test (unit tests)
```shell
./gradlew test
```

## How to run
In the root directory start MySql docker by running
```shell
docker-compose up --wait
```

(Optional) To create initial dev entities in the database when the app is booting set the following environment variables:
```shell
export SPRING_PROFILES_ACTIVE=dev
```

(Optional) To activate the mail sender set the following environment variables:
```shell
export MAIL_SENDER_ACTIVE=true
export MAIL_SENDER_USERNAME=<GmailUsername>
export MAIL_SENDER_PASSWORD=<GmailApiPassword>
```

Then start the application by running
```shell
./gradlew application:bootRun
```

## Postman collection
A postman collection with examples for all available endpoints can be found in `postman/` directory

## Additional info

### Database
The application uses MySql as its database. For local development there is a need to run a MySql 8.0.17 docker container.
I found it cleaner to run the MySql in a container than to install it on the local machine. I also evaluated working with H2
database (in memory or with file storage), but in general I prefer working as close as possible to production. 

### Mail sender
The application uses Gmail SMTP server to send emails to customers.
If active, the mail sender will send emails to customers when jobs are responded to by workers.

The credentials for the mail sender are not included in the code and should be set as environment variables for security reasons.
For production, I would expect the credentials to be injected to the application by Kubernetes or other deployment tool.

### OpenAPI (Swagger) documentation
After running the application, you can access the OpenAPI documentation at http://localhost:8080/v1/api-docs

### Spring Hibernate
The application uses Spring Hibernate to manage the database. I wouldn't be my first choice for a production application,
but it's a good fit for this exercise, and as I understand it, it's part of your tech stack, and I wanted to be aligned with it.

Some reasons I wouldn't use it in production:
- It's not very flexible. It's hard to customize the queries it generates.
- OneToMany and ManyToOne relations are only relevant when the "many" are in small number. However, when scale is needed,
  there is no direct way to get the "many" in pages. Instead, you have to get all of them.
- Relations are automatically mapped to FK in the DB. While providing another verification that the data is valid, it also
  makes it harder to change the DB schema in the future, and it creates more load on the DB. I rather validate the data in the application
  instances where loading more instances is easy.
- FK also creates coupling and may become a problem when trying to split the application to microservices.

## Next steps
A list of things I would consider adding to this project before it's production ready

- Integration tests
  - Running the application with a real MySql database. In order to implement a full flow there is a need for API's to create
    customers, workers and jobs.
- CI/CD
  - Build the application and run the tests on every commit (probably on Github actions, Travis or Circleci)
  - Build a docker image and push it to a docker registry (probably Dockerhub / AWS ECR)
  - Build Java SDK based on the open-api definitions and publish it to a maven repository (probably Jfrog Artifactory)
  - Deploy the application to production using the tested docker image (probably Kubernetes)
- Authentication and authorization
- Observability and monitoring
  - metrics (probably Actuator with Prometheus or Statsd)
    - Adding auto-scaling based on resources metrics 
  - dashboards (probably Grafana)
  - logs (collect the logs to a central place probably ELK, Coralogix or equivalent)
  - tracing (probably Spring Sleuth with Jaeger)
  - alerts (probably Prometheus alert-manager, or Grafana alerts)
