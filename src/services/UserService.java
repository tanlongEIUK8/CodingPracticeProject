package services;

import database.DataStorage;
import models.HashPassword;
import models.User;

public class UserService {
	DataStorage dataStorage;

	public UserService() {
		this.dataStorage = DataStorage.getInstance();
	}

	public boolean createNewUser(String lastName, String firstName, String username, String password, String gender,
			String dateOfBirth) {
		String hashedPassword = HashPassword.getHash(password);
		if (getUserByUsername(username) == null) {
			User user = new User(lastName, firstName, username, hashedPassword, gender, dateOfBirth);
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
		String hashedPassword = HashPassword.getHash(password);
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

	public User getUserByUserId(int id) {
		User tempUser = null;
		for (User user : dataStorage.getUserList()) {
			if (user.getId() == id) {
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
