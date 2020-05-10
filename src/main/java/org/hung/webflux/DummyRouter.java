package org.hung.webflux;

import java.io.IOException;

import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.netty.handler.ssl.SslContextBuilder;
import reactor.netty.http.server.HttpServer;
import reactor.netty.tcp.SslProvider;

@Configuration
public class DummyRouter {
	
	private Resource keyFile;
	
	private Resource certFile;
	
	@Bean
	public RouterFunction<ServerResponse> route(DummyHandler handler) {
		return RouterFunctions
				.route()
				  .GET("/echo", handler::echo)
				  .GET("/echo2", handler::echo2)
				  .GET("/interval", handler::interval)
				  .build();
	}

}
