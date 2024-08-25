package gg.pixelgruene.oergpbackend.minecraft;

import gg.pixelgruene.oergpbackend.Main;
import org.bukkit.entity.Player;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class User {

    Player p;

    public int getPlayerMoneyByUsername(String username) {
        String query = "SELECT money FROM moneyTable WHERE username = ?";
        int money = 0;
        try (Connection connection = Main.getMoney().getConnection();  // Stelle sicher, dass du eine funktionierende Verbindung erhältst
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                money = resultSet.getInt("money");
            } else {
                // Wenn keine Ergebnisse gefunden wurden
                System.out.println("Spieler mit dem Benutzernamen " + username + " wurde nicht gefunden.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return money;
    }

    public int getPlayerMoneyByUUID(String playerUUID) {
        String query = "SELECT money FROM moneyTable WHERE UUID = ?";
        int money = 0;

        try (Connection connection = Main.getMoney().getConnection();  // Stelle sicher, dass du eine funktionierende Verbindung erhältst
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, playerUUID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                money = resultSet.getInt("money");
            } else {
                // Wenn keine Ergebnisse gefunden wurden
                System.out.println("Spieler mit der UUID " + playerUUID + " wurde nicht gefunden.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Hier kannst du auch mit `throw new RuntimeException(e);` weiterleiten oder eine eigene Exception werfen.
        }
        return money;
    }

    private Connection getConnection() {
        return Main.getBackend().getConnection();
    }

    public String getFirstJoin(String uuid) {
        String query = "SELECT first_join FROM player_data WHERE UUID = ?";
        String firstJoin = null;

        try (Connection connection = Main.getBackend().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                firstJoin = resultSet.getString("first_join");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return firstJoin;
    }

}
