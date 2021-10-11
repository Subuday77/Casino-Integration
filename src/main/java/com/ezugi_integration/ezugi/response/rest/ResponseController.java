package com.ezugi_integration.ezugi.response.rest;

import static com.ezugi_integration.ezugi.beans.Constants.HASHKEY;
import static com.ezugi_integration.ezugi.beans.Constants.OPERATORID;

import java.text.DecimalFormat;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezugi_integration.ezugi.beans.AuthResponse;
import com.ezugi_integration.ezugi.beans.Constants.DATA_TYPES;
import com.ezugi_integration.ezugi.beans.OtherResponse;
import com.ezugi_integration.ezugi.beans.Storage;
import com.ezugi_integration.ezugi.beans.User;
import com.ezugi_integration.ezugi.storage.DAO.StorageDAO;
import com.ezugi_integration.ezugi.user.DAO.UserDAO;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/ezugi")
public class ResponseController {

	@Autowired

	HttpServletRequest servletRequest;
	@Autowired
	AuthResponse authResponse;
	@Autowired

	OtherResponse response;
	@Autowired
	UserDAO userDAO;
	@Autowired
	User user;
	@Autowired
	Storage storage;
	@Autowired
	StorageDAO storageDAO;

	public double balanceToRecord = 0;
	
	

	@PostMapping("/auth")
	public synchronized ResponseEntity<?> authResponse(@RequestBody String request) throws JsonProcessingException {
		if (hashCheck(servletRequest.getHeader("hash"), request)) {
			JSONObject authRequestJson = new JSONObject(request);
			if (authResponse.getOperatorId() != authRequestJson.getLong("operatorId")) {
				authResponse.setErrorCode(1);
				authResponse.setErrorDescription("Wrong operatorId");
				authResponse.setTimestamp(System.currentTimeMillis());
				return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
			}
			Optional<User> existingUser = userDAO.findUserByInitialToken(authRequestJson.getString("token"));
			if (existingUser.isPresent()) {
				String token = String.valueOf(UUID.randomUUID());
				authResponse.setUid((int) existingUser.get().getUid());
				authResponse.setNickName(existingUser.get().getUserName());
				authResponse.setToken(token);
				userDAO.findUserById(existingUser.get().getUid()).get().setSessionToken(token);
				userDAO.findUserById(existingUser.get().getUid()).get()
						.setSessionTokenTimestamp(System.currentTimeMillis());
				authResponse.setPlayerTokenAtLaunch(
						userDAO.findUserById(existingUser.get().getUid()).get().getInitialToken());
				userDAO.findUserById(existingUser.get().getUid()).get().setInitialToken(null);
				userDAO.findUserById(existingUser.get().getUid()).get().setInitialTokenTimestamp(0);
				authResponse.setBalance(existingUser.get().getBalance());
				authResponse.setCurrency(existingUser.get().getCurrency());
				authResponse.setVip(existingUser.get().getVip());
				authResponse.setErrorCode(0);
				authResponse.setErrorDescription("OK");
				userDAO.addUser(userDAO.findUserById(existingUser.get().getUid()).get());
				JSONObject authResp = new JSONObject(authResponse);
				authResp.put("timestamp", System.currentTimeMillis());

				String auResp = authResp.toString();
				System.out.println(auResp);
				return new ResponseEntity<String>(auResp, HttpStatus.OK);
			}
			authResponse = new AuthResponse();
			authResponse.setErrorCode(6);
			authResponse.setErrorDescription("Token not found");
			authResponse.setTimestamp(System.currentTimeMillis());
			return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
		}
		authResponse = new AuthResponse();
		authResponse.setErrorCode(1);
		authResponse.setErrorDescription("Invalid hash");
		authResponse.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
	}

	@PostMapping("/debit")
	public synchronized ResponseEntity<?> debitResponse(@RequestBody String request) {
		String expectedHash = servletRequest.getHeader("hash");
		String call = "debit";
		response = validations(request, expectedHash, call);
		response.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<OtherResponse>(response, HttpStatus.OK);

	}

	@PostMapping("/rollback")
	public synchronized ResponseEntity<?> rollbackResponse(@RequestBody String request) {
		String expectedHash = servletRequest.getHeader("hash");
		String call = "rollback";
		response = validations(request, expectedHash, call);
		response.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<OtherResponse>(response, HttpStatus.OK);

	}

	@PostMapping("/credit")
	public synchronized ResponseEntity<?> creditResponse(@RequestBody String request) {
		String expectedHash = servletRequest.getHeader("hash");
		String call = "credit";
		response = validations(request, expectedHash, call);
		response.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<OtherResponse>(response, HttpStatus.OK);

	}

