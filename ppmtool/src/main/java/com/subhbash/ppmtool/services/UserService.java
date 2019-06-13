package com.subhbash.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.subhbash.ppmtool.domain.User;
import com.subhbash.ppmtool.exceptions.UsernameAlreadyExistsException;
import com.subhbash.ppmtool.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User saveUser(User newUser) {
		
		try {
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			
			// User Name must be unique(exception)
			newUser.setUsername(newUser.getUsername());
			//Make sure the passowrd and confirm password match
			
			//we donot perssist or show the confirm password
			newUser.setConfirmPassword("");
			return userRepository.save(newUser);	
		} catch (Exception e) {
			throw new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"' already exists");
		}
		
		
	}

}
