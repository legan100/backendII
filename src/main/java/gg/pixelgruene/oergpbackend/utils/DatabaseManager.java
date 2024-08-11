package gg.pixelgruene.oergpbackend.utils;

import gg.pixelgruene.oergpbackend.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private final FileManager fileAPI;
    private Connection c = null;

    public DatabaseManager(String filename) {
        fileAPI = new FileManager(filename+ ".yml");
        if (!fileAPI.exists()) {
            fileAPI.createFile();
            fileAPI.writeInNextFreeLine("database: database");
            fileAPI.writeInNextFreeLine("host: 127.0.0.1");
            fileAPI.writeInNextFreeLine("port: 3306");
            fileAPI.writeInNextFreeLine("username: username");
            fileAPI.writeInNextFreeLine("password: password");
        }
    }

    public String getDatabase() {
        String database = null;
        for (String str : fileAPI.readAll()) {
            if (str.contains("database")) {
                database = str.split(": ")[1];
            }
        }
        return database;
    }

    public String getHost() {
        String host = null;
        for (String str : fileAPI.readAll()) {
            if (str.contains("host")) {
                host = str.split(": ")[1];
            }
        }
        return host;
    }

    public String getPort() {
        String port = null;
        for (String str : fileAPI.readAll()) {
            if (str.contains("port")) {
                port = str.split(": ")[1];
            }
        }
        return port;
    }

    public String getDatabaseUsername() {
        String username = null;
        for (String str : fileAPI.readAll()) {
            if (str.contains("username")) {
                username = str.split(": ")[1];
            }
        }
        return username;
    }

    public String getDatabasePassword() {
        String password = null;
        for (String str : fileAPI.readAll()) {
            if (str.contains("password")) {
                password = str.split(": ")[1];
            }
        }
        return password;
    }

    public void connect() {
        final String url="jdbc:mariadb://" + this.getHost() + ":" + this.getPort() + "/" + getDatabase();
        String driver="org.mariadb.jdbc.Driver";
        try {
            Class.forName(driver);
            c=DriverManager.getConnection(url, getDatabaseUsername(), getDatabasePassword());
            Main.getLogger().logInfo("Connected to Database " + this.getDatabase() + "!");
        } catch (Exception e) {
            Main.getLogger().logError(e.getClass().getName() + ": " + e.getMessage());
            System.exit(3);
        }
    }

    public Connection getConnection() {
        return c;
    }

    public void disconnect() {
        try {
            c.close();
            Main.getLogger().logInfo("Disconnected from Database " + this.getDatabase() + "!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}