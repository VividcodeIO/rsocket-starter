package io.vividcode.rsocketstarter.spring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class DataCollectorTest extends AbstractTest {

  @Test
  @DisplayName("Test data collector")
  void testDataCollector() {
    RSocketRequester requester = createRSocketRequester();
    Mono<Void> result = requester.route("collect")
        .data("a")
        .send();
    StepVerifier.create(result)
        .verifyComplete();
  }
}
