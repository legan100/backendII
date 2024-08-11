package gg.pixelgruene.oergpbackend.commands.check;

import gg.pixelgruene.oergpbackend.Main;
import gg.pixelgruene.oergpbackend.user.UserController;

import java.sql.SQLException;

public class CMD_CheckUsername {

    public static void onCommand(){
        String eingabe = "";
        Main.getLogger().logInfo("Which username do you like to check?");
        System.out.print("> ");
        Main.getScanner().nextLine();
        UserController user = new UserController();
        try {
            Main.getLogger().logInfo(user.checkUsername(eingabe));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
