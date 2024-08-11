package gg.pixelgruene.oergpbackend.commands.stats;

import gg.pixelgruene.oergpbackend.Main;

public class CMD_Javainfo {

    public static void onCommand() {
        Main.getLogger().logInfo("---------------[Javainfo]---------------");
        Main.getLogger().logInfo( "Javaversion for the JRE: " + Main.getInternalMethods().getJavaVersion());
        Main.getLogger().logInfo( "Vendorname for the JRE: " + Main.getInternalMethods().getJavaVendorName());
        Main.getLogger().logInfo( "Java installation directory for the JRE: " + Main.getInternalMethods().getInstallationPathJava() + "\n");
    }
}
