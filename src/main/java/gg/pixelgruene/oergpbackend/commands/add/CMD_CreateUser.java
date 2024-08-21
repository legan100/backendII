package gg.pixelgruene.oergpbackend.commands.add;

import gg.pixelgruene.oergpbackend.Main;
import gg.pixelgruene.oergpbackend.user.UserController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CMD_CreateUser {

    public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(13);

    public static void onCommand(){
        String username, password, email;
        Long groupid;
        password = Main.getInternalMethods().createPassword(16, "abcdefghijklmnopqrstuvwxyz","ABCDEFGHIJKLMNOPQRSTUVWXYZ","-.,;.-!0123456789");
        do{
            Main.getLogger().logInfo("What is the name of the new user?");
            System.out.print("> ");
            username = Main.getScanner().nextLine();
            username = switch (username) {
                case "backend", "root", "admin", "administrator" -> {
                    Main.getLogger().logError("You can not create the user " + username + ".");
                    yield null;
                }
                default -> username;
            };
        } while (username == null);
        do {
            Main.getLogger().logInfo("What is the email address of the new user?");
            System.out.print("> ");
            email = Main.getScanner().nextLine();
        } while (email == null);
        do {
            Main.getLogger().logInfo("What is the group ID of the new user?");
            System.out.print("> ");
            groupid =Main.getScanner().nextLong();
        } while (groupid == null);

        passwordEncoder.encode(password);

        UserController userController = new UserController();
        userController.setUsername(username);
        userController.setEmail(email);
        userController.createUser(username, email, password, groupid);
    }

}
