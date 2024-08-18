package gg.pixelgruene.oergpbackend.user;

import gg.pixelgruene.oergpbackend.Main;
import gg.pixelgruene.oergpbackend.commands.add.CMD_CreateUser;
import lombok.Getter;
import lombok.Setter;

import java.sql.*;

public class User {

    @Getter
    private String username;
    @Setter
    @Getter
    Group group = new Group();
    @Setter
    private String email;
    @Setter
    @Getter
    private String password;

    public User(){}

    public User(String username) {
        this.username = username;
    }

    public String getUsernameByUserId(int userId) {
        String query = "SELECT username FROM users WHERE userid = ?";
        String username = null;

        try (Connection connection = Main.getDatabaseManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                username = resultSet.getString("username");
            }

        } catch (SQLException e) {
            Main.getLogger().logWarn("Fehler beim Abrufen des Benutzernamens nach Benutzer-ID: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return username;
    }

    /**
     * Findet den Benutzernamen eines Benutzers anhand der E-Mail-Adresse.
     *
     * @param email Die E-Mail-Adresse des Benutzers.
     * @return Der Benutzername des Benutzers oder null, wenn kein Benutzer gefunden wurde.
     */
    public String getUsernameByEmail(String email) {
        String query = "SELECT username FROM users WHERE email = ?";
        String username = null;

        try (Connection connection = Main.getDatabaseManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                username = resultSet.getString("username");
            }

        } catch (SQLException e) {
            Main.getLogger().logWarn("Fehler beim Abrufen des Benutzernamens nach E-Mail-Adresse: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return username;
    }

    public boolean isUserExists(String username) {
        try {
            PreparedStatement st = Main.getDatabaseManager().getConnection().prepareStatement("SELECT COUNT(*) FROM users WHERE username = ?");
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            Main.getLogger().logWarn("Fehler beim Abrufen, ob der User existiert.");
            throw new RuntimeException(e);
        }
        return false;
    }

    public String getPasswordByUserID(int id, String pasword) {
        int userId = 0;
        try {
            PreparedStatement st = Main.getDatabaseManager().getConnection().prepareStatement("SELECT password FROM users WHERE userid = ?");
            st.setString(1, String.valueOf(userId));
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                password = rs.getString("password");
                CMD_CreateUser.passwordEncoder.matches(password, pasword);
            }
            return username;
        } catch (SQLException e) {
            Main.getLogger().logWarn("Failed to get the password");
            throw new RuntimeException(e);
        }
    }

    public String getPasswordbyUsername(String username, String pasword) {
        try {
            PreparedStatement st = Main.getDatabaseManager().getConnection().prepareStatement("SELECT password FROM users WHERE username = ?");
            st.setString(1, String.valueOf(username));
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                username = rs.getString("password");
                CMD_CreateUser.passwordEncoder.matches(password, pasword);
            }
            return username;
        } catch (SQLException e) {
            Main.getLogger().logWarn("Failed to get the password");
            throw new RuntimeException(e);
        }
    }

    public int getUserID(String usernameOrEmail) {
        int userId = -1; // Standardwert für ungültige ID
        String query = "SELECT userid FROM users WHERE username = ? OR email = ?";

        try (Connection connection = Main.getDatabaseManager().getConnection();
             PreparedStatement st = connection.prepareStatement(query)) {

            st.setString(1, usernameOrEmail);
            st.setString(2, usernameOrEmail);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("userid");
            }

        } catch (SQLException e) {
            Main.getLogger().logWarn("Failed to get the UserID");
        }

