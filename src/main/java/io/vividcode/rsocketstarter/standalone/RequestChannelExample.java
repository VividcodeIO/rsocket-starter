package io.vividcode.rsocketstarter.standalone;

import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RequestChannelExample {

  public static void main(String[] args) {
    RSocketFactory.receive()
        .acceptor(((setup, sendingSocket) -> Mono.just(
            new AbstractRSocket() {
              @Override
              public Flux<Payload> requestChannel(Publisher<Payload> payloads) {
                return Flux.from(payloads).flatMap(payload ->
                    Flux.fromStream(
                        payload.getDataUtf8().codePoints()
                            .mapToObj(c -> String.valueOf((char) c))
                            .map(DefaultPayload::create)));
              }
            }
        )))
        .transport(TcpServerTransport.create("localhost", 7000))
        .start()
        .subscribe();

    RSocket socket = RSocketFactory.connect()
        .transport(TcpClientTransport.create("localhost", 7000))
        .start()
        .block();

    socket.requestChannel(Flux.just("hello", "world", "goodbye").map(DefaultPayload::create))
        .map(Payload::getDataUtf8)
        .doOnNext(System.out::println)
        .blockLast();

    socket.dispose();
  }
}
