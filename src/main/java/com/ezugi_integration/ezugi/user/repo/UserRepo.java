package com.ezugi_integration.ezugi.user.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezugi_integration.ezugi.beans.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByUserNameIgnoreCase(String userName);

	Optional<User> findByInitialToken(String initialToken);
	
	Optional<User> findBySessionToken(String sessionToken);
}
