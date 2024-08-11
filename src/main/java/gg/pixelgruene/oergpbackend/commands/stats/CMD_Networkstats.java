package gg.pixelgruene.oergpbackend.commands.stats;

import gg.pixelgruene.oergpbackend.Main;

public class CMD_Networkstats {

    public static void onCommand(){
        Main.getLogger().logInfo("---------[Networkstats]---------");
        Main.getLogger().logInfo("Hostname: \t\t\t\t" +  Main.getInternalMethods().getOwnerHostName());
        String ownerNetworkDeviceName = Main.getInternalMethods().getOwnerNetworkDeviceName();
        if(ownerNetworkDeviceName!= null){
            Main.getLogger().logInfo("IPv4-Adresse: \t\t\t" + Main.getInternalMethods().getOwnerIp());
            Main.getLogger().logInfo("Owner Network Device Name:\t\t"+Main.getInternalMethods().getOwnerNetworkDeviceName() + "\n");
        }else{
            Main.getLogger().logInfo("IPv4-Adresse: \t\t\t" + Main.getInternalMethods().getOwnerIp() + "\n\n");
        }
    }
}
