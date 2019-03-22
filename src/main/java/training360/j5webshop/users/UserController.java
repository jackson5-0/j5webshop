package training360.j5webshop.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import training360.j5webshop.authentication.UserRole;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user")
    public UserInfo getUser(Authentication auth) {
        UserInfo userInfo = new UserInfo();
        if (auth != null) {
            String userName = auth.getName();
            String role = auth.getAuthorities().iterator().next().getAuthority();
            Long id = userService.findBasketId(userName);
            userInfo.setUserName(userName);
            userInfo.setUserRole(UserRole.valueOf(role));
            userInfo.setBasketId(id);
        }

        return userInfo;
    }

}
