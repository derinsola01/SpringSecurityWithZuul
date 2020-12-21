package com.derinplayground.tester.discovery.client.users.security;

import javax.servlet.Filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.derinplayground.tester.discovery.client.users.entity.dao.TestUserGatewayCredentialsRepository;
import com.derinplayground.tester.discovery.client.users.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	private static final Logger log = LogManager.getLogger(WebSecurity.class);
	private Environment environment;
	private UserService userService;
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	private TestUserGatewayCredentialsRepository testUserGatewayCredentialsRepository;
			
	@Autowired
	public WebSecurity(Environment environment, UserService userService, 
			BCryptPasswordEncoder bcryptPasswordEncoder, TestUserGatewayCredentialsRepository testUserGatewayCredentialsRepository) {
		this.environment = environment;
		this.userService = userService;
		this.testUserGatewayCredentialsRepository = testUserGatewayCredentialsRepository;
		this.bcryptPasswordEncoder = bcryptPasswordEncoder;
		configureTokenGenerator(this.environment);
	}
	
	private void configureTokenGenerator(Environment environment) {
		TokenGenerator tokenGenerator = new TokenGenerator();
		tokenGenerator.setEnvironment(environment);
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		log.info("environment.getProperty(\"localized.login.path\") holds: {}", environment.getProperty("localized.login.path"));
		log.info("environment.getProperty(\"localized.registration.path\") holds: {}", environment.getProperty("localized.registration.path"));
		httpSecurity.csrf().disable();
		httpSecurity.authorizeRequests() // 
		.antMatchers(HttpMethod.POST, environment.getProperty("localized.login.path")).permitAll() // "/test/login"	environment.getProperty("localized.login.path")
		.antMatchers(HttpMethod.POST, environment.getProperty("localized.registration.path")).permitAll() //  "/test/register"	environment.getProperty("localized.registration.path")
		.antMatchers("/**").hasIpAddress(environment.getProperty("gateway.ip")) // "192.168.0.20" 	environment.getProperty("gateway.ip")
		.and().addFilter(getAuthenticationFilter());
		httpSecurity.headers().frameOptions().disable();
	}

	private AuthenticationFilter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, environment, 
									authenticationManager(), testUserGatewayCredentialsRepository);
//		authenticationFilter.setAuthenticationManager(authenticationManager());
		authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));	// "/tester-discovery-client-users/test/login"	environment.getProperty("login.url.path")
		return authenticationFilter;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
		authBuilder.userDetailsService(userService).passwordEncoder(bcryptPasswordEncoder);
	}	

}