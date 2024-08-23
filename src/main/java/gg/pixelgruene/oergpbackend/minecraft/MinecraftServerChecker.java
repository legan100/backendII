package gg.pixelgruene.oergpbackend.minecraft;

import gg.pixelgruene.oergpbackend.utils.FileManager;

public class MinecraftServerChecker {

    public void scanBungeeConfigForServers(){
        FileManager mc = new FileManager("mc.yml");
        FileManager bungeecord = new FileManager("/home/nico/Minecraftserver/Bungeecord/config.yml");
        for (int i = 0; i>= bungeecord.getFile().length();i++) {
            if(bungeecord.readLine(i++).contains("motd:")&& bungeecord.readLine(i+2).contains("address")&&bungeecord.readLine(i+3).contains("restricted")){
                mc.writeInNextFreeLine(bungeecord.readLine(i));
                mc.writeInNextFreeLine("\t"+bungeecord.readLine(i++));
                mc.writeInNextFreeLine("\t"+bungeecord.readLine(i+2));
                mc.writeInNextFreeLine("\t"+bungeecord.readLine(i+3));
            }
        }
    }

    public void createMinecraftserver(String name, String spielmodus, short port){
        FileManager mc = new FileManager("mc.yml");
        FileManager bungeecord = new FileManager("/home/nico/Minecraftserver/Bungeecord/config.yml");
        String motd = "§aOnline";
        mc.writeInNextFreeLine("  "+name);
        mc.writeInNextFreeLine("    motd: "+motd);
        mc.writeInNextFreeLine("    address: localhost:"+port);
        mc.writeInNextFreeLine("    restricted: false");
        bungeecord.writeInNextFreeLine("  "+name);
        bungeecord.writeInNextFreeLine("    motd: "+motd);
        bungeecord.writeInNextFreeLine("    address: localhost:"+port);
        bungeecord.writeInNextFreeLine("    restricted: false");
    }
}
