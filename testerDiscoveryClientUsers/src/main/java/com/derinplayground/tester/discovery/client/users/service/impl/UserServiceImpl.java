package com.derinplayground.tester.discovery.client.users.service.impl;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.derinplayground.tester.discovery.client.users.entity.TestUser;
import com.derinplayground.tester.discovery.client.users.entity.TestUserGatewayCredentials;
import com.derinplayground.tester.discovery.client.users.entity.UserLoginCredentials;
import com.derinplayground.tester.discovery.client.users.entity.dao.TestUserGatewayCredentialsRepository;
import com.derinplayground.tester.discovery.client.users.entity.dao.UserLoginCredentialsRepository;
import com.derinplayground.tester.discovery.client.users.entity.dao.UserRepository;
import com.derinplayground.tester.discovery.client.users.gateway.request.LoginRequest;
import com.derinplayground.tester.discovery.client.users.gateway.request.LogoutRequest;
import com.derinplayground.tester.discovery.client.users.gateway.request.TestUserRequest;
import com.derinplayground.tester.discovery.client.users.gateway.response.LoginResponse;
import com.derinplayground.tester.discovery.client.users.gateway.response.LogoutResponse;
import com.derinplayground.tester.discovery.client.users.gateway.response.TestUserResponse;
import com.derinplayground.tester.discovery.client.users.security.TokenGenerator;
import com.derinplayground.tester.discovery.client.users.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger log = LogManager.getLogger(UserServiceImpl.class);
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserLoginCredentialsRepository userLoginCredentialsRepository;
	
	@Autowired
	private TestUserGatewayCredentialsRepository testUserGatewayCredentialsRepository;
	
//	@Autowired
	public UserServiceImpl() {
//		this.userRepository = userRepository;
//		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public TestUserResponse createUser(TestUserRequest userToBeCreated) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		TestUser user = modelMapper.map(userToBeCreated, TestUser.class);
		user.setEncryptedPassword(bCryptPasswordEncoder.encode(userToBeCreated.getPassword()));
		log.info("Passwords String holds: Plain - {}, Encrypted - {}", userToBeCreated.getPassword(), 
				user.getEncryptedPassword());
		log.info("saving user Entity.");
		userRepository.save(user);
		log.info("user entity holds: {} - {} - {} - {} - {}", user.getEmail(), user.getEncryptedPassword(), 
				user.getFirstName(), user.getLastName(), user.getUserId());
		log.info("user Entity saved!");
		UserLoginCredentials loginCredentials = new UserLoginCredentials();
		loginCredentials.setEncryptedPassword(user.getEncryptedPassword());
		loginCredentials.setUserId(user.getUserId());
		log.info("About to save loginCredentials");
		userLoginCredentialsRepository.save(loginCredentials);
		log.info("loginCredentials saved!");
		
		TokenGenerator tokenGenerator = new TokenGenerator();
		String webToken = tokenGenerator.generateUserToken(user.getUserId());
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
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		TestUser testUser = userRepository.findByUserId(username);
		if (testUser == null) throw new UsernameNotFoundException(username);
		return new User(testUser.getUserId(), testUser.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
	}

	@Override
	public TestUserResponse getUserDetailsByUserId(String userId) {
		TestUser testUser = userRepository.findByUserId(userId);
		if (testUser == null) throw new UsernameNotFoundException(userId);
		return new ModelMapper().map(testUser, TestUserResponse.class);
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
				//TODO leaving this here to see if I may re-purpose later.
//				TestUser testUser = userRepository.findByUserId(request.getUserId());
				
				TokenGenerator tokenGenerator = new TokenGenerator();
				String webToken = tokenGenerator.generateUserToken(request.getUserId());
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
		LogoutResponse response = null;
		TestUserGatewayCredentials testUserGatewayCredentials = testUserGatewayCredentialsRepository.findByUserId(request.getUserId());
		
		if(testUserGatewayCredentials == null) return response;
		
		testUserGatewayCredentialsRepository.delete(testUserGatewayCredentials);
		log.info("{} has successfully logged out!", request.getUserId());
		response.setUserId(request.getUserId());
		
		return response;
	}

}