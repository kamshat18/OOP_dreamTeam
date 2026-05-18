package models;

public class AuthenticationService {
    private User currentUser;

    public boolean login(User user, String email, String password) {
        if (user == null || email == null || password == null) {
            return false;
        }
        boolean ok = email.equals(user.getEmail()) && password.equals(user.getPassword());
        if (ok) {
            currentUser = user;
        }
        return ok;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
