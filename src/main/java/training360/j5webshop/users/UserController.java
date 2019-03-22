package training360.j5webshop.users;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import training360.j5webshop.authentication.UserRole;

@RestController
public class UserController {

    @RequestMapping("/user")
    public UserInfo getUser(Authentication auth) {
        UserInfo userInfo = new UserInfo();
        if (auth != null) {
            String userName = auth.getName();
            String role = auth.getAuthorities().iterator().next().getAuthority();
            userInfo.setUserName(userName);
            userInfo.setUserRole(UserRole.valueOf(role));
        }
        return userInfo;
    }

}
