package gg.pixelgruene.oergpbackend.user;

import gg.pixelgruene.oergpbackend.Main;
import gg.pixelgruene.oergpbackend.commands.add.CMD_CreateUser;
import lombok.Getter;
import lombok.Setter;

import javax.mail.internet.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
public class UserController extends User{

    String username;

    public boolean isUsernameIsValid(String username) {
        if (username == null || username.length() < 3 || username.length() > 20) {
            return false;
        }
        return username.matches("^[a-zA-Z0-9_]+$");
    }

    public boolean isUsernameAvailable(String username) {
        try {
            PreparedStatement st = Main.getDatabaseManager().getConnection().prepareStatement("SELECT COUNT(*) FROM users WHERE username = ?");
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            Main.getLogger().logError("");
        }
        return false;
    }

    public void createUser(String email, String password, long groupid){
        try {
            if (email == null || email.isEmpty()) {
                throw new IllegalArgumentException("Email address is empty or null");
            }
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
            this.checkUsername(this.getUsername());
            Main.getLogger().logInfo("GroupID: " + groupid);
            PreparedStatement st = Main.getDatabaseManager().getConnection().prepareStatement("INSERT INTO users (email, username, password, groupid) VALUES (?, ?, ?, ?)");
            st.setString(1, email);
            st.setString(2, this.getUsername());
            String passwordEncoderpass = CMD_CreateUser.passwordEncoder.encode(password);
            st.setString(3, passwordEncoderpass);
            st.setLong(4, groupid);
            Main.getLogger().logInfo(st.toString());
            st.executeUpdate();

            // Send confirmation email
            Main.getEmailHandler().sendEmail(this.getUser(), "Logindaten",
                    "Hallo " + this.getUsername() + ", \n\nwir freuen uns, dass du dich registriert hast.\n\nIn dieser E-Mail erhälst du deine Zugangsdaten.\n\nBitte melde dich mit diesen an. Nach dieser Anmeldung wirst du aufgefordert, das Passwort zu ändern. \n\nName: " + this.getEmail(this.getUsername()) + "\nPasswort: " + this.getPassword() + "\n\nHier findest du die Datenschutzerklärung. \nSolltest du noch Fragen haben, darfst du dich gerne an den Support herantreten. \n\nViele Grüße\n\nDein Team", this.getEmail(this.getUsername()));
        } catch (AddressException e) {
            throw new RuntimeException("Invalid email address: " + email, e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePassword(String username, String email) {
        if(isUserExists(username)){
            String newPassword = Main.getInternalMethods().createPassword(16, "abcdefghijklmnopqrstuvwxyz","ABCDEFGHIJKLMNOPQRSTUVWXYZ","-.,;.-!0123456789");
            try {
                PreparedStatement st = Main.getDatabaseManager().getConnection().prepareStatement("UPDATE users SET password = ? WHERE username = ?");
                st.setString(1, newPassword);
                st.setString(2, username);
                st.executeUpdate();
                Main.getLogger().logInfo("Password for user " + username + " updated successfully for user: " + username);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Main.getEmailHandler().sendEmail(this.getUser(), "Passwortänderung", "Hallo "+ this.getUsername() + ",\n\ndu hast gerade ein neues Passwort angefordert für deinen Zugang.\n\nDein neues Passwort lautet: "+newPassword+"\n\nViele Grüße \n\ndein Team vom ACP", email);
        }
    }

    /**
     * Aktualisiert die E-Mail-Adresse eines Benutzers in der Datenbank.
     *
     * @param userId   Die ID des Benutzers, dessen E-Mail-Adresse aktualisiert werden soll.
     * @param newEmail Die neue E-Mail-Adresse des Benutzers.
     * @throws SQLException Wenn ein SQL-Fehler auftritt.
     */
    public void updateEmail(int userId, String newEmail) {
        String updateEmailSQL = "UPDATE users SET email = ? WHERE userid = ?";

        try (Connection connection = Main.getDatabaseManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateEmailSQL)) {

            preparedStatement.setString(1, newEmail);
            preparedStatement.setInt(2, userId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Main.getLogger().logInfo("E-Mail-Adresse erfolgreich aktualisiert.");
            } else {
                Main.getLogger().logInfo("Kein Benutzer mit ID " + userId + " gefunden.");
            }

        } catch (SQLException e) {
            Main.getLogger().logInfo("Fehler beim Aktualisieren der E-Mail-Adresse: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Aktualisiert die E-Mail-Adresse eines Benutzers in der Datenbank basierend auf dem Benutzernamen.
     *
     * @param username Der Benutzername des Benutzers, dessen E-Mail-Adresse aktualisiert werden soll.
     * @param newEmail Die neue E-Mail-Adresse des Benutzers.
     * @throws SQLException Wenn ein SQL-Fehler auftritt.
     */
    public void updateEmail(String username, String newEmail) {
        String updateEmailSQL = "UPDATE users SET email = ? WHERE username = ?";

        try (Connection connection = Main.getDatabaseManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateEmailSQL)) {

            preparedStatement.setString(1, newEmail);
            preparedStatement.setString(2, username);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Main.getLogger().logInfo("E-Mail-Adresse erfolgreich aktualisiert.");
            } else {
                Main.getLogger().logInfo("Kein Benutzer mit dem Benutzernamen " + username + " gefunden.");
            }

        } catch (SQLException e) {
            Main.getLogger().logWarn("Fehler beim Aktualisieren der E-Mail-Adresse: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void updateUserGroup(int userId, int groupId) {
        String updateUserGroupSQL = "UPDATE users SET groupid = ? WHERE userid = ?";

        try (Connection connection = Main.getDatabaseManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateUserGroupSQL)) {

            preparedStatement.setInt(1, groupId);
            preparedStatement.setInt(2, userId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Main.getLogger().logInfo("Benutzergruppe erfolgreich aktualisiert.");
            } else {
                Main.getLogger().logInfo("Kein Benutzer mit der ID " + userId + " gefunden.");
            }

        } catch (SQLException e) {
            Main.getLogger().logWarn("Fehler beim Aktualisieren der Benutzergruppe: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Aktualisiert die Gruppen-ID eines Benutzers in der Datenbank basierend auf dem Benutzernamen.
     *
     * @param username Der Benutzername des Benutzers, dessen Gruppen-ID aktualisiert werden soll.
     * @param groupId  Die neue Gruppen-ID des Benutzers.
     * @throws SQLException Wenn ein SQL-Fehler auftritt.
     */
    public void updateUserGroup(String username, int groupId) {
        String updateUserGroupSQL = "UPDATE users SET groupid = ? WHERE username = ?";

        try (Connection connection = Main.getDatabaseManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateUserGroupSQL)) {

            preparedStatement.setInt(1, groupId);
            preparedStatement.setString(2, username);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Main.getLogger().logInfo("Benutzergruppe erfolgreich aktualisiert.");
            } else {
                Main.getLogger().logInfo("Kein Benutzer mit dem Benutzernamen " + username + " gefunden.");
            }

        } catch (SQLException e) {
            Main.getLogger().logWarn("Fehler beim Aktualisieren der Benutzergruppe: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Aktualisiert den Benutzernamen eines Benutzers in der Datenbank basierend auf der Benutzer-ID.
     *
     * @param userId   Die ID des Benutzers, dessen Benutzername aktualisiert werden soll.
     * @param username Der neue Benutzername.
     * @throws SQLException Wenn ein SQL-Fehler auftritt.
     */
    public void updateUsername(int userId, String username) {
        String updateUsernameSQL = "UPDATE users SET username = ? WHERE userid = ?";

        try (Connection connection = Main.getDatabaseManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateUsernameSQL)) {

            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, userId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Main.getLogger().logInfo("Benutzername erfolgreich aktualisiert.");
            } else {
                Main.getLogger().logInfo("Kein Benutzer mit der ID " + userId + " gefunden.");
            }

        } catch (SQLException e) {
            Main.getLogger().logWarn("Fehler beim Aktualisieren des Benutzernamens: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Aktualisiert den Benutzernamen eines Benutzers in der Datenbank basierend auf der E-Mail-Adresse.
     *
     * @param email    Die E-Mail-Adresse des Benutzers, dessen Benutzername aktualisiert werden soll.
     * @param username Der neue Benutzername.
     * @throws SQLException Wenn ein SQL-Fehler auftritt.
     */
    public void updateUsernameByEmail(String email, String username) {
        String updateUsernameSQL = "UPDATE users SET username = ? WHERE email = ?";

        try (Connection connection = Main.getDatabaseManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateUsernameSQL)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Main.getLogger().logInfo("Benutzername erfolgreich aktualisiert.");
            } else {
                Main.getLogger().logInfo("Kein Benutzer mit der E-Mail-Adresse " + email + " gefunden.");
            }

        } catch (SQLException e) {
            Main.getLogger().logWarn("Fehler beim Aktualisieren des Benutzernamens: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Entfernt einen Benutzer aus der Datenbank anhand ihrer Benutzer-ID.
     *
     * @param userId Die ID des Benutzers, der entfernt werden soll.
     * @throws SQLException Wenn ein SQL-Fehler auftritt.
     */
    public void removeUserById(int userId) {
        String deleteUserSQL = "DELETE FROM users WHERE userid = ?";

        try (Connection connection = Main.getDatabaseManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteUserSQL)) {

            preparedStatement.setInt(1, userId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Main.getLogger().logInfo("Benutzer mit ID " + userId + " erfolgreich entfernt.");
            } else {
                Main.getLogger().logInfo("Kein Benutzer mit ID " + userId + " gefunden.");
            }

        } catch (SQLException e) {
            Main.getLogger().logWarn("Fehler beim Entfernen des Benutzers: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Entfernt einen Benutzer aus der Datenbank anhand ihres Benutzernamens.
     *
     * @param username Der Name des Benutzers, der entfernt werden soll.
     * @throws SQLException Wenn ein SQL-Fehler auftritt.
     */
    public void removeUserByUsername(String username) {
        String deleteUserSQL = "DELETE FROM users WHERE username = ?";

        try (Connection connection = Main.getDatabaseManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteUserSQL)) {

            preparedStatement.setString(1, username);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Main.getLogger().logInfo("Benutzer mit Namen " + username + " erfolgreich entfernt.");
            } else {
                Main.getLogger().logInfo("Kein Benutzer mit Namen " + username + " gefunden.");
            }

        } catch (SQLException e) {
            Main.getLogger().logWarn("Fehler beim Entfernen des Benutzers: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Entfernt einen Benutzer aus der Datenbank anhand ihrer E-Mail-Adresse.
     *
     * @param email Die E-Mail-Adresse des Benutzers, der entfernt werden soll.
     * @throws SQLException Wenn ein SQL-Fehler auftritt.
     */
    public void removeUserByEmail(String email) {
        String deleteUserSQL = "DELETE FROM users WHERE email = ?";

        try (Connection connection = Main.getDatabaseManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteUserSQL)) {

            preparedStatement.setString(1, email);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Main.getLogger().logInfo("Benutzer mit E-Mail " + email + " erfolgreich entfernt.");
            } else {
                Main.getLogger().logInfo("Kein Benutzer mit E-Mail " + email + " gefunden.");
            }

        } catch (SQLException e) {
            Main.getLogger().logWarn("Fehler beim Entfernen des Benutzers: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}