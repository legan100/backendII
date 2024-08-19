package gg.pixelgruene.oergpbackend.minecraft;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gg.pixelgruene.oergpbackend.Main;
import org.bukkit.entity.Player;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    public void getFirstJoin(Player p) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Lese die JSON-Datei und konvertiere sie in eine Liste von User-Objekten
            List<User> users = mapper.readValue(new File("/home/nico/Minecraftserver/Lobby/usercache.json"), new TypeReference<>() {
            });

            // Ausgabe der Benutzerdaten
            for (User user : users) {
                System.out.println(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() {
        // Hier den Code zur Erstellung der Datenbankverbindung einfügen
        // Beispiel: return DriverManager.getConnection(url, user, password);

        return Main.getLogin().getConnection(); // Beispiel für deine bestehende Methode
    }

    public String getFirstJoin(String uuid) {
        String query = "SELECT first_join FROM player_data WHERE UUID = ?";
        String firstJoin = null;

        try (Connection connection = Main.getLogin().getConnection();
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
