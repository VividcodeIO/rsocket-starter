package io.vividcode.rsocketstarter.standalone;

import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.WebsocketClientTransport;
import io.rsocket.transport.netty.server.WebsocketServerTransport;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Mono;

public class WebSocketRequestResponseExample {

  public static void main(String[] args) {
    RSocketFactory.receive()
        .acceptor(((setup, sendingSocket) -> Mono.just(
            new AbstractRSocket() {
              @Override
              public Mono<Payload> requestResponse(Payload payload) {
                return Mono.just(DefaultPayload.create("ECHO >> " + payload.getDataUtf8()));
              }
            }
        )))
        .transport(WebsocketServerTransport.create("localhost", 7000))
        .start()
        .subscribe();

    RSocket socket = RSocketFactory.connect()
        .transport(WebsocketClientTransport.create("localhost", 7000))
        .start()
        .block();

    socket.requestResponse(DefaultPayload.create("hello"))
        .map(Payload::getDataUtf8)
        .doOnNext(System.out::println)
        .block();

    socket.dispose();
  }
}
