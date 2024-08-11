package gg.pixelgruene.oergpbackend.commands;

import gg.pixelgruene.oergpbackend.Main;
import gg.pixelgruene.oergpbackend.user.User;

public class CMD_GetUser {

    public static void onCommand(){
        User user = new User();
        Main.getLogger().logInfo("Which user would you like to receive information about?");
        String eingabe = Main.getScanner().nextLine();

        Main.getLogger().logInfo("Email: " + user.getEmail(eingabe));
        Main.getLogger().logInfo("UserID: " + user.getUserID(eingabe));
        Main.getLogger().logInfo("Groupname" + user.getGroup().getGroupname());
        Main.getLogger().logInfo("username  " + user.getUsername());
    }
}
