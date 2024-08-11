package gg.pixelgruene.oergpbackend.commands.stats;

import gg.pixelgruene.oergpbackend.Main;

public class CMD_Serverstats {

    public static void onCommand(){
        Main.getLogger().logInfo("---------------[ServerInfo]---------------");
        //BackendApplication.getLogger().logInfo("Port: \t\t\t\t" + Main.getServerAPI().getServerPort());
        Main.getLogger().logInfo("Uptime: \t\t\t\t" + Main.getInternalMethods().getUptime()/1000 + " Sekunden");
        Main.getLogger().logInfo("System-Time: \t\t\t\t" + Main.getInternalMethods().getTimeForStats());
        Main.getLogger().logInfo("Operating system: \t\t\t" + Main.getInternalMethods().getOS());
        Main.getLogger().logInfo("Operating system architecture: \t" + Main.getInternalMethods().getOSArch());
        Main.getLogger().logInfo("Operating system version: \t\t" + Main.getInternalMethods().getOSVersion());
        Main.getLogger().logInfo("Used RAM: \t\t\t\t" + Main.getInternalMethods().getUsedMemory());
        Main.getLogger().logInfo("Free RAM: \t\t\t\t" + Main.getInternalMethods().getFreeMemory());
        Main.getLogger().logInfo("Total RAM: \t\t\t\t" + Main.getInternalMethods().getTotalMemory() + " GB\n");
        Main.getLogger().logInfo("Streamermodus: \t\t\t" + Main.getInternalMethods().isSecret() + "\n\n");

        Main.getLogger().logInfo( "---------------[Saveinfo]---------------");
        Main.getLogger().logInfo("Freespace: \t\t\t\t" + Main.getInternalMethods().getFreeSaveStorage());
        Main.getLogger().logInfo("Usespace: \t\t\t\t" + Main.getInternalMethods().getUsedSaveStorage());
        Main.getLogger().logInfo("Totalspace: \t\t\t\t" + Main.getInternalMethods().getTotalSaveStorage() + "\n");
    }

}
