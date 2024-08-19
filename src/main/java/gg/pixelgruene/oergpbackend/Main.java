package gg.pixelgruene.oergpbackend;

import gg.pixelgruene.oergpbackend.commands.*;
import gg.pixelgruene.oergpbackend.commands.add.CMD_CreateUser;
import gg.pixelgruene.oergpbackend.commands.check.*;
import gg.pixelgruene.oergpbackend.commands.stats.*;
import gg.pixelgruene.oergpbackend.commands.update.CMD_updatePassword;
import gg.pixelgruene.oergpbackend.serverhandler.*;
import gg.pixelgruene.oergpbackend.utils.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Main {

    static ConsoleHandler consoleHandler = new ConsoleHandler();
    static LogHandler logger;
    static DatabaseManager backend;
    static DatabaseManager money;
    static DatabaseManager changelog;
    static DatabaseManager login;
    static Scanner scanner = new Scanner(System.in);
    static InternalMethods internalMethods = new InternalMethods();
    static EmailHandler emailHandler = new EmailHandler();

    public static void main(String[] args) {
        final long timeStart = System.currentTimeMillis();
        consoleHandler.setError("[ERROR] ");
        consoleHandler.setInfo("[INFO] ");
        consoleHandler.setWarning("[WARNING] ");
        logger = new LogHandler();
        logger.startLogging();
        logger.logInfo("Backend ist starting");
        backend = new DatabaseManager("backend");
        backend.connect();
        money = new DatabaseManager("money");
        money.connect();
        changelog = new DatabaseManager("changelog");
        changelog.connect();
        login = new DatabaseManager("login");
        login.connect();
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
        getBackend().disconnect();
        getLogger().logInfo("Backend is stopped");
        System.exit(3);
    }

    public static LogHandler getLogger() {
        return logger;
    }

    public static EmailHandler getEmailHandler() {
        return emailHandler;
    }

    public static InternalMethods getInternalMethods() {
        return internalMethods;
    }

    public static Scanner getScanner() {
        return scanner;
    }

    public static ConsoleHandler getConsoleHandler() {
        return consoleHandler;
    }

    public static DatabaseManager getBackend() {
        return backend;
    }

    public static DatabaseManager getChangelog() {
        return changelog;
    }

    public static DatabaseManager getMoney() {
        return money;
    }

    public static DatabaseManager getLogin() {
        return login;
    }
}
