package gg.pixelgruene.oergpbackend.utils;

import lombok.Getter;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class InternalMethods {

    private final boolean secret =true;

    public String getOS() {
        return System.getProperty("os.name").toLowerCase();
    }

    public String getOSArch() {
        return System.getProperty("os.arch").toLowerCase();
    }

    public String getOSVersion() {
        return System.getProperty("os.version").toLowerCase();
    }

    public void printAllNetworkaddresses() {
        InetAddress[] ias;
        try {
            ias = InetAddress.getAllByName(getOwnerHostName());
            if (ias != null)
                for (InetAddress ia : ias) {
                    System.out.println(ia.getHostAddress());
                }
        } catch (UnknownHostException e) {
            System.err.println("unknown hostname");
        }
    }

    public String getOwnerHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getOwnerNetworkDeviceName() {
        try {
            NetworkInterface ni = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            if (ni != null)
                return ni.getDisplayName();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getOwnerMac() {
        try {
            NetworkInterface ni = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            byte[] hwa = null;
            if (ni != null) {
                hwa = ni.getHardwareAddress();
            } else {
                return;
            }
            if (hwa != null)  {
                StringBuilder mac = new StringBuilder();
                for (byte b : hwa) {
                    mac.append(String.format("%x:", b));
                }
                if (!mac.isEmpty() && !ni.isLoopback()) {
                    System.out.println(mac.toString().toLowerCase().substring(0, mac.length() - 1));
                }

            }
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public String getOwnerIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getInstallationPathJava() {
        return System.getProperty("java.home").toLowerCase();
    }

    public String getJavaVendorName() {
        return System.getProperty("java.vendor").toLowerCase();
    }

    public String getJavaVendorURL() {
        return System.getProperty("java.vendor.url").toLowerCase();
    }

    public String getJavaVersion() {
        return System.getProperty("java.version").toLowerCase();
    }

    public long getTotalMemory() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory();
    }

    public long getFreeMemory() {
        Runtime rt = Runtime.getRuntime();
        return rt.freeMemory();
    }

    public long getUsedMemory() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }

    public double getCPULoad() {
        double cpuload = ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage();
        return (Math.round(cpuload * 100));
    }

    public long getUptime() {
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        return rb.getUptime()/1000;
    }

    public double getTotalSaveStorage() {
        File file = new File("/");
        long totalSpace = file.getTotalSpace();
        return (double) (totalSpace/ 8/1024/1024/1024);
    }

    public double getFreeSaveStorage() {
        File file = new File("/");
        long freeSpace = file.getFreeSpace();
        return (double) (freeSpace/ 8/1024/1024/1024);
    }

    public double getUsedSaveStorage() {
        File file = new File("/");
        long usedSpace = file.getUsableSpace();
        return (double) (usedSpace/8/1024/1024/1024);
    }

    public String getTimeForStats(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String createPassword(int lengthKey, String smallLetters, String bigLetters, String specialssigns){
        String[] key = new String[lengthKey];
        String whole = smallLetters.toLowerCase() + bigLetters.toUpperCase() + specialssigns;
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < lengthKey; i++) {
            int random = (int) Math.floor((Math.random() * (whole.length() - 1) + 1));
            key[i] = String.valueOf(whole.charAt(random));
            password.append(key[i]);
        }
        return password.toString();
    }

}