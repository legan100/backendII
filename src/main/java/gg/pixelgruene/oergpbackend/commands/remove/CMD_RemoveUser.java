package gg.pixelgruene.oergpbackend.commands.remove;

import gg.pixelgruene.oergpbackend.Main;
import gg.pixelgruene.oergpbackend.user.UserController;

public class CMD_RemoveUser {

    public static void onCommand(){
        String eingabe = "";
        UserController userController = new UserController();
        Main.getLogger().logInfo("Which user do you want to delete?");
        eingabe = Main.getScanner().nextLine();
        if(!userController.isUsernameAvailable(eingabe)){
            userController.removeUserByUsername(eingabe);
        }else {
            Main.getLogger().logInfo("The User " + eingabe + " isn't existing.");
        }
    }
}
