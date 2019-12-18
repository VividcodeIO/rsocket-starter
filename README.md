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

## Guide

You can check out this guide about how to use RSocket.

* [Get Started with RSocket - Part 1: Protocol and Standalone Usage](https://vividcode.io/get-started-with-rsocket-part-1-protocol-and-standalone-usage/)
* [Get Started with RSocket - Part 2: Spring Integration](https://vividcode.io/get-started-with-rsocket-part-2-spring-integration/)
* [Get Started with RSocket - Part 3: WebSocket](https://vividcode.io/get-started-with-r-socket-part-3-web-socket/)