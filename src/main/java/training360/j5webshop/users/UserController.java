package training360.j5webshop.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import training360.j5webshop.authentication.UserRole;
import training360.j5webshop.validation.ResponseStatus;
import training360.j5webshop.validation.ValidationStatus;
import training360.j5webshop.validation.Validator;

import javax.validation.Validation;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

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
        if (userService.listUserIds().contains(id)) {
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

    @PostMapping("/useraddresses")
    public ResponseStatus saveNewAddress(@RequestParam long id, @RequestBody String newAddress) {
        ResponseStatus status = new ResponseStatus();
        String[] arr = newAddress.split(";");
        Validator validator = new Validator(arr);
        if (validator.getResponseStatus().getStatus() == ValidationStatus.FAIL) {
            status.addMessage(validator.getResponseStatus().getMessages().get(0));
            status.setStatus(ValidationStatus.FAIL);
        } else {
            if (!findUserById(id).isPresent()) {
                status.addMessage("Ezzel az id-vel felhasználó nem található!");
                status.setStatus(ValidationStatus.FAIL);
                return status;
            }
            User user = findUserById(id).get();
            if (arr.length == 5) {
                Address addressToSave = new Address(user, arr[0].trim(), arr[1].trim(), arr[2].trim(), arr[3].trim(), arr[4].trim());
                if (usersAddressIsAlreadySaved(addressToSave, user)) {
                    status.addMessage("A megadott cím már szerepel az adatbázisunkban!");
                    status.setStatus(ValidationStatus.FAIL);
                    return status;
                }
                userService.saveNewAddress(addressToSave);
                status.addMessage("A szállítási cím sikeresen létrejött!");
                status.setStatus(ValidationStatus.SUCCESS);
            } else if (arr.length == 6) {
                Address addressToSave = new Address(user, arr[0].trim(), arr[1].trim(), arr[2].trim(), arr[3].trim(), arr[4].trim(), arr[5].trim());
                if (usersAddressIsAlreadySaved(addressToSave, user)) {
                    status.addMessage("A megadott cím már szerepel az adatbázisunkban!");
                    status.setStatus(ValidationStatus.FAIL);
                    return status;
                }
                userService.saveNewAddress(addressToSave);
                status.addMessage("A szállítási cím sikeresen létrejött!");
                status.setStatus(ValidationStatus.SUCCESS);
            }
        }
        return status;
    }

    @GetMapping("/useraddresses")
    public List<Address> listUserAddresses(Authentication authentication) {
        return userService.listUserAddresses(authentication.getName());
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

    public boolean usersAddressIsAlreadySaved(Address address, User user){
        if (userService.listUserAddresses(user.getUserName()) == null || userService.listUserAddresses(user.getUserName()).size() == 0) {
            return false;
        } else {
            for (Address a : userService.listUserAddresses(user.getUserName())){
                if (a.equals(address)){
                    return true;
                }
            }
            return false;
        }
    }

}
