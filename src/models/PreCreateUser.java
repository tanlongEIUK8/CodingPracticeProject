package models;

public class PreCreateUser {
    private String lastName;
    private String firstName;
    private String username;
    private String gender;
    private String dateOfBirth;

    public PreCreateUser(String lastName, String firstName, String username, String gender, String dateOfBirth) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.username = username;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getUsername() {
        return username;
    }

    public String getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
}
