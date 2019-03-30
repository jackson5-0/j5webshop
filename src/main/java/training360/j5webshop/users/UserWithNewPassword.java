package training360.j5webshop.users;

public class UserWithNewPassword {
    private User user;
    private String newPassword;

    public UserWithNewPassword(User user, String newPassword) {
        this.user = user;
        this.newPassword = newPassword;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
