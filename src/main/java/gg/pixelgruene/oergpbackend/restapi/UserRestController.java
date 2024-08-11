package gg.pixelgruene.oergpbackend.restapi;

import gg.pixelgruene.oergpbackend.Main;
import gg.pixelgruene.oergpbackend.user.User;
import gg.pixelgruene.oergpbackend.user.UserController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserController userController = new UserController();

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestParam String email,
                                             @RequestParam long groupid,
                                             @RequestParam String username,
                                             @RequestParam String password) {
        try {
            userController.setUsername(username);
            userController.createUser(email, password, groupid);
            return ResponseEntity.ok("User created successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Error creating user: " + e.getMessage());
        }
    }

    @PostMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestParam String username,
                                                 @RequestParam String email) {
        try {
            userController.updatePassword(username, email);
            return ResponseEntity.ok("Password updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Error updating password: " + e.getMessage());
        }
    }

    @PostMapping("/update-email")
    public ResponseEntity<String> updateEmail(@RequestParam int userId,
                                              @RequestParam String newEmail) {
        try {
            userController.updateEmail(userId, newEmail);
            return ResponseEntity.ok("Email updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Error updating email: " + e.getMessage());
        }
    }

    @PostMapping("/update-group")
    public ResponseEntity<String> updateUserGroup(@RequestParam int userId,
                                                  @RequestParam int groupId) {
        try {
            userController.updateUserGroup(userId, groupId);
            return ResponseEntity.ok("User group updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Error updating user group: " + e.getMessage());
        }
    }

    @PostMapping("/update-username")
    public ResponseEntity<String> updateUsername(@RequestParam int userId,
                                                 @RequestParam String username) {
        try {
            userController.updateUsername(userId, username);
            return ResponseEntity.ok("Username updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Error updating username: " + e.getMessage());
        }
    }

    @PostMapping("/remove-by-id")
    public ResponseEntity<String> removeUserById(@RequestParam int userId) {
        try {
            userController.removeUserById(userId);
            return ResponseEntity.ok("User removed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Error removing user: " + e.getMessage());
        }
    }

    @PostMapping("/remove-by-username")
    public ResponseEntity<String> removeUserByUsername(@RequestParam String username) {
        try {
            userController.removeUserByUsername(username);
            return ResponseEntity.ok("User removed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Error removing user: " + e.getMessage());
        }
    }

    @PostMapping("/remove-by-email")
    public ResponseEntity<String> removeUserByEmail(@RequestParam String email) {
        try {
            userController.removeUserByEmail(email);
            return ResponseEntity.ok("User removed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Error removing user: " + e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public String getUsernameByUserId(@PathVariable int userId) throws SQLException {
        return userController.getUsernameByUserId(userId);
    }

    @GetMapping("/email/{email}")
    public String getUsernameByEmail(@PathVariable String email) throws SQLException {
        return userController.getUsernameByEmail(email);
    }

    @GetMapping("/exists/{username}")
    public boolean isUserExists(@PathVariable String username) throws SQLException {
        return userController.isUserExists(username);
    }

    @GetMapping("/password/{userId}")
    public String getPasswordByUserId(@PathVariable int userId, String password) throws SQLException {
        return userController.getPasswordByUserID(userId, password);
    }

    @GetMapping("/email/by-username/{username}")
    public String getEmailByUsername(@PathVariable String username) throws SQLException {
        return userController.getEmailByUsername(username);
    }

    @GetMapping("/details/{email}")
    public User getUserByEmail(@PathVariable String email) throws SQLException {
        return userController.getUserByEmail(email);
    }

    @GetMapping("/check-username/{username}")
    public void checkUsername(@PathVariable String username) throws SQLException {
        userController.checkUsername(username);
    }

    @GetMapping("/check-email")
    public void checkEmail(@RequestParam String email, @RequestParam String username) throws SQLException {
        userController.checkEmail(email, username);
    }
}
