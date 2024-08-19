package gg.pixelgruene.oergpbackend.changelog;

import gg.pixelgruene.oergpbackend.Main;
import lombok.Setter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Changelog {

    @Setter
    int changelogId;
    @Setter
    Timestamp changeDate;
    @Setter
    int authorId;

    public List<Changelog> getAllChangelogs() {
        String selectChangelogSQL = "SELECT * FROM Changelog";
        List<Changelog> changelogs = new ArrayList<>();

        try (Connection connection = Main.getBackend().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectChangelogSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Changelog changelog = new Changelog();
                changelog.setChangelogId(resultSet.getInt("changelogid"));
                changelog.setChangeDate(resultSet.getTimestamp("changedate"));
                changelog.setAuthorId(resultSet.getInt("authorid"));
                changelogs.add(changelog);
            }

        } catch (SQLException e) {
            Main.getLogger().logError("Fehler beim Abrufen der Changelog-Einträge: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return changelogs;
    }

}
