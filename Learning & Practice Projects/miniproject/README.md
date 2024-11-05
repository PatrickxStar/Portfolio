# Spring Boot Miniproject - Dockerized

This project demonstrates how to containerize a Spring Boot application using Docker. It includes instructions for building and running the application with Docker and Docker Compose.

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Building and Running with Docker](#building-and-running-with-docker)
   - [Build the Docker Image](#1-build-the-docker-image)
   - [Run the Docker Container](#2-run-the-docker-container)
3. [Using Docker Compose](#using-docker-compose)
   - [Create the Docker Compose File](#1-create-the-docker-compose-file)
   - [Run the Application with Docker Compose](#2-run-the-application-with-docker-compose)
4. [Stopping and Cleaning Up](#stopping-and-cleaning-up)
5. [Troubleshooting](#troubleshooting)

## Prerequisites

- Docker installed on your machine ([Installation Guide](https://docs.docker.com/get-docker/))
- Docker Compose installed ([Installation Guide](https://docs.docker.com/compose/install/))
- Maven installed for building the JAR file ([Installation Guide](https://maven.apache.org/install.html))

## Building and Running with Docker


To format the README.md for IntelliJ, I will use a structured and clean markdown layout with proper headings, code blocks, and links. Below is the complete formatted README.md file content for your project:

markdown
Copy code
# Spring Boot Miniproject - Dockerized

This project demonstrates how to containerize a Spring Boot application using Docker. It includes instructions for building and running the application with Docker and Docker Compose.

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Building and Running with Docker](#building-and-running-with-docker)
   - [Build the Docker Image](#1-build-the-docker-image)
   - [Run the Docker Container](#2-run-the-docker-container)
3. [Using Docker Compose](#using-docker-compose)
   - [Create the Docker Compose File](#1-create-the-docker-compose-file)
   - [Run the Application with Docker Compose](#2-run-the-application-with-docker-compose)
4. [Stopping and Cleaning Up](#stopping-and-cleaning-up)
5. [Troubleshooting](#troubleshooting)

## Prerequisites

- Docker installed on your machine ([Installation Guide](https://docs.docker.com/get-docker/))
- Docker Compose installed ([Installation Guide](https://docs.docker.com/compose/install/))
- Maven installed for building the JAR file ([Installation Guide](https://maven.apache.org/install.html))

## Building and Running with Docker

### 1. Build the Docker Image

First, make sure your Spring Boot application is built. Run the following command to build the JAR file:

```bash
./mvnw clean package
Next, build the Docker image using the provided Dockerfile:

bash
Copy code
docker build -t spring-boot-miniproject .
2. Run the Docker Container
Run the container, mapping the application port to the host port:

bash
Copy code
docker run -dp 8080:8080 spring-boot-miniproject
You can now access the application at http://localhost:8080.

Using Docker Compose
If your application has dependencies (like a database), it's useful to define them in a docker-compose.yml file. Here's how you can add Docker Compose support to your project.

1. Create the Docker Compose File
Create a docker-compose.yml file in the root of your project directory with the following content:

yaml
Copy code
version: '3.8'
services:
  app:
    image: spring-boot-miniproject
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/mydb
      - SPRING_DATASOURCE_USERNAME=root
      - SPRING_DATASOURCE_PASSWORD=root
    depends_on:
      - db

  db:
    image: mysql:8
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: mydb
    ports:
      - "3306:3306"
This file sets up two services:

app: The Spring Boot application.
db: A MySQL database with default credentials.
2. Run the Application with Docker Compose
Build and start the application with Docker Compose:

bash
Copy code
docker-compose up --build
This will build the Docker image, start the Spring Boot application, and the MySQL database. Access the application at http://localhost:8080.

3. Stop and Remove Containers
To stop and remove the containers, run:

bash
Copy code
docker-compose down
Stopping and Cleaning Up
Stop the Container:

bash
Copy code
docker stop <container_id>
Remove the Container:

bash
Copy code
docker rm <container_id>
Remove the Docker Image (if needed):

bash
Copy code
docker rmi spring-boot-miniproject
Remove All Stopped Containers and Unused Images (optional):

bash
Copy code
docker system prune -a
Troubleshooting
Port Already in Use: Make sure port 8080 is not being used by another application.
Application Not Starting: Check the Docker container logs for errors.
bash
Copy code
docker logs <container_id>
Database Connection Issues: Ensure the database credentials and URL are correctly set in application.properties or environment variables.

### 1. Build the Docker Image

First, make sure your Spring Boot application is built. Run the following command to build the JAR file:

```bash
./mvnw clean package
