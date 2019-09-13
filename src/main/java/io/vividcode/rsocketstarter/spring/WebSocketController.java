package io.vividcode.rsocketstarter.spring;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
public class WebSocketController {
  @MessageMapping("")
  public Flux<String> webSocket(String input) {
    return Flux.fromStream(input.codePoints().mapToObj(c -> String.valueOf((char) c)));
  }
}
