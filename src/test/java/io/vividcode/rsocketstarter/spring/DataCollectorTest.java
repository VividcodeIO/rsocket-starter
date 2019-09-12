package io.vividcode.rsocketstarter.spring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;

import java.time.Duration;

@SpringBootTest
class DataCollectorTest extends AbstractTest {
	@Test
	@DisplayName("Test data collector")
	void testDataCollector() {
		RSocketRequester requester = createRSocketRequester();
		requester.route("collect")
				.data("a")
				.send()
				.block(Duration.ofSeconds(10));
	}
}
