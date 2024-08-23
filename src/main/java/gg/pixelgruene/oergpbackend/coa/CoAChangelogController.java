package gg.pixelgruene.oergpbackend.coa;

import gg.pixelgruene.oergpbackend.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CoAChangelogController {

    public void createNewChangelog(String changelog, String author) {
        String insertChangelogSQL = "INSERT INTO CoAChangelog (changedate, authorid) VALUES (CURRENT_TIMESTAMP, (SELECT userid FROM users WHERE username = ?))";

        try (Connection connection = Main.getBackend().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertChangelogSQL)) {

            preparedStatement.setString(1, author);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Main.getLogger().logInfo("Changelog erfolgreich erstellt.");
            } else {
                Main.getLogger().logWarn("Changelog konnte nicht erstellt werden.");
            }

        } catch (SQLException e) {
            Main.getLogger().logError("Fehler beim Erstellen des Changelogs: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void removeChangelog(int id) {
        String deleteChangelogSQL = "DELETE FROM CoAChangelog WHERE changelogid = ?";

        try (Connection connection = Main.getBackend().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteChangelogSQL)) {

            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Main.getLogger().logInfo("Changelog mit ID " + id + " erfolgreich entfernt.");
            } else {
                Main.getLogger().logWarn("Kein Changelog mit ID " + id + " gefunden.");
            }

        } catch (SQLException e) {
            Main.getLogger().logError("Fehler beim Entfernen des Changelogs: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
