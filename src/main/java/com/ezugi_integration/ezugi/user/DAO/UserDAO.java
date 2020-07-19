package com.ezugi_integration.ezugi.user.DAO;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezugi_integration.ezugi.beans.User;
import com.ezugi_integration.ezugi.user.repo.UserRepo;
import java.util.Optional;

@Repository
public class UserDAO {

	@Autowired
	UserRepo userRepo;

	public void addUser(User user) {
		userRepo.save(user);
	}

	public void deleteUser(User user) {
		userRepo.delete(user);
	}

	public Collection<User> getAllUsers() {
		return userRepo.findAll();

	}

	public Optional<User> findUserById(long uid) {
		return userRepo.findById(uid);
	}
	
	public Optional<User> findUserByUserName (String userName){
		return userRepo.findByUserName(userName);
	}
	
	public Optional<User> findUserByInitialToken (String initialToken){
		
	
	return userRepo.findByInitialToken(initialToken);
}
	public Optional<User> findUserBySessionToken (String sessionToken){
		
		
		return userRepo.findBySessionToken(sessionToken);
	}
}
