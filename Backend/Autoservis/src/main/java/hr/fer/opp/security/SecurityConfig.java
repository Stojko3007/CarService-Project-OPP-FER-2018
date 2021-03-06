package hr.fer.opp.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import hr.fer.opp.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserService userService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}		
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().configurationSource(configurationSource())
				.and().csrf().disable()
//			    .authorizeRequests()
//			      .antMatchers("/serviceVehicle/**").hasRole("ADMIN")
//			      .antMatchers("/serviceVehicle/free/**").hasRole("MECH")
//			      .antMatchers("/userVehicle/**").hasAnyRole("ADMIN", "USER")
//			      .antMatchers(HttpMethod.GET, "/user").hasRole("ADMIN")
//			      // ne znam hoce li ovo biti problem kasnije...
//			      .antMatchers(HttpMethod.GET, "/user/loggedIn").hasRole("USER")
//			      .antMatchers(HttpMethod.POST, "/user").hasAnyRole("ADMIN", "USER", "MECH")
//			      .antMatchers(HttpMethod.DELETE, "/user/**").hasAnyRole("ADMIN", "USER", "MECH")
			      	.logout().disable();
						
		// .and().httpBasic();
		// ako se odkomentira ovo iznad i iduca metoda onda se
		// preko postmana moze radit basic authorization, ali onda ne radi samo s loginom...
		// uopce se ne gleda tko je ulogiran nego samo trenutna autorizacija
		// pretpostavljam da je ovako lakše s angularom radi
	}
	
//	@Overridefalse
//	public void configure(WebSecurity web) throws Exception {
//	   web.ignoring().antMatchers("/login");
//	}
	
	@Bean
	public CorsConfigurationSource configurationSource() {
		  UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		  CorsConfiguration config = new CorsConfiguration();
		  config.addAllowedOrigin("*");
		  config.setAllowCredentials(true);
		  config.addAllowedHeader("*");
		  config.addAllowedMethod(HttpMethod.GET);
		  config.addAllowedMethod(HttpMethod.POST);
		  config.addAllowedMethod(HttpMethod.PUT);
		  config.addAllowedMethod(HttpMethod.DELETE);
		  config.addAllowedMethod(HttpMethod.OPTIONS);
		  source.registerCorsConfiguration("/logout", config);
		  return source;
		}
	
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManager();
	}
}
