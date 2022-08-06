package services;

import database.DataStorage;
import models.HashPassword;
import models.PreCreateUser;
import models.User;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class UserService {
	DataStorage dataStorage;

	public UserService() {
		this.dataStorage = DataStorage.getInstance();
	}

	public boolean createNewUser(String lastName, String firstName, String username, String password, String gender,
			String dateOfBirth) throws NoSuchAlgorithmException, NoSuchProviderException {
		PreCreateUser tempUser = new PreCreateUser(lastName, firstName, username, gender, dateOfBirth);
		String salt = HashPassword.getSalt();
		String hashedPassword = HashPassword.getHash(password, salt);
		if (getUserByUsername(tempUser.getUsername()) == null) {
			User user = new User(tempUser, hashedPassword);
			user.setSalt(salt);
			dataStorage.addUser(user);
			return true;
		} else
			return false;
	}

	public boolean login(String username, String password) {
		if (getUserByUsername(username) != null) {
			if (checkHashPassword(username, password)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public boolean checkHashPassword(String username, String password) {
		String salt = getUserByUsername(username).getSalt();
		String hashedPassword = HashPassword.getHash(password, salt);
		return getUserByUsername(username).getPassword().equals(hashedPassword);
	}

	public User getUserByUsername(String username) {
		User tempUser = null;
		for (User user : dataStorage.getUserList()) {
			if (user.getUsername().equals(username)) {
				tempUser = user;
			}
		}
		return tempUser;
	}

	public User getUserByUserId(String id) {
		User tempUser = null;
		for (User user : dataStorage.getUserList()) {
			if (user.getId().equalsIgnoreCase(id)) {
				tempUser = user;
			}
		}
		return tempUser;
	}

	public boolean setAlias(User user1, User user2, String alias) {
		user1.getAlias().put(user2, alias);
		return true;
	}
}
