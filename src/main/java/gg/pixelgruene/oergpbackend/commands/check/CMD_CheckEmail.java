package gg.pixelgruene.oergpbackend.commands.check;

import gg.pixelgruene.oergpbackend.Main;
import gg.pixelgruene.oergpbackend.user.UserController;

import java.sql.SQLException;

public class CMD_CheckEmail {

    public static void onCommand(){
        UserController user = new UserController();
        String being = "";
        Main.getLogger().logInfo("Which emailadress do you like to check?");
        System.out.print("> ");
        being = Main.getScanner().nextLine();
        try {
            Main.getLogger().logInfo(user.checkEmail(being));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
