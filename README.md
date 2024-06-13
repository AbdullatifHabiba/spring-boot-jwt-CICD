# JWT CICD
![image](https://github.com/AbdullatifHabiba/spring-boot-jwt-CICD/assets/94381197/0e0437a9-d83b-4687-9f0f-e65a0535c4f9)

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
[https://ci-jwt.onrender.com/api/swagger-ui/index.html?configUrl=/v3/api-doc/swagger-config](https://ci-jwt.onrender.com/api/swagger-ui/index.html?configUrl=/v3/api-doc/swagger-config)

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


## CI/CD Pipeline

This document outlines the Continuous Integration and Continuous Deployment (CI/CD) pipeline for our project, detailing the automated process of integrating changes, testing, and deploying the application.

### Process Flow
![Screenshot from 2024-06-05 00-31-34](https://github.com/AbdullatifHabiba/spring-boot-jwt-CICD/assets/94381197/72c6a309-a758-493a-9ee5-7203cd98231b)


1. **Push Updates**: Developers push updates to the local repository, initiating the CI/CD pipeline.

2. **Trigger Master Branch**: The updates trigger the master branch on GitHub Actions, initiating the workflow.

3. **Automate Tests and Build Image**: GitHub Actions automates the execution of tests and builds the Docker image based on the updated code.

4. **Push Image to Docker Hub**: The Docker image is then pushed to Docker Hub, making it available for deployment.

5. **Pull New Image in Render Web Server**: The Render web server pulls the latest Docker image from Docker Hub to update the application.

6. **Connect to Render PostgreSQL Server**: The Render web server establishes a connection with the Render PostgreSQL server, ensuring data persistence.

7. **Client Application Receives Updated Deployment**: Finally, the client application receives the updated deployment from the Render web server, allowing users to access the latest version of the application.

### CI/CD Workflow

The CI/CD workflow is defined in the `.github/workflows` directory of our project repository. It consists of the following steps:

1. **Checkout repository**: The code repository is checked out to allow access to the codebase.

2. **Set up JDK 16**: The Java Development Kit (JDK) version 16 is configured using the `actions/setup-java@v3` action.

3. **Cache Maven packages**: Maven packages are cached to accelerate the build process.

4. **Validate the POM**: The Project Object Model (POM) file of the project is validated using Maven.

5. **Test with Maven**: Unit tests are executed using Maven to ensure code quality.

6. **Build with Maven**: The project is built, and a JAR file is generated using Maven.

7. **Move JAR to Docker folder**: The JAR file is moved from the `target` folder to the `docker` folder for Docker image creation.

8. **Set up Docker Buildx**: Docker Buildx is set up to facilitate building the Docker image.

9. **Login to DockerHub**: Authentication is performed to log into Docker Hub using the `docker/login-action@v1` action. Docker Hub credentials are stored as secrets in the GitHub repository settings.

10. **Build and push**: The Docker image is built and pushed to Docker Hub using the `docker/build-push-action@v2` action, making it available for deployment.

### Secrets

Docker Hub credentials are securely stored as secrets in the GitHub repository settings and used for authentication during the Docker image push process. These secrets ensure the confidentiality and security of the CI/CD pipeline.

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
  

