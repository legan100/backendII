package gg.pixelgruene.oergpbackend.commands.remove;

import gg.pixelgruene.oergpbackend.Main;
import gg.pixelgruene.oergpbackend.user.GroupController;

public class CMD_RemoveGroup {

    public void onCommand(){
        String eingabe = "";
        GroupController groupController = new GroupController();
        Main.getLogger().logInfo("Which group do you want to delete?");
        eingabe = Main.getScanner().nextLine();
        if(!groupController.isGroupNameAvailable(eingabe)){
            groupController.removeGroupByName(eingabe);
        }else {
            Main.getLogger().logInfo("The Group " + eingabe + " isn't existing.");
        }
    }
}
