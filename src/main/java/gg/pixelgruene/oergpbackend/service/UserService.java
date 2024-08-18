package gg.pixelgruene.oergpbackend.service;

import gg.pixelgruene.oergpbackend.Main;
import gg.pixelgruene.oergpbackend.user.User;
import gg.pixelgruene.oergpbackend.user.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;

@Service
public class UserService {

    @Autowired
    private UserController userRepository;

    public String getUsernameByUserId(int userId) throws SQLException {
        return userRepository.getUsernameByUserId(userId);
    }

    public String getUsernameByEmail(String email) throws SQLException {
        return userRepository.getUsernameByEmail(email);
    }

    public boolean isUserExists(String username) throws SQLException {
        return userRepository.isUserExists(username);
    }

    public String getPasswordByUserId(int userId, String password) throws SQLException {
        return userRepository.getPasswordByUserID(userId, password);
    }

    public String getEmailByUsername(String username) throws SQLException {
        return userRepository.getEmailByUsername(username);
    }

    public User getUserByEmail(String email) throws SQLException {
        return userRepository.getUserByEmail(email);
    }

    public void checkUsername(String username) throws SQLException {
        if (userRepository.isUserExists(username)) {
            Main.getLogger().logInfo("Username is already in use");
        } else {
            Main.getLogger().logInfo("Username is not in use");
        }
    }

    public void checkEmail(String email, String username) throws SQLException {
        String currentEmail = userRepository.getEmailByUsername(username);
        if (currentEmail != null && currentEmail.equals(email)) {
            Main.getLogger().logInfo("Email address is already in use");
        } else {
            Main.getLogger().logInfo("Email address is not in use");
        }
    }

    public Timestamp getLastLoginByUsername(String username) {
        User user = new User();
        return user.getLastLoginByUsername(username);
    }

    public Timestamp getLastLoginByEmail(String email) {
        User user = new User();
        return user.getLastLoginByEmail(email);
    }

    public Timestamp getRegistrationTimeByUsername(String username) {
        User user = new User();
        return user.getRegistrationTimeByUsername(username);
    }

    public Timestamp getRegistrationTimeByEmail(String email) {
        User user = new User();
        return user.getRegistrationTimeByEmail(email);
    }
}
