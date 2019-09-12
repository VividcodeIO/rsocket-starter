package io.vividcode.rsocketstarter.standalone;

import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Mono;

public class FireAndForgetExample {

  public static void main(String[] args) {
    RSocketFactory.receive()
        .acceptor(((setup, sendingSocket) -> Mono.just(
            new AbstractRSocket() {
              @Override
              public Mono<Void> fireAndForget(Payload payload) {
                System.out.println("Receive: " + payload.getDataUtf8());
                return Mono.empty();
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

    socket.fireAndForget(DefaultPayload.create("hello")).block();
    socket.fireAndForget(DefaultPayload.create("world")).block();

    socket.dispose();
  }
}
