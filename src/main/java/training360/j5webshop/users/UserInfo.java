package training360.j5webshop.users;

import training360.j5webshop.authentication.UserRole;

public class UserInfo {

    private String userName;
    private UserRole userRole;

    public UserInfo() {
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getUserName() {
        return userName;
    }

    public UserRole getUserRole() {
        return userRole;
    }
}
