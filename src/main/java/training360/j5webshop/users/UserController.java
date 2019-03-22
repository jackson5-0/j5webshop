package training360.j5webshop.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import training360.j5webshop.authentication.UserRole;
import training360.j5webshop.validation.ResponseStatus;
import training360.j5webshop.validation.ValidationStatus;
import training360.j5webshop.validation.Validator;

import javax.validation.Validation;
import java.util.List;

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

    @PostMapping("/users")
    public ResponseStatus addUser(@RequestBody User user){
        Validator validator = new Validator(user, listUsers());
        if (validator.getResponseStatus().getStatus() == ValidationStatus.SUCCESS) {
            userService.addUser(user);
            validator.getResponseStatus().addMessage("A regisztráció sikeres volt, bejelentkezhet oldalunkra!");
        }
        return validator.getResponseStatus();
    }


    public List<User> listUsers() {
        return userService.listUsers();
    }
}
