package com.derinplayground.tester.discovery.auth.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.derinplayground.tester.discovery.auth.entity.TestUser;
import com.derinplayground.tester.discovery.auth.entity.TestUserGatewayCredentials;
import com.derinplayground.tester.discovery.auth.entity.UserLoginCredentials;
import com.derinplayground.tester.discovery.auth.entity.dao.TestUserGatewayCredentialsRepository;
import com.derinplayground.tester.discovery.auth.entity.dao.UserLoginCredentialsRepository;
import com.derinplayground.tester.discovery.auth.gateway.request.AuthTesterRequest;
import com.derinplayground.tester.discovery.auth.gateway.request.LoginRequest;
import com.derinplayground.tester.discovery.auth.gateway.request.LogoutRequest;
import com.derinplayground.tester.discovery.auth.gateway.request.TestUserRequest;
import com.derinplayground.tester.discovery.auth.gateway.response.AuthTesterResponse;
import com.derinplayground.tester.discovery.auth.gateway.response.LoginResponse;
import com.derinplayground.tester.discovery.auth.gateway.response.LogoutResponse;
import com.derinplayground.tester.discovery.auth.gateway.response.TestUserResponse;
import com.derinplayground.tester.discovery.auth.security.service.impl.TokenGeneratorImpl;
import com.derinplayground.tester.discovery.auth.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	
	private static final Logger log = LogManager.getLogger(AuthServiceImpl.class);
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private TokenGeneratorImpl tokenService;
	
//	@Autowired
//	private UserRepository userRepository;
	
	@Autowired
	private UserLoginCredentialsRepository userLoginCredentialsRepository;
	
	@Autowired
	private TestUserGatewayCredentialsRepository testUserGatewayCredentialsRepository;
	
	public AuthServiceImpl() { }
	
	@Override
	public TestUserResponse createUser(TestUserRequest userToBeCreated) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		TestUser user = modelMapper.map(userToBeCreated, TestUser.class);
		user.setEncryptedPassword(bCryptPasswordEncoder.encode(userToBeCreated.getPassword()));
		log.info("Passwords String holds: Plain - {}, Encrypted - {}", userToBeCreated.getPassword(), 
				user.getEncryptedPassword());
		log.info("saving user Entity.");
//		userRepository.save(user);
		log.info("user entity holds: {} - {} - {} - {} - {}", user.getEmail(), user.getEncryptedPassword(), 
				user.getFirstName(), user.getLastName(), user.getUserId());
		log.info("user Entity saved!");
		UserLoginCredentials loginCredentials = new UserLoginCredentials();
		loginCredentials.setEncryptedPassword(user.getEncryptedPassword());
		loginCredentials.setUserId(user.getUserId());
		log.info("About to save loginCredentials");
		userLoginCredentialsRepository.save(loginCredentials);
		log.info("loginCredentials saved!");
		
		String webToken = tokenService.generateUserToken(user.getUserId());
		TestUserGatewayCredentials testUserGatewayCredentials = new TestUserGatewayCredentials();
		testUserGatewayCredentials.setUserId(user.getUserId());
		testUserGatewayCredentials.setWebToken(webToken);
		log.info("About to save testUserGatewayCredentials");
		testUserGatewayCredentialsRepository.save(testUserGatewayCredentials);
		log.info("testUserGatewayCredentials saved!");
		
		TestUserResponse response = new TestUserResponse();
		response.setUserId(userToBeCreated.getUserId());
		response.setEmail(userToBeCreated.getEmail());
		response.setFirstName(userToBeCreated.getFirstName());
		response.setLastName(userToBeCreated.getLastName());
		response.setEncryptedPassword(user.getEncryptedPassword());
		return response;
	}

	@Override
	public LoginResponse userLogin(LoginRequest request) {
		UserLoginCredentials loginCredentials = userLoginCredentialsRepository.findByUserId(request.getUserId());
		LoginResponse loginResponse = new LoginResponse();
		log.info("Getting user: {}", request.getUserId());
		log.info("returned loginCredentials is: {}", loginCredentials);
		if(loginCredentials != null) {
			if(bCryptPasswordEncoder.matches(request.getPassword(), loginCredentials.getEncryptedPassword())) {
				log.info("User Login Success!");
				String webToken = tokenService.generateUserToken(request.getUserId());
				TestUserGatewayCredentials testUserGatewayCredentials = new TestUserGatewayCredentials();
				testUserGatewayCredentials.setUserId(request.getUserId());
				testUserGatewayCredentials.setWebToken(webToken);
				log.info("About to save testUserGatewayCredentials inside userLogin");
				testUserGatewayCredentialsRepository.save(testUserGatewayCredentials);
				log.info("testUserGatewayCredentials saved! inside userLogin");
				
				loginResponse.setEncryptedPassword(loginCredentials.getEncryptedPassword());
				loginResponse.setUserId(request.getUserId());
				loginResponse.setWebToken(webToken);
			} else {
				log.fatal("PASSWORDS DID NOT MATCH, THROW ERROR!");
			}
		}
		return loginResponse;
	}

	@Override
	public LogoutResponse userLogout(LogoutRequest request) {
		LogoutResponse response = new LogoutResponse();
		TestUserGatewayCredentials testUserGatewayCredentials = testUserGatewayCredentialsRepository.findByUserId(request.getUserId());
		
		if(testUserGatewayCredentials == null) return response;
		
		testUserGatewayCredentialsRepository.delete(testUserGatewayCredentials);
		log.info("{} has successfully logged out!", request.getUserId());
		response.setUserId(request.getUserId());
		
		return response;
	}

	@Override
	public AuthTesterResponse validateLoggedInUser(AuthTesterRequest request) {
		AuthTesterResponse response = new AuthTesterResponse();
		log.info("User was able to successfully access this resource after login.");
		response.setUserId(request.getUserId());
		return response;
	}

}
