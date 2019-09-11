package io.vividcode.rsocketstarter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EchoServerTest extends AbstractTest {

	@Test
	@DisplayName("Test echo server")
	void testEcho() {
		RSocketRequester requester = createRSocketRequester();
		String response = requester.route("echo")
				.data("hello")
				.retrieveMono(String.class)
				.block(Duration.ofSeconds(10));
		assertEquals("ECHO >> hello", response);
	}

}
