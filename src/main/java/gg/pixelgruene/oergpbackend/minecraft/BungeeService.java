package gg.pixelgruene.oergpbackend.minecraft;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class BungeeService {

    private static final String BUNGEECORD_PATH = "/home/nico/Minecraftserver/Bungeecord";
    private static final String CONFIG_PATH = BUNGEECORD_PATH + "/config.yml";
    private static final String PLUGINS_DIR = BUNGEECORD_PATH + "/plugins";

    public static boolean checkIfBungeeCordIsRunning() {
        try {
            String line;
            Process p = Runtime.getRuntime().exec("ps -ef | grep BungeeCord.jar | grep -v grep");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                if (line.contains("BungeeCord.jar")) {
                    return true;
                }
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void startBungeeCord() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(BUNGEECORD_PATH);

        // Setzt das Arbeitsverzeichnis, falls nötig
        processBuilder.directory(new File("/home/nico/Minecraftserver/Bungeecord"));

        // Fehler- und Standard-Stream zusammenführen (optional)
        processBuilder.redirectErrorStream(true);

        // Optional: Log-Datei zur Ausgabe
        File log = new File("/home/nico/Minecraftserver/Bungeecord/bungeecord_startup.log");
        processBuilder.redirectOutput(log);

        // Startet den Prozess
        Process process = processBuilder.start();

        System.out.println("BungeeCord wird über start.sh gestartet.");
    }

    public void addServer(String name, String ip, int port, String description) throws IOException {
        Yaml yaml = new Yaml();
        Map<String, Object> data;

        try (FileInputStream inputStream = new FileInputStream(CONFIG_PATH)) {
            data = yaml.load(inputStream);
        }

        Map<String, Object> servers = (Map<String, Object>) data.get("servers");
        Map<String, Object> newServer = Map.of(
                "address", ip + ":" + port,
                "motd", description,
                "restricted", false
        );
        servers.put(name, newServer);

        saveConfig(data);
    }

    public void updateServer(String name, String newIp, int newPort, String newDescription) throws IOException {
        Yaml yaml = new Yaml();
        Map<String, Object> data;

        try (FileInputStream inputStream = new FileInputStream(CONFIG_PATH)) {
            data = yaml.load(inputStream);
        }

        Map<String, Object> servers = (Map<String, Object>) data.get("servers");
        if (servers.containsKey(name)) {
            Map<String, Object> server = (Map<String, Object>) servers.get(name);
            server.put("address", newIp + ":" + newPort);
            server.put("motd", newDescription);
            saveConfig(data);
        } else {
            System.out.println("Server not found.");
        }
    }

    public void deleteServer(String name) throws IOException {
        Yaml yaml = new Yaml();
        Map<String, Object> data;

        try (FileInputStream inputStream = new FileInputStream(CONFIG_PATH)) {
            data = yaml.load(inputStream);
        }

        Map<String, Object> servers = (Map<String, Object>) data.get("servers");
        if (servers.containsKey(name)) {
            servers.remove(name);
            saveConfig(data);
        } else {
            System.out.println("Server not found.");
        }
    }

    public void addRank(String rankName) throws IOException {
        Yaml yaml = new Yaml();
        Map<String, Object> data;

        try (FileInputStream inputStream = new FileInputStream(CONFIG_PATH)) {
            data = yaml.load(inputStream);
        }

        Map<String, Object> permissions = (Map<String, Object>) data.get("permissions");
        if (!permissions.containsKey(rankName)) {
            permissions.put(rankName, List.of());
            saveConfig(data);
        } else {
            System.out.println("Rank already exists.");
        }
    }

    public void deleteRank(String rankName) throws IOException {
        Yaml yaml = new Yaml();
        Map<String, Object> data;

        try (FileInputStream inputStream = new FileInputStream(CONFIG_PATH)) {
            data = yaml.load(inputStream);
        }

        Map<String, Object> permissions = (Map<String, Object>) data.get("permissions");
        if (permissions.containsKey(rankName)) {
            permissions.remove(rankName);
            saveConfig(data);
        } else {
            System.out.println("Rank not found.");
        }
    }

    public void changePlayerRank(String playerName, String newRank) throws IOException {
        Yaml yaml = new Yaml();
        Map<String, Object> data;

        try (FileInputStream inputStream = new FileInputStream(CONFIG_PATH)) {
            data = yaml.load(inputStream);
        }

        Map<String, Object> groups = (Map<String, Object>) data.get("groups");
        if (groups.containsKey(playerName)) {
            groups.put(playerName, List.of(newRank));
            saveConfig(data);
        } else {
            System.out.println("Player not found.");
        }
    }

    private void saveConfig(Map<String, Object> data) throws IOException {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);

        try (FileWriter writer = new FileWriter(CONFIG_PATH)) {
            yaml.dump(data, writer);
        }
    }

    public static void listPlugins() throws IOException {
        File pluginDir = new File(PLUGINS_DIR);
        File[] files = pluginDir.listFiles((dir, name) -> name.endsWith(".jar"));

        if (files == null) {
            System.out.println("No plugins found.");
            return;
        }

        for (File file : files) {
            try (ZipFile zipFile = new ZipFile(file)) {
                ZipEntry entry = zipFile.getEntry("plugin.yml");

                if (entry != null) {
                    try (InputStream inputStream = zipFile.getInputStream(entry)) {
                        Yaml yaml = new Yaml();
                        Map<String, Object> pluginInfo = yaml.load(inputStream);
                        System.out.println("Plugin Name: " + pluginInfo.get("name"));
                        System.out.println("Version: " + pluginInfo.get("version"));
                        System.out.println("Main Class: " + pluginInfo.get("main"));
                        System.out.println("----------------------------");
                    }
                }
            } catch (IOException e) {
                System.out.println("Failed to read plugin: " + file.getName());
                e.printStackTrace();
            }
        }
    }
}
