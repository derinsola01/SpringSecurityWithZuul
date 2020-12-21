package com.derinplayground.tester.discovery.client.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.derinplayground.tester.discovery.client.service.ClientService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	private Environment environment;
	private ClientService clientService;
//	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@Autowired
	public WebSecurity(Environment environment, ClientService clientService) {
		this.environment = environment;
		this.clientService = clientService;
//		this.bcryptPasswordEncoder = bcryptPasswordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable();
		httpSecurity.authorizeRequests().antMatchers("/**").hasIpAddress(environment.getProperty("gateway.ip"))
		.and().addFilter(getAuthenticationFilter());
		httpSecurity.headers().frameOptions().disable();
	}

	private AuthenticationFilter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(clientService, environment, authenticationManager());
//		authenticationFilter.setAuthenticationManager(authenticationManager());
		authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
		return authenticationFilter;
	}
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
//		authBuilder.userDetailsService(clientService).passwordEncoder(bcryptPasswordEncoder);
//	}

}
