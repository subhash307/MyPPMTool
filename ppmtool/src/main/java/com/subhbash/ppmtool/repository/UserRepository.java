package com.subhbash.ppmtool.repository;

import org.springframework.data.repository.CrudRepository;

import com.subhbash.ppmtool.domain.User;

public interface UserRepository extends CrudRepository<User, Long>{

	User findByUsername(String username);
	User getById(Long id);
}
