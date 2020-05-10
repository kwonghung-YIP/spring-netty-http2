package org.hung.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebFluxSecurity
public class WebFluxSecurityConfig {
	
	@Bean
	public MapReactiveUserDetailsService userDetailsService() {
		UserDetails user = 
			User
			  .withUsername("user")
			  	.passwordEncoder(passwordEncoder()::encode)
			    .password("password")
				.roles("USER")
				.build();
		UserDetails admin = 
			User
			  .withUsername("admin")
			  	.passwordEncoder(passwordEncoder()::encode)
			    .password("password")
				.roles("ADMIN")
				.build();
		return new MapReactiveUserDetailsService(user,admin);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http
		  .authorizeExchange()
		    .pathMatchers("/actuator","/actuator/**").hasRole("ADMIN")
		    .anyExchange().authenticated()
		  .and()
		    .httpBasic();
		return http.build();
	}	

}
