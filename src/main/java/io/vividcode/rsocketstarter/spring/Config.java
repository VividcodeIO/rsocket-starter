package io.vividcode.rsocketstarter.spring;

import io.rsocket.RSocketFactory;
import io.rsocket.SocketAcceptor;
import io.rsocket.transport.ServerTransport;
import io.rsocket.transport.netty.server.WebsocketRouteTransport;
import org.springframework.boot.web.embedded.netty.NettyRouteProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.http.server.HttpServerRoutes;

@Configuration
@EnableWebFlux
public class Config {

  @Bean
  RSocketWebSocketNettyRouteProvider rSocketWebsocketRouteProvider(
      RSocketMessageHandler messageHandler) {
    return new RSocketWebSocketNettyRouteProvider("/ws",
        messageHandler.responder());
  }

  @Bean
  public RouterFunction<ServerResponse> staticResourcesRouter() {
    return RouterFunctions
        .resources("/**", new ClassPathResource("static/"));
  }

  static class RSocketWebSocketNettyRouteProvider implements NettyRouteProvider {

    private final String mappingPath;

    private final SocketAcceptor socketAcceptor;

    RSocketWebSocketNettyRouteProvider(String mappingPath, SocketAcceptor socketAcceptor) {
      this.mappingPath = mappingPath;
      this.socketAcceptor = socketAcceptor;
    }

    @Override
    public HttpServerRoutes apply(HttpServerRoutes httpServerRoutes) {
      ServerTransport.ConnectionAcceptor acceptor = RSocketFactory.receive()
          .acceptor(this.socketAcceptor)
          .toConnectionAcceptor();
      return httpServerRoutes.ws(this.mappingPath, WebsocketRouteTransport.newHandler(acceptor));
    }

  }

}