        return userId;
    }

    public String getEmail(String username) {
        return email;
    }

    public String getEmailByUsername(String username) {
        String email = "";
        try {
            PreparedStatement st = Main.getDatabaseManager().getConnection().prepareStatement("SELECT email FROM users WHERE userid = ?");
            st.setString(1, String.valueOf(username));
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                email = rs.getString("email");
            }
            return email;
        } catch (SQLException e) {
            Main.getLogger().logWarn("Failed to get the email");
            return email;
        }
    }

    public String getEmailByUserId(int userId) {
        String email = null;
        try {
            PreparedStatement st = Main.getDatabaseManager().getConnection().prepareStatement("SELECT email FROM users WHERE userid = ?");
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                email = rs.getString("email");
            }
        } catch (SQLException e) {
            Main.getLogger().logWarn("Failed to get the email");
            throw new RuntimeException(e);
        }
        return email;
    }

    public User getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        User user = null;

        try (Connection connection = Main.getDatabaseManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setEmail(resultSet.getString("email"));
            }

        } catch (SQLException e) {
            Main.getLogger().logError("Fehler beim Abrufen des Benutzers nach E-Mail: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return user;
    }

    public User getUser() {
        return this;
    }

    public String checkUsername(String username) throws SQLException{
        if(getUsername().equals(username)){
            Main.getLogger().logInfo("username is already in use");
        }else{
            Main.getLogger().logInfo("username is not in use.");
        }
        return username;
    }

    public String checkEmail(String email) throws SQLException{
        if(getEmailByUsername(getUsername()).contains(email)){
            Main.getLogger().logInfo("Emailadress is already in use");
        }else{
            Main.getLogger().logInfo("Emailadress is not in use.");
        }
        return email;
    }

    public Timestamp getLastLogin(int userId) {
        String query = "SELECT last_login FROM users WHERE userid = ?";
        Timestamp lastLogin = null;

        try (Connection connection = Main.getDatabaseManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                lastLogin = resultSet.getTimestamp("last_login");
            }

        } catch (SQLException e) {
            Main.getLogger().logWarn("Fehler beim Abrufen des letzten Logins: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return lastLogin;
    }

    public Timestamp getRegistrationTime(int userId) {
        String query = "SELECT registration_time FROM users WHERE userid = ?";
        Timestamp registrationTime = null;

        try (Connection connection = Main.getDatabaseManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                registrationTime = resultSet.getTimestamp("registration_time");
            }

        } catch (SQLException e) {
            Main.getLogger().logWarn("Fehler beim Abrufen der Registrierungszeit: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return registrationTime;
    }


    public Timestamp getLastLoginByUsername(String username) {
        String query = "SELECT last_login FROM users WHERE username = ?";
        Timestamp lastLogin = null;

        try (Connection connection = Main.getDatabaseManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                lastLogin = resultSet.getTimestamp("last_login");
            }

        } catch (SQLException e) {
            Main.getLogger().logWarn("Fehler beim Abrufen des letzten Logins nach Benutzername: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return lastLogin;
    }

    public Timestamp getLastLoginByEmail(String email) {
        String query = "SELECT last_login FROM users WHERE email = ?";
        Timestamp lastLogin = null;

        try (Connection connection = Main.getDatabaseManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                lastLogin = resultSet.getTimestamp("last_login");
            }

        } catch (SQLException e) {
            Main.getLogger().logWarn("Fehler beim Abrufen des letzten Logins nach E-Mail: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return lastLogin;
    }

    public Timestamp getRegistrationTimeByUsername(String username) {
        String query = "SELECT registration_time FROM users WHERE username = ?";
        Timestamp registrationTime = null;

        try (Connection connection = Main.getDatabaseManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                registrationTime = resultSet.getTimestamp("registration_time");
            }

        } catch (SQLException e) {
            Main.getLogger().logWarn("Fehler beim Abrufen der Registrierungszeit nach Benutzername: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return registrationTime;
    }

    public Timestamp getRegistrationTimeByEmail(String email) {
        String query = "SELECT registration_time FROM users WHERE email = ?";
        Timestamp registrationTime = null;

        try (Connection connection = Main.getDatabaseManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                registrationTime = resultSet.getTimestamp("registration_time");
            }

        } catch (SQLException e) {
            Main.getLogger().logWarn("Fehler beim Abrufen der Registrierungszeit nach E-Mail: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return registrationTime;
    }

}