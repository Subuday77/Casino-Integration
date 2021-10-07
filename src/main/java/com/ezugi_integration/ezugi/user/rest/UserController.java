package com.ezugi_integration.ezugi.user.rest;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezugi_integration.ezugi.beans.Storage;
import com.ezugi_integration.ezugi.beans.User;
import com.ezugi_integration.ezugi.storage.DAO.StorageDAO;
import com.ezugi_integration.ezugi.user.DAO.UserDAO;
import static com.ezugi_integration.ezugi.beans.Constants.DATA_TYPES;

@RestController
@CrossOrigin(origins = "*")

@RequestMapping("/usercontrol")
public class UserController {
	@Autowired
	User user;
	@Autowired
	UserDAO userDAO;
	@Autowired
	Storage storage;
	@Autowired
	StorageDAO storageDAO;

	@PostMapping("/adduser")
	public ResponseEntity<?> addUser(@RequestBody User user) {
		Optional<User> existingUser = userDAO.findUserByUserName(user.getUserName());
		if (existingUser.isEmpty()) {

			user.setBalance(1000);
			user.setCurrency("USD");
			user.setLanguage("en");
			user.setVip("1");
			user.setBonusAmount(0);
			userDAO.addUser(user);
			return new ResponseEntity<String>("User with UID " + user.getUid() + " was added", HttpStatus.OK);
		}
		return new ResponseEntity<String>("User name alredy in use", HttpStatus.IM_USED);
	}

	@DeleteMapping("/deleteuser")
	public ResponseEntity<?> deleteUser(@RequestBody User user) {
		Optional<User> existingUser = userDAO.findUserById(user.getUid());
		if (existingUser.isPresent()) {
			userDAO.deleteUser(user);
			return new ResponseEntity<String>("User " + user.getUid() + " was deleted.", HttpStatus.OK);
		}
		return new ResponseEntity<String>("User " + user.getUid() + " was not found.", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/getallusers")
	public ResponseEntity<?> getAllUsers() {
		return new ResponseEntity<ArrayList<User>>((ArrayList<User>) userDAO.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping("/login")
	public ResponseEntity<?> login(@RequestParam(name = "user") String userName,
			@RequestParam(name = "password") String password) {
		Optional<User> existingUser = userDAO.findUserByUserName(userName);
		if (existingUser.isPresent()) {
			String passwordToCheck = existingUser.get().getPassword();
			if (passwordToCheck.equals(password)) {
				existingUser.get().setPassword("secured");
				return new ResponseEntity<User>(existingUser.get(), HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>("Invalid username or password", HttpStatus.FORBIDDEN);
	}

	@PutMapping("/logout")
	public ResponseEntity<?> logOut(@RequestParam(name = "uid") long uid) {
		Optional<User> existingUser = userDAO.findUserById(uid);
		if (existingUser.isPresent()) {
			storage = new Storage();
			storage.setData(userDAO.findUserById(uid).get().getSessionToken());
			storage.setDataType(DATA_TYPES.token);
			storage.setUid(String.valueOf(uid));
			storage.setTimestamp(System.currentTimeMillis());
			storageDAO.addStorageRecord(storage);
			userDAO.findUserById(uid).get().setSessionToken(null);
			userDAO.findUserById(uid).get().setSessionTokenTimestamp(0);
			userDAO.addUser(userDAO.findUserById(uid).get());
			return new ResponseEntity<String>("Logged off", HttpStatus.OK);
		}
		return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/token")
	public ResponseEntity<?> generateInitialToken(@RequestParam(name = "uid") long uid) {
		Optional<User> existingUser = userDAO.findUserById(uid);
		if (existingUser.isPresent()) {
			userDAO.findUserById(uid).get().setInitialToken(String.valueOf(UUID.randomUUID()));
			userDAO.findUserById(uid).get().setInitialTokenTimestamp(System.currentTimeMillis());
			userDAO.addUser(userDAO.findUserById(uid).get());
			userDAO.findUserById(uid).get().setPassword("**********");
			return new ResponseEntity<User>(userDAO.findUserById(uid).get(), HttpStatus.OK);
		}
		return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/balance")
	public ResponseEntity<?> changeBalance(@RequestParam(name = "uid") long uid,
			@RequestParam(name = "amount") double amount) {
		Optional<User> existingUser = userDAO.findUserById(uid);

		if (existingUser.isPresent()) {

			double balance = userDAO.findUserById(uid).get().getBalance();
			userDAO.findUserById(uid).get().setBalance(userDAO.findUserById(uid).get().getBalance() + amount);
			userDAO.addUser(userDAO.findUserById(uid).get());
			if (userDAO.findUserById(uid).get().getBalance() < 0) {
				userDAO.findUserById(uid).get().setBalance(balance);
				userDAO.addUser(userDAO.findUserById(uid).get());
				return new ResponseEntity<String>("Invalid amount. Balance can't be lower than 0",
						HttpStatus.NOT_ACCEPTABLE);
			}
			if (userDAO.findUserById(uid).get().getBalance() > 1000000) {
				userDAO.findUserById(uid).get().setBalance(balance);
				userDAO.addUser(userDAO.findUserById(uid).get());
				return new ResponseEntity<String>("Don't be so greedy.\nBetter come later and get some more.",
						HttpStatus.NOT_ACCEPTABLE);
			}
			return new ResponseEntity<Double>(userDAO.findUserById(uid).get().getBalance(), HttpStatus.OK);
		}
		return new ResponseEntity<String>("User " + uid + " was not found.", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/changeuname")

	public ResponseEntity<?> changeUName(@RequestParam(name = "uid") long uid,
			@RequestParam(name = "username") String userName) {
		Optional<User> existingUser = userDAO.findUserByUserName(userName);
		if (existingUser.isEmpty()) {
			userDAO.findUserById(uid).get().setUserName(userName);
			userDAO.addUser(userDAO.findUserById(uid).get());
			userDAO.findUserById(uid).get().setPassword("**********");
			return new ResponseEntity<User>(userDAO.findUserById(uid).get(), HttpStatus.OK);
		}
		return new ResponseEntity<String>("This username already in use. Please, choose another.", HttpStatus.IM_USED);
	}

}
