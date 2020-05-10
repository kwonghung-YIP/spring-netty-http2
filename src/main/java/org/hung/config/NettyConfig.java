package org.hung.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import io.netty.handler.ssl.SslContextBuilder;
import lombok.extern.slf4j.Slf4j;
import reactor.netty.http.HttpProtocol;

@Slf4j
@Configuration
public class NettyConfig {

	@Value("file:C:\\Users\\kwong\\Documents\\certs\\selfsigned\\ca\\my-service.key")
	private Resource keyFile;
	
	@Value("file:C:\\Users\\kwong\\Documents\\certs\\selfsigned\\ca\\my-service.crt")
	private Resource certChainFile;
	
	@Bean
	public NettyServerCustomizer sslSetting() throws IOException {
		log.info("hey hey!");
		
		SslContextBuilder sslCtxBuilder = getSslContentBuilder();
		
		return (httpServer) -> {
			return httpServer
			  .secure((spec) -> {
				  spec.sslContext(sslCtxBuilder);
			  })
			  .protocol(HttpProtocol.H2, HttpProtocol.HTTP11);
		};
	}
	
	private SslContextBuilder getSslContentBuilder() throws IOException {
		SslContextBuilder builder = 
		  SslContextBuilder
		    .forServer(certChainFile.getInputStream(), keyFile.getInputStream());
		return builder;
	}
}
