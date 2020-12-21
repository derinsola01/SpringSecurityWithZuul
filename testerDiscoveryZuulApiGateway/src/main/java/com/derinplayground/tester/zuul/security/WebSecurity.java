package com.derinplayground.tester.zuul.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableWebSecurity
//@EnableResourceServer
public class WebSecurity extends WebSecurityConfigurerAdapter { // ResourceServerConfigurerAdapter	WebSecurityConfigurerAdapter
	
	private static final Logger log = LogManager.getLogger(WebSecurity.class);
	private Environment environment;
	
	@Autowired
	public WebSecurity(Environment environment) {
		this.environment = environment;
	}
	
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		log.info("configure method called!");
		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();
		httpSecurity.authorizeRequests()
		.antMatchers(HttpMethod.POST, environment.getProperty("login.url.path")).permitAll()
		.antMatchers(HttpMethod.POST, environment.getProperty("registration.url.path")).permitAll()
		.antMatchers("/**").hasIpAddress(environment.getProperty("gateway.ip"))
		.anyRequest().authenticated()
		.and().addFilter(new AuthorizationFilter(authenticationManager(), environment));
		httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

}
