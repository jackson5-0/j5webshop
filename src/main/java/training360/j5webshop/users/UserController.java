package training360.j5webshop.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import training360.j5webshop.authentication.UserRole;
import training360.j5webshop.validation.ResponseStatus;
import training360.j5webshop.validation.ValidationStatus;
import training360.j5webshop.validation.Validator;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/admin/users")
    public List<User> listUsers() {
        return userService.listUsers();
    }

    @DeleteMapping("/admin/users")
    public ResponseStatus deleteUserById(@RequestParam long id) {
        ResponseStatus status = new ResponseStatus();
        boolean userExist = userService.listUserIds().contains(id);
        if (userExist && userService.findUserById(id).getRole() == Role.ROLE_ADMIN) {
            List<User> users = userService.listUsers();
            int numOfAdmins = 0;
            for (User user : users) {
                if (user.getRole() == Role.ROLE_ADMIN) {
                    numOfAdmins += 1;
                }
            }
            if (numOfAdmins < 2) {
                status.setStatus(ValidationStatus.FAIL);
                status.addMessage("Nem törölhető az összes admin!");
                return status;
            }
        }
        if (userExist) {
            userService.deleteUserById(id);
            status.setStatus(ValidationStatus.SUCCESS);
            status.addMessage("A felhasználó törlése sikeres volt!");
        }  else {
            status.setStatus(ValidationStatus.FAIL);
            status.addMessage("Ilyen id-vel felhasználó nem található!");
        }
        return status;
    }

    @PutMapping("/admin/users")
    public ResponseStatus updateUserDatasByAdmin(@RequestParam long id, @RequestBody User user) {
        ResponseStatus status = new ResponseStatus();
        if (userService.listUserIds().contains(id)) {
            Validator validator = new Validator(user, userService.listUsers());
            if (validator.getResponseStatus().getStatus() == ValidationStatus.SUCCESS) {
                userService.updateUserDatasByAdmin(id, user);
                status.addMessage("Sikeres módosítás!");
                status.setStatus(ValidationStatus.SUCCESS);
            } else {
                status.setStatus(ValidationStatus.FAIL);
                status.addMessage(validator.getResponseStatus().getMessages().get(0));
            }
        }  else {
            status.setStatus(ValidationStatus.FAIL);
            status.addMessage("Ilyen id-vel felhasználó nem található!");
        }
        return status;
    }

    @GetMapping("/userprofile")
    public User findUserByUserName(Authentication authentication) {
        return userService.findUserByUserName(authentication.getName());
    }

    @PostMapping("/userprofile")
    public ResponseStatus updateUserDatasByUser(@RequestParam long id, @RequestBody User user) {
        ResponseStatus status = new ResponseStatus();
        if (userService.listUserIds().contains(id)) {
            Validator validator = new Validator(id, user, userService.listUsers());
            if (validator.getResponseStatus().getStatus() == ValidationStatus.FAIL) {
                status.addMessage(validator.getResponseStatus().getMessages().get(0));
                status.setStatus(ValidationStatus.FAIL);
            } else if (validator.getResponseStatus().getStatus() == ValidationStatus.SUCCESS && userService.updateUserDatasByUser(id, user)) {
                status.addMessage("Sikeres módosítás!");
                status.setStatus(ValidationStatus.SUCCESS);
            } else if (validator.getResponseStatus().getStatus() == ValidationStatus.SUCCESS && !userService.updateUserDatasByUser(id, user)) {
                status.addMessage("A beírt jelszó nem megfelelő!");
                status.setStatus(ValidationStatus.FAIL);
            }
        }  else {
            status.setStatus(ValidationStatus.FAIL);
            status.addMessage("Ilyen id-vel felhasználó nem található!");
        }
        return status;
    }

    @PutMapping("/userprofile")
    public ResponseStatus changePassword(@RequestParam long id, @RequestBody UserWithNewPassword userWithNewPassword) {
        ResponseStatus status = new ResponseStatus();
        try {
            if (!userService.givenPasswordIsCorrect(id, userWithNewPassword.getUser())) {
                status.addMessage("A régi jelszó nem megfelelő!");
                status.setStatus(ValidationStatus.FAIL);
            } else {
                Validator validator = new Validator(userWithNewPassword);
                if (validator.getResponseStatus().getMessages().size() > 0) {
                    status.addMessage(validator.getResponseStatus().getMessages().get(0));
                    status.setStatus(ValidationStatus.FAIL);
                } else {
                    userService.changePassword(userWithNewPassword.getUser().getId(), new User(userWithNewPassword.getUser().getFirstName(),
                            userWithNewPassword.getUser().getLastName(), userWithNewPassword.getUser().getUserName(), userWithNewPassword.getNewPassword()));
                    status.addMessage("A jelszó sikeresen módosult!");
                    status.setStatus(ValidationStatus.SUCCESS);
                }
            }
        } catch (EmptyResultDataAccessException erdae) {
            status.addMessage("A kért felhasználó nem található!");
            status.setStatus(ValidationStatus.FAIL);
        }
        return status;
    }

    public Optional<User> findUserById(long id)  {
        try {
            return Optional.of(userService.findUserById(id));
        } catch (EmptyResultDataAccessException erdae) {
            return Optional.empty();
        }
    }

    public Optional<Long> getUserId(String userName) {
        try {
            return Optional.of(userService.getUserId(userName));
        } catch (EmptyResultDataAccessException erdae) {
            return Optional.empty();
        }
    }

    public List<Long> listUserIds() {
        return userService.listUserIds();
    }
}
