# RSocket examples

## Examples

Examples in this repo:

 * Standalone examples
 * Spring messaging examples
 * WebSocket 

## Run WebSocket example

### Local

Go to `src/app` and run `yarn run start` to start dev server. You need to start Spring Boot server first.

### Production build

Run `mvn package` to build UI assets and package a single executable JAR file.

```bash
$ java -jar target/rsocket-starter-0.0.1-SNAPSHOT.jar
```

View the UI in `http://localhost:8080/index.html`.