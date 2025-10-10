package org.sav.fornas.iotback.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
						.anyRequest().hasRole("IOT_USER")
				)
				.oauth2ResourceServer(oauth2 -> oauth2
						.jwt(jwt -> jwt
								.jwtAuthenticationConverter(jwtAuthenticationConverter())
						)
				)
				.exceptionHandling(ex -> ex
						// користувач не авторизований → 401
						.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
						// користувач авторизований, але не має ролі → 403
						.accessDeniedHandler((request, response, accessDeniedException) -> {
							response.setStatus(HttpStatus.FORBIDDEN.value());
							response.getWriter().write("У вас немає доступу");
						})
				)
				.build();
	}


	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter rolesConverter = new JwtGrantedAuthoritiesConverter();
		rolesConverter.setAuthorityPrefix("");
		rolesConverter.setAuthoritiesClaimName("roles");

		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		converter.setJwtGrantedAuthoritiesConverter(rolesConverter);
		return converter;
	}
}

