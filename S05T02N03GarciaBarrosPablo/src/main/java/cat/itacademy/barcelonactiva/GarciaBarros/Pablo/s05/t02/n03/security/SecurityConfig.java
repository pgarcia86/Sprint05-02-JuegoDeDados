package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
public class SecurityConfig {
	
	
	private CustomUserDetailsServices userDetailServiceImpl;
	private JwtAuthEntryPoint authEntryPoint;
	
	@Autowired
	public SecurityConfig(CustomUserDetailsServices userDetailServiceImpl, JwtAuthEntryPoint authEntryPoint) {
		this.userDetailServiceImpl = userDetailServiceImpl;
		this.authEntryPoint = authEntryPoint;
	}
	
	
	//Aqui configuro la cadena de filtros para segurizar mi API
	@Bean 
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		/*
		http
			.csrf((csrf)->csrf.disable())
			.exceptionHandling((exHand) -> exHand.authenticationEntryPoint(authEntryPoint))
			.sessionManagement((sessManag) -> sessManag.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/**").permitAll()
            .anyRequest().authenticated()
        );
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
		*/
		
		 http
	         .csrf((csrf)->csrf.disable())
	         .exceptionHandling((eh)-> eh.authenticationEntryPoint(authEntryPoint))
	         .sessionManagement((sm) -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	         .authorizeHttpRequests((ar) -> ar.requestMatchers("/auth/**").permitAll()
	        		 .anyRequest().authenticated())
	         .httpBasic(Customizer.withDefaults())
			 .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		 return http.build();
 
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
			throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
		
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

}
