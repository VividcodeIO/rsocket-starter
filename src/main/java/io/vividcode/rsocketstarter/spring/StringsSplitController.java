package io.vividcode.rsocketstarter.spring;

import org.reactivestreams.Publisher;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
public class StringsSplitController {

  @MessageMapping("stringsSplit")
  public Flux<String> stringsSplit(Publisher<String> strings) {
    return Flux.from(strings).flatMap(
        input -> Flux.fromStream(input.codePoints().mapToObj(c -> String.valueOf((char) c))));
  }
}