	private static boolean hashCheck(String hash, String request) {

		try {
			String secret = HASHKEY;
			String message = request;

			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
			sha256_HMAC.init(secret_key);

			String countedHash = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes()));

			return countedHash.equals(hash);

		} catch (Exception e) {
			System.out.println("Generate Hash Error");
		}

		return false;
	}

	private OtherResponse validations(String request, String expectedHash, String call) {
		JSONObject requestJson = new JSONObject(request);
		response = new OtherResponse();
		response.setOperatorId(requestJson.getLong("operatorId"));
		response.setUid(requestJson.getString("uid"));
		response.setToken(requestJson.getString("token"));
		response.setTransactionId(requestJson.getString("transactionId"));
		response.setRoundId(requestJson.getLong("roundId"));
		response.setCurrency(requestJson.getString("currency"));
		response.setGameId(requestJson.getInt("gameId"));
		response.setTableId(requestJson.getInt("tableId"));
		response.setBonusAmount(0);
		// Common validations
		if (!hashCheck(expectedHash, request)) {
			response.setErrorCode(1);
			response.setErrorDescription("Invalid hash");
			response.setBalance(0);
			return response;
		}
		if (response.getOperatorId() != OPERATORID) {
			response.setErrorCode(1);
			response.setErrorDescription("Invalid operator ID");
			response.setBalance(0);
			return response;

		}
		Optional<User> user = userDAO.findUserBySessionToken(response.getToken());
		if (user.isEmpty() && (!call.equals("credit"))) {
			response.setErrorCode(6);
			response.setErrorDescription("Token not found");
			response.setBalance(0);
			return response;
		}
		user = userDAO.findUserById(Long.parseLong((response.getUid())));
		if (user.isEmpty() || (!user.equals(userDAO.findUserBySessionToken(response.getToken())))) {

			response.setErrorCode(7);
			response.setErrorDescription("User not found");
			response.setBalance(0);
			return response;
		}
		// Debit validations
		if (call.equals("debit")) {
			if (requestJson.getDouble("debitAmount") < 0) {
				response.setErrorCode(1);
				response.setErrorDescription("Negative amount");
				response.setBalance(0);
				return response;
			}
			if (requestJson.getDouble("debitAmount") > user.get().getBalance()) {
				response.setErrorCode(3);
				response.setErrorDescription("Insuficient funds");
				response.setBalance(user.get().getBalance());
				return response;
			}
			Optional<Storage> txnToCompare = storageDAO.findStorageRecordByData(requestJson.getString("transactionId"));
			if (txnToCompare.isPresent()) {
				if (txnToCompare.get().getDataType().equals(DATA_TYPES.debit_transaction_id)) {
					response.setErrorCode(0);
					response.setErrorDescription("Transaction already processed");
					response.setBalance(user.get().getBalance());
					return response;
				}
				if (txnToCompare.get().getDataType().equals(DATA_TYPES.rollback_transaction_id)) {
					response.setErrorCode(1);
					response.setErrorDescription("Debit after rollback");
					response.setBalance(user.get().getBalance());
					return response;
				}
			}
			response.setErrorCode(0);
			response.setErrorDescription("OK");
			balanceToRecord = Double
					.parseDouble(formatMyDouble(user.get().getBalance() - requestJson.getDouble("debitAmount")));
			response.setBalance(balanceToRecord);
			userDAO.findUserById(requestJson.getLong("uid")).get().setBalance(balanceToRecord);
			userDAO.findUserById(requestJson.getLong("uid")).get().setSessionTokenTimestamp(System.currentTimeMillis());
			userDAO.addUser(userDAO.findUserById(requestJson.getLong("uid")).get());
			storage.setData(requestJson.getString("transactionId"));
			storage.setDataType(DATA_TYPES.debit_transaction_id);
			storage.setAmount(requestJson.getDouble("debitAmount"));
			storage.setUid(requestJson.getString("uid"));
			storage.setTimestamp(System.currentTimeMillis());
			storageDAO.addStorageRecord(storage);
			storage = new Storage();
			return response;
		}

		// Rollback validations
		if (call.equals("rollback")) {
			if (requestJson.getDouble("rollbackAmount") < 0) {
				response.setErrorCode(1);
				response.setErrorDescription("Negative amount");
				response.setBalance(user.get().getBalance());
				return response;
			}

			Optional<Storage> txnToCompare = storageDAO.findStorageRecordByData(requestJson.getString("transactionId"));
			if (txnToCompare.isPresent()) {
				if (txnToCompare.get().getDataType().equals(DATA_TYPES.rollback_transaction_id)) {
					response.setErrorCode(0);
					response.setErrorDescription("Transaction already processed");
					response.setBalance(user.get().getBalance());
					return response;
				}
				if (txnToCompare.get().getDataType().equals(DATA_TYPES.debit_transaction_id)) {
					if (txnToCompare.get().getAmount() != requestJson.getDouble("rollbackAmount")) {
						response.setErrorCode(1);
						response.setErrorDescription("Invalid amount");
						response.setBalance(user.get().getBalance());
						return response;
					}
					response.setErrorCode(0);
					response.setErrorDescription("OK");
					balanceToRecord = Double.parseDouble(
							formatMyDouble(user.get().getBalance() + requestJson.getDouble("rollbackAmount")));
					response.setBalance(balanceToRecord);
					userDAO.findUserById(requestJson.getLong("uid")).get().setBalance(balanceToRecord);
					userDAO.findUserById(requestJson.getLong("uid")).get()
							.setSessionTokenTimestamp(System.currentTimeMillis());
					userDAO.addUser(userDAO.findUserById(requestJson.getLong("uid")).get());
					storage = storageDAO.findStorageRecordByData(requestJson.getString("transactionId")).get();
					storage.setDataType(DATA_TYPES.rollback_transaction_id);
					storage.setAmount(requestJson.getDouble("rollbackAmount"));
					storage.setUid(requestJson.getString("uid"));
					storage.setTimestamp(System.currentTimeMillis());
					storageDAO.addStorageRecord(storage);
					storage = new Storage();
					return response;
				}
			}
			response.setErrorCode(9);
			response.setErrorDescription("Transaction not found");
			response.setBalance(user.get().getBalance());
			storage.setData(requestJson.getString("transactionId"));
			storage.setDataType(DATA_TYPES.rollback_transaction_id);
			storage.setAmount(requestJson.getDouble("rollbackAmount"));
			storage.setUid(requestJson.getString("uid"));
			storage.setTimestamp(System.currentTimeMillis());
			storageDAO.addStorageRecord(storage);
			storage = new Storage();
			return response;
		}
		// Credit validations
		if (call.equals("credit")) {
			if (user.isEmpty()) {
				Optional<Storage> storage = storageDAO.findStorageRecordByData(requestJson.getString("token"));
				if (storage.isEmpty()) {
					response.setErrorCode(6);
					response.setErrorDescription("Token not found");
					response.setBalance(0);
					return response;
				}
			}
			if (requestJson.getDouble("creditAmount") < 0) {
				response.setErrorCode(1);
				response.setErrorDescription("Negative amount");
				response.setBalance(user.get().getBalance());
				return response;
			}

			Optional<Storage> txnToCompare = storageDAO.findStorageRecordByData(requestJson.getString("transactionId"));
			if (txnToCompare.isPresent()) {
				if (txnToCompare.get().getDataType().equals(DATA_TYPES.credit_transaction_id)) {
					response.setErrorCode(0);
					response.setErrorDescription("Transaction already processed");
					response.setBalance(user.get().getBalance());
					return response;
				}
			}
			txnToCompare = storageDAO.findStorageRecordByData(requestJson.optString("debitTransactionId"));
			if (txnToCompare.isEmpty()) {
				response.setErrorCode(9);
				response.setErrorDescription("Corresponding debit transaction not found");
				response.setBalance(user.get().getBalance());
				return response;
			}
			if (txnToCompare.get().isCredited()) {
				response.setErrorCode(1);
				response.setErrorDescription("Corresponding debit transaction already processed");
				response.setBalance(user.get().getBalance());
				return response;
			}
			response.setErrorCode(0);
			response.setErrorDescription("OK");
			balanceToRecord = Double
					.parseDouble(formatMyDouble(user.get().getBalance() + requestJson.getDouble("creditAmount")));
			response.setBalance(balanceToRecord);
			userDAO.findUserById(requestJson.getLong("uid")).get().setBalance(balanceToRecord);
			userDAO.findUserById(requestJson.getLong("uid")).get().setSessionTokenTimestamp(System.currentTimeMillis());
			userDAO.addUser(userDAO.findUserById(requestJson.getLong("uid")).get());
			storage.setData(requestJson.getString("transactionId"));
			storage.setDataType(DATA_TYPES.credit_transaction_id);
			storage.setAmount(requestJson.getDouble("creditAmount"));
			storage.setUid(requestJson.getString("uid"));
			storage.setTimestamp(System.currentTimeMillis());
			storageDAO.findStorageRecordByData(requestJson.getString("debitTransactionId")).get().setCredited(true);
			storageDAO.addStorageRecord(storage);
			storage = new Storage();
			return response;
		}

		return null;

	}

	public static String formatMyDouble(double num) {
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		return decimalFormat.format(num);
	}

}
