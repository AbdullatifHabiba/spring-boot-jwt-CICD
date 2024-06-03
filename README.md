# JWT Demo

### Overview
This demo is my point of view of how spring boot application should look like, code structure and clean code principles.

The system is nothing but JWT handling using java 16 and spring boot 

### Features
* Rest API best practices 
* Spring Security 
* Exception Handling using AOP
* Junit 5 and Mockito
* Integration Tests with Test Containers
* Jacoco test coverage

### How to start up the application?
* You need to have docker installed, or if you have postgresql installed locally, you can skip second step
* Run docker-compose file under docker directory using, it will create local instance of postgre DB
```docker-compose up -d```
* Connect to DB and create new database using ``CREATE DATABASE "jwt-db"``
* Create user using ``create user jwtuser with password 'letmein'`` if you already have DB installed, or change username and password in application.yml file to match your existing database user.
* Start the application from any IDE of your choice


### Application Testing
The project has admin setup under main class with user defined within application.yml (admin.details), you can use the default "msweelam" with password "sweelam123" to test APIs.
Or you can change it and use a user details of your choice.

Some curl command to try
* Login API
````
curl --location --request POST 'localhost:8082/api/login' \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --data-urlencode 'username=msweelam' \
  --data-urlencode 'password=sweelam123' -i 
````

* Having the token you can use it via the following curl commands or through swagger

#### 1. Swagger
You have to authorize yourself first using the **access_token** from swagger before executing any API
http://localhost:8082/api/swagger-ui/index.html?configUrl=/v3/api-doc/swagger-config

#### 2. CURL testing
* Add user you need to replace the **access_token** with the provided header token from login API 
````
curl --location --request POST 'localhost:8082/api/user' \
--header 'Authorization: Bearer <access_token>' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Alaa Mahmoud",
    "username": "alaMahmoud",
    "password": "all123",
    "roles": [
        {
            "name": "USER"
        }
    ]
}'
````
* Refresh token API using the **refresh_token** provided from login if **access_token** expired
````
curl --location --request GET 'localhost:8082/api/token' \
--header 'Authorization: Bearer <refresh_token>' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Alaa Mahmoud",
    "username": "alaMahmoud",
    "password": "all123",
    "roles": [
        {
            "name": "USER"
        }
    ]
}'
````
* Retrieve al users 
````
curl --location --request GET 'localhost:8082/api/user' \
--header 'Authorization: Bearer <access_token>'
````

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
  
## Continuous Integration and Continuous Deployment (CI/CD)

This project uses GitHub Actions for Continuous Integration (CI) and Continuous Deployment (CD). The workflow is defined in the `.github/workflows` directory.

### Workflow

The workflow is triggered on every `push` and `pull_request` event to the `master` branch. It consists of the following steps:

1. **Checkout repository**: This step checks out the code repository so that the workflow can access it.

2. **Set up JDK 16**: This step sets up the Java Development Kit (JDK) using the `actions/setup-java@v3` action.

3. **Cache Maven packages**: This step caches the Maven packages to speed up the build process.

4. **Validate the POM**: This step validates the Project Object Model (POM) file of the project using Maven.

5. **Test with Maven**: This step runs the unit tests of the project using Maven.

6. **Build with Maven**: This step builds the project and packages it into a JAR file using Maven.

7. **Move JAR to Docker folder**: This step moves the JAR file from the `target` folder to the `docker` folder.

8. **Set up Docker Buildx**: This step sets up Docker Buildx, which is used to build the Docker image.

9. **Login to DockerHub**: This step logs into Docker Hub using the `docker/login-action@v1` action. The Docker Hub username and token are stored as secrets in the GitHub repository settings.

10. **Build and push**: This step builds the Docker image and pushes it to Docker Hub using the `docker/build-push-action@v2` action.


### Secrets

The Docker Hub username and token are stored as secrets in the GitHub repository settings. These secrets are used to log into Docker Hub in the workflow.

