package io.vividcode.rsocketstarter;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Mono;

@Controller
public class DataCollector {
	@MessageMapping("collect")
	public Mono<Void> collect(String data) {
		System.out.println("Received >> " + data);
		return Mono.empty();
	}
}
