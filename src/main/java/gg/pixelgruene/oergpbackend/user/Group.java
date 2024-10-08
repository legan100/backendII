package gg.pixelgruene.oergpbackend.user;

import gg.pixelgruene.oergpbackend.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Group {

    private Long id;

    private String groupname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public long getGroupID(String groupname) {
        Long groupId = null;
        try {
            PreparedStatement st = Main.getBackend().getConnection().prepareStatement(
                    "SELECT groupid FROM groups WHERE groupname = ?");
            st.setString(1, groupname);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                groupId = rs.getLong("groupid");
            }
        } catch (SQLException e) {
            Main.getLogger().logError(e.getCause().toString());
            throw new RuntimeException(e);
        }
        return groupId;    }

    public static String getGroupNameById(Long groupid) {
        String groupName = null;
        try {
            PreparedStatement st = Main.getBackend().getConnection().prepareStatement(
                    "SELECT groupname FROM groups WHERE groupid = ?");
            st.setLong(1, groupid);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                groupName = rs.getString("groupname");
            }
        } catch (SQLException e) {
            Main.getLogger().logError(e.getCause().toString());
            throw new RuntimeException(e);
        }
        return groupName;
    }

    public boolean isGroupNameAvailable(String groupName) {
        String query = "SELECT COUNT(*) FROM groups WHERE groupname = ?";

        try (Connection connection = Main.getBackend().getConnection();
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

        return false;
    }

    public static List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<>();
        try {
            PreparedStatement st = Main.getBackend().getConnection().prepareStatement("SELECT * FROM groups");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Group group = new Group();
                group.setId(rs.getLong("groupid"));
                group.setGroupname(rs.getString("groupname"));
                groups.add(group);
            }
        } catch (SQLException e) {
            Main.getLogger().logError(e.getCause().toString());
            throw new RuntimeException(e);
        }
        return groups;
    }

}
