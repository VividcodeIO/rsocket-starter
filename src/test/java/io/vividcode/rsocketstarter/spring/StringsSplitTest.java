package io.vividcode.rsocketstarter.spring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
public class StringsSplitTest extends AbstractTest {
  @Test
  @DisplayName("Test strings split")
  void testEcho() {
    RSocketRequester requester = createRSocketRequester();
    Flux<String> response = requester.route("stringsSplit")
        .data(Flux.fromArray(new String[] {"hello", "world"}))
        .retrieveFlux(String.class);

    StepVerifier.create(response)
        .expectNext("h", "e", "l", "l", "o", "w", "o", "r", "l", "d")
        .expectComplete()
        .verify();
  }
}