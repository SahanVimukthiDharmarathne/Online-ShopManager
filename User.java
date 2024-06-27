//Create the user class for the purpose of the login page.
class User {
    // Initializing class attributes.
    private String UserName;
    private String PassWord;

    // Making the constructor for the User class.
    public User(String username, String password) {
        this.UserName = username;
        this.PassWord = password;
    }

    // Constructing Getters and setters for the User class.

    public void setUsername(String username) {
        this.UserName = username;
    }

    public void setPassword(String password) {
        this.PassWord = password;
    }

    public String getUsername() {
        return UserName;
    }

    public String getPassword() {
        return PassWord;
    }
}