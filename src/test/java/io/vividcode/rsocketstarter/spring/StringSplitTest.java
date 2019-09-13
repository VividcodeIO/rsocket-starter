package io.vividcode.rsocketstarter.spring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
class StringSplitTest extends AbstractTest {
  @Test
  @DisplayName("Test string split")
  void testStringSplit() {
    RSocketRequester requester = createRSocketRequester();
    Flux<String> response = requester.route("stringSplit")
        .data("hello")
        .retrieveFlux(String.class);

    StepVerifier.create(response)
        .expectNext("h", "e", "l", "l", "o")
        .expectComplete()
        .verify();
  }
}
