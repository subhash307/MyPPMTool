package com.subhbash.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.subhbash.ppmtool.domain.User;
import com.subhbash.ppmtool.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User saveUser(User newUser) {
		newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
		
		// User Name must be unique(exception)
		
		//Make sure the passowrd and confirm password match
		
		//we donot perssist or show the confirm password
		return userRepository.save(newUser);
	}

}
