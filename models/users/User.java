package models;
import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private String id;
    private String fullName;
    private String email;
    private String password;
    private String language;
    private boolean loggedIn;
    public User(String id, String fullName, String email, String password, String language) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.language = language;
    }
    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword(){
        return password;
    }
    public String getLanguage() {
        return language;
    }
    public boolean login(String email, String password) {
        if (email == null || password == null) {
            return false;
        }
        loggedIn = this.email.equals(email) && this.password.equals(password);
        return loggedIn;
    }
    public void logout() {
        loggedIn = false;
    }
    public void changePassword(String newPassword) {
        if (newPassword != null && !newPassword.isBlank()) {
            this.password = newPassword;
        }
    }
    public boolean isLoggedIn() {
        return loggedIn;
    }
    @Override
    public String toString(){
        return "User{id='" + id + "', name='" + fullName + "', email='" + email + "'}";
    }
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }
    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}
