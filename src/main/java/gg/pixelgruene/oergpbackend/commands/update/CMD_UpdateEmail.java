package gg.pixelgruene.oergpbackend.commands.update;

import gg.pixelgruene.oergpbackend.Main;
import gg.pixelgruene.oergpbackend.user.User;
import gg.pixelgruene.oergpbackend.user.UserController;

public class CMD_UpdateEmail {

    public static void onCommand(){
        User user = new User();
        UserController userController = new UserController();
        Main.getLogger().logInfo("What is the name of the user who should receive a new email?");
        String username;
        username = Main.getScanner().nextLine();
        Main.getLogger().logInfo("What is the new email address of the user?");
        String newEmail = Main.getScanner().nextLine();
        System.out.println(user.isUserExists(username));
        if(user.isUserExists(username)) {
            userController.updateEmail(username, newEmail);
        }else{
            Main.getLogger().logInfo("the User " + username + " don't exist.");
        }
    }

}
