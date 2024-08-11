package gg.pixelgruene.oergpbackend;

import gg.pixelgruene.oergpbackend.commands.*;
import gg.pixelgruene.oergpbackend.commands.add.CMD_CreateUser;
import gg.pixelgruene.oergpbackend.commands.check.*;
import gg.pixelgruene.oergpbackend.commands.stats.*;
import gg.pixelgruene.oergpbackend.commands.update.CMD_updatePassword;
import gg.pixelgruene.oergpbackend.serverhandler.*;
import gg.pixelgruene.oergpbackend.utils.*;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Main {

    static ConsoleHandler consoleHandler = new ConsoleHandler();
    @Getter
    static LogHandler logger;
    @Getter
    static DatabaseManager databaseManager;
    @Getter
    static Scanner scanner = new Scanner(System.in);
    @Getter
    static InternalMethods internalMethods = new InternalMethods();
    @Getter
    static EmailHandler emailHandler = new EmailHandler();

    public static void main(String[] args) {
        final long timeStart = System.currentTimeMillis();
        consoleHandler.setError("[ERROR] ");
        consoleHandler.setInfo("[INFO] ");
        consoleHandler.setWarning("[WARNING] ");
        logger = new LogHandler();
        logger.startLogging();
        logger.logInfo("Backend ist starting");
        databaseManager = new DatabaseManager("backend");
        databaseManager.connect();
        SpringApplication.run(Main.class, args);
        final long timeEnd = System.currentTimeMillis();
        getLogger().logInfo("Starttime: " + (timeEnd - timeStart) + " ms.\n");
        do {
            System.out.print("> ");
            String eingabe = scanner.nextLine();
            switch (eingabe) {
                case "stop", "end" -> CMD_Stop.onCommand();
                case "clear", "cls"-> CMD_Clear.onCommand();
                case "createuser" -> CMD_CreateUser.onCommand();
                case "updatepassword" -> CMD_updatePassword.onCommand();
                case "help", "?"-> CMD_Help.onCommand();
                case "getuser"-> CMD_GetUser.onCommand();
                case "checkusername"-> CMD_CheckUsername.onCommand();
                case "checkmail", "checkemail"-> CMD_CheckEmail.onCommand();
                case "javainfo" -> CMD_Javainfo.onCommand();
                case "networkstats"-> CMD_Networkstats.onCommand();
                case "serverstats" -> CMD_Serverstats.onCommand();
                case "stats"-> CMD_Stats.onCommand();
                default -> getLogger().logInfo("Please use 'help' for help.\n");
            }

        }while (true);
    }

    public static void onStop() {
        getLogger().logInfo("Backend is stopping");
        getDatabaseManager().disconnect();
        getLogger().logInfo("Backend is stopped");
        System.exit(3);
    }
}
