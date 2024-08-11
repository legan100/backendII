package gg.pixelgruene.oergpbackend.commands;

import gg.pixelgruene.oergpbackend.Main;

public class CMD_Help {

    public static void onCommand(){
        Main.getLogger().logInfo("----------[Help]----------");
        Main.getLogger().logInfo("clear \t\tclear the console");
        Main.getLogger().logInfo("createuser\tcreate new ACP-User");
        Main.getLogger().logInfo("help\t\tShow all commands");
        Main.getLogger().logInfo("stop\t\tStop the backend");
    }

}
