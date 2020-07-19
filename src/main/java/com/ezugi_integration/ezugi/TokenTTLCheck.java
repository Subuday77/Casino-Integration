package com.ezugi_integration.ezugi;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ezugi_integration.ezugi.beans.Storage;
import com.ezugi_integration.ezugi.beans.User;
import com.ezugi_integration.ezugi.storage.DAO.StorageDAO;
import com.ezugi_integration.ezugi.user.DAO.UserDAO;
import static com.ezugi_integration.ezugi.beans.Constants.DATA_TYPES;

@Component
@EnableAsync
public class TokenTTLCheck {

	@Autowired
	UserDAO userDAO;
	@Autowired
	StorageDAO storageDAO;
	@Autowired
	Storage storage;

	@Async
	@Scheduled(fixedRate = 1000 * 60)
	public void initialTokenTTL() throws InterruptedException {

		for (User user : userDAO.getAllUsers()) {
			if (user.getInitialTokenTimestamp() < System.currentTimeMillis() - (1000 * 60)) {
				user.setInitialToken(null);
				user.setInitialTokenTimestamp(0);
				userDAO.addUser(user);
			}
		}

	}

	@Async
	@Scheduled(fixedRate = 1000 * 60 * 10)
	public void sessionTokenTTL() throws InterruptedException {

		for (User user : userDAO.getAllUsers()) {

			if (user.getSessionTokenTimestamp() < System.currentTimeMillis() - (1000 * 60 * 30)
					&& user.getSessionTokenTimestamp() > 0) {
				storage.setData(user.getSessionToken());
				storage.setDataType(DATA_TYPES.token);
				storage.setTimestamp(System.currentTimeMillis());
				storage.setUid(String.valueOf(user.getUid()));
				storageDAO.addStorageRecord(storage);
				storage = new Storage();
				user.setSessionToken(null);
				user.setSessionTokenTimestamp(0);
				userDAO.addUser(user);
			}

		}

	}

	@Async
	@Scheduled(fixedRate = 1000 * 60 * 5)
	public void cleanStorage() throws InterruptedException {

		for (Storage storage : storageDAO.getAllStorageRecords()) {
			if (storage.getTimestamp() < System.currentTimeMillis() - (1000 * 60 * 10)) {
				storageDAO.deleteStorageRecord(storage);
			}
		}

	}

}