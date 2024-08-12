# OTA Notes
A simple REST backend for note-taking

## Technologies used
* Java 21
* Spring Boot 3
* Maven 3 (build wrapper included)

## Running the application
1. Clone the repository:
   ```bash
   git clone git@github.com:janbetco/ota-notes.git
   ```
2. Run the application:
   ```bash
   cd ota-notes
   ./mvnw spring-boot:run
   ```

## API Documentation
The REST API is built using OpenAPI. Documentation is accessible when the app is running: http://localhost:8080/swagger-ui/index.html

## Assumptions made on the requirements
- A note contains two fields: title and content
  * Title is optional. It has a character limit of 200
  * Content is required. It has a character limit of 10,000
- The note ID data type is UUID
- Data is not persistent. It is lost between application restarts.
