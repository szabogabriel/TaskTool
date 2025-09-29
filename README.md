# Task Tool

This project is aimed to showcase a simple Spring Boot application with all the nuances necessary at a production-grade application.

## Prerequisites

- Java 17 or newer
- [Visual Studio Code](https://code.visualstudio.com/)
- [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) for VS Code
- [Maven](https://maven.apache.org/) (or use the included `mvnw`/`mvnw.cmd` wrapper)
- Docker or Podman (for containerization, optional)

## Setup in Visual Studio Code

1. **Clone the repository**
   ```sh
   git clone <repository-url>
   cd jAcademy@IBM
   ```

2. Open the project in VS Code

- Launch VS Code.
- Select File > Open Folder... and choose the project directory.

3. Install recommended extensions

- When prompted, install the recommended extensions for Java and Spring Boot.

4. Build the project

- Open the integrated terminal (`Ctrl+``).
Run:
`./mvnw clean package`
or on Windows:
`mvnw.cmd clean package`

5. Run the application

- In the terminal, execute:
`./mvnw spring-boot:run`
or use the VS Code Run/Debug panel to start TodoApplication.

6. Access the application

- The API will be available at http://localhost:8080.

### Running in a Container

1. Build the container image

`./build.sh`

2. Run the container

`./run.sh`

The application will be available at http://localhost:8080.

## Running Tests
To run unit tests:

`./mvnw test`

## Project Structure

- java – Application source code
- java – Unit tests
- legacy – Legacy demo code (not part of main app)
- Containerfile – Container build instructions
- build.sh, run.sh – Scripts for building and running in a container

## Notes

- The application uses an in-memory or file-based database by default.
- For production, configure environment variables or application properties as needed.