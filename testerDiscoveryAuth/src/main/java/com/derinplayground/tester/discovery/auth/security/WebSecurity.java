package com.derinplayground.tester.discovery.auth.security;

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

import com.derinplayground.tester.discovery.auth.entity.dao.TestUserGatewayCredentialsRepository;
import com.derinplayground.tester.discovery.auth.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	private static final Logger log = LogManager.getLogger(WebSecurity.class);
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@Autowired
	private TestUserGatewayCredentialsRepository testUserGatewayCredentialsRepository;
	
//	public WebSecurity(Environment environment, UserService userService, 
//			BCryptPasswordEncoder bcryptPasswordEncoder, TestUserGatewayCredentialsRepository testUserGatewayCredentialsRepository) {
	public WebSecurity() {
//		this.environment = environment;
//		this.userService = userService;
//		this.testUserGatewayCredentialsRepository = testUserGatewayCredentialsRepository;
//		this.bcryptPasswordEncoder = bcryptPasswordEncoder;
//		configureTokenGenerator(this.environment);
	}
	
//	private void configureTokenGenerator(Environment environment) {
//		new TokenGenerator(environment); //TokenGenerator tokenGenerator = 
////		tokenGenerator.setEnvironment(environment);
//	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		log.info("environment.getProperty(\"login.url.path\") holds: {}", environment.getProperty("login.url.path"));
		log.info("environment.getProperty(\"logout.url.path\") holds: {}", environment.getProperty("logout.url.path"));
		log.info("environment.getProperty(\"registration.url.path\") holds: {}", environment.getProperty("registration.url.path"));
		log.info("environment.getProperty(\"token.secret\") holds: {}", environment.getProperty("token.secret"));
		httpSecurity.csrf().disable();
		httpSecurity.authorizeRequests()
		.antMatchers(HttpMethod.POST, environment.getProperty("localized.login.path")).permitAll()
		.antMatchers(HttpMethod.POST, environment.getProperty("localized.registration.path")).permitAll()
		.antMatchers("/**").hasIpAddress(environment.getProperty("gateway.ip"))
		.and().addFilter(new AuthenticationFilter(authenticationManager(), environment));
		httpSecurity.headers().frameOptions().disable();
	}

//	private AuthenticationFilter getAuthenticationFilter() throws Exception {
//		AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, environment, 
//									authenticationManager(), testUserGatewayCredentialsRepository);
////		authenticationFilter.setAuthenticationManager(authenticationManager());
//		authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
//		return authenticationFilter;
//	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
		authBuilder.userDetailsService(userService).passwordEncoder(bcryptPasswordEncoder);
	}	

}