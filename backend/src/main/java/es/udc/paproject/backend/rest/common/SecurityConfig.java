package es.udc.paproject.backend.rest.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtGenerator jwtGenerator;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.cors(Customizer.withDefaults())
				.csrf((csrf) -> csrf.disable())
				.sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(new JwtFilter(jwtGenerator), UsernamePasswordAuthenticationFilter.class)
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers(HttpMethod.POST, "/users/signUp").permitAll()
						.requestMatchers(HttpMethod.POST, "/users/login").permitAll()
						.requestMatchers(HttpMethod.POST, "/users/loginFromServiceToken").permitAll()
						//FUNC-1 acceso a la cartelera
						//.requestMatchers(HttpMethod.GET, "/cinema/movies").permitAll()
						//FUNC-2 edito el de arriba porque ademas de ver la cartelera tenemos que poder ver cada peli
						.requestMatchers(HttpMethod.GET, "/cinema/movies", "/cinema/movies/*").permitAll()
                        //FUNC-3 detalle de sesion
                        .requestMatchers(HttpMethod.GET, "/cinema/sessions/*").permitAll()
                        //FUNC-4 comprar entrada
                        .requestMatchers(HttpMethod.POST, "/cinema/buy").hasRole("VIEWER")
                        //FUNC-5 historico de compras
                        .requestMatchers(HttpMethod.GET, "/cinema/purchases").hasRole("VIEWER")
                        //FUNC-6 entregar entradas
                        .requestMatchers(HttpMethod.POST, "/cinema/purchases/*/deliver").hasRole("TICKET_SELLER")
						.requestMatchers(HttpMethod.PUT, "/users/*").hasAnyRole("VIEWER", "TICKET_SELLER")
						.requestMatchers(HttpMethod.POST, "/users/*/changePassword").hasAnyRole("VIEWER", "TICKET_SELLER")
						.anyRequest().denyAll());

		return http.build();

	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration config = new CorsConfiguration();
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		config.setAllowCredentials(true);
		config.setAllowedOriginPatterns(Arrays.asList("*"));
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");

		source.registerCorsConfiguration("/**", config);

		return source;

	}

}
