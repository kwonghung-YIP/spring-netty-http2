package org.hung.webflux;

import java.security.Principal;
import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class DummyHandler {

	public Mono<ServerResponse> echo(ServerRequest request) {
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue("Hello from webflux"));
	}
	
	public Mono<ServerResponse> echo2(ServerRequest request) {
		return request
		  .principal()
		    .map(Principal::getName)
		    .flatMap(user -> ServerResponse.ok()
		    	.body(BodyInserters.fromValue("Hello User " + user)));
	}
	
	public Mono<ServerResponse> interval(ServerRequest request) {
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_STREAM_JSON)
				.body(Flux.interval(Duration.ofSeconds(2)).log(), Long.class);
		/*return Mono
				.from(Flux.interval(Duration.ofSeconds(2)).log())
				.flatMap(l -> 
					ServerResponse.ok()
					.contentType(MediaType.APPLICATION_STREAM_JSON)
					.body(BodyInserters.fromValue(l))
				);*/
	}
}
