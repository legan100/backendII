package gg.pixelgruene.oergpbackend.user;

import gg.pixelgruene.oergpbackend.Main;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class GroupController extends Group{

    private Long id;
    private String groupname;

    public void setId(Long id) {
        this.id = id;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public boolean isGroupNameAvailable(String groupName) {
        String query = "SELECT COUNT(*) FROM groups WHERE groupname = ?";

        try (Connection connection = Main.getDatabaseManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, groupName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) == 0;
            }
        } catch (SQLException e) {
            Main.getLogger().logError("Fehler bei der Überprüfung des Gruppennamens: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return false; // Falls keine Daten gefunden wurden, nehmen wir an, dass der Name verfügbar ist
    }

    public void addGroup(String groupName) {
        try {
            PreparedStatement st = Main.getDatabaseManager().getConnection().prepareStatement("INSERT INTO groups (groupname) VALUES (?)");
            st.executeUpdate();
            Main.getLogger().logInfo("Gruppe erfolgreich hinzugefügt: " + groupName);

        } catch (SQLException e) {
            Main.getLogger().logError("Fehler beim Hinzufügen der Gruppe: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Aktualisiert den Namen einer Gruppe in der Datenbank.
     *
     * @param groupId   Die ID der Gruppe, die aktualisiert werden soll.
     * @param newName Der neue Name der Gruppe.
     * @throws SQLException Wenn ein SQL-Fehler auftritt.
     */
    public void updateGroupName(int groupId, String newName) {
        String updateGroupSQL = "UPDATE groups SET groupname = ? WHERE groupid = ?";

        try (Connection connection = Main.getDatabaseManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateGroupSQL)) {

            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, groupId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Main.getLogger().logInfo("Gruppenname erfolgreich aktualisiert.");
            } else {
                Main.getLogger().logInfo("Keine Gruppe mit ID " + groupId + " gefunden.");
            }

        } catch (SQLException e) {
            Main.getLogger().logError("Fehler beim Aktualisieren des Gruppennamens: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Entfernt eine Gruppe aus der Datenbank anhand ihrer Gruppen-ID.
     *
     * @param groupId Die ID der Gruppe, die entfernt werden soll.
     * @throws SQLException Wenn ein SQL-Fehler auftritt.
     */
    public void removeGroupById(int groupId) {
        String deleteGroupSQL = "DELETE FROM groups WHERE groupid = ?";

        try (Connection connection = Main.getDatabaseManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteGroupSQL)) {

            preparedStatement.setInt(1, groupId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Main.getLogger().logInfo("Gruppe mit ID " + groupId + " erfolgreich entfernt.");
            } else {
                Main.getLogger().logInfo("Keine Gruppe mit ID " + groupId + " gefunden.");
            }

        } catch (SQLException e) {
            Main.getLogger().logError("Fehler beim Entfernen der Gruppe: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Entfernt eine Gruppe aus der Datenbank anhand ihres Gruppennamens.
     *
     * @param groupName Der Name der Gruppe, die entfernt werden soll.
     */
    public void removeGroupByName(String groupName) {
        String deleteGroupSQL = "DELETE FROM groups WHERE groupname = ?";

        try (Connection connection = Main.getDatabaseManager().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteGroupSQL)) {

            preparedStatement.setString(1, groupName);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Main.getLogger().logInfo("Gruppe mit Namen " + groupName + " erfolgreich entfernt.");
            } else {
                Main.getLogger().logInfo("Keine Gruppe mit Namen " + groupName + " gefunden.");
            }

        } catch (SQLException e) {
            Main.getLogger().logError("Fehler beim Entfernen der Gruppe: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
