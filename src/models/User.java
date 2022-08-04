package models;

import java.util.HashMap;
import java.util.Map;

public class User {
	private static int count = 0;
	private String id;
	private String lastName;
	private String firstName;
	private String username;
	private String password;
	private String salt;
	private String gender;
	private String dateOfBirth;
	private Map<User, String> alias;

	public User(String lastName, String firstName, String username, String password, String gender,
			String dateOfBirth) {
		id = "user"+count++;
		this.lastName = lastName;
		this.firstName = firstName;
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		alias = new HashMap<User, String>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getSalt() {
		return salt;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Map<User, String> getAlias() {
		return alias;
	}

	public void setAlias(Map<User, String> alias) {
		this.alias = alias;
	}
}
