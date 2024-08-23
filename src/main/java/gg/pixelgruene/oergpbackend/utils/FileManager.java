package gg.pixelgruene.oergpbackend.utils;

import lombok.Getter;

import java.io.*;
import java.util.*;

@Getter
public class FileManager {

    private final File file;

    public FileManager(String filePath) {
        file = new File(filePath);
    }

    public boolean exists() {
        return this.file.exists();
    }

    public String getPath() {
        return this.file.getPath();
    }

    public String getFilename() {
        return this.file.getName();
    }

    public boolean createFile() {
        try {
            return this.file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean renameFile(String newName) {
        return this.file.renameTo(new File(newName));
    }

    public boolean deleteFile() {
        return this.file.delete();
    }

    public boolean isFile() {
        return this.file.isFile();
    }

    public boolean isDirectory() {
        return this.file.isDirectory();
    }

    public boolean isHidden() {
        return this.file.isHidden();
    }

    public boolean canRead() {
        return this.file.canRead();
    }

    public boolean canWrite() {
        return this.file.canWrite();
    }

    public boolean canExecute() {
        return this.file.canExecute();
    }

    public long lastModified() {
        return this.file.lastModified();
    }

    public long length() {
        return this.file.length();
    }

    public void writeInNextFreeLine(String text) {
        try {
            FileWriter writer = new FileWriter(this.file, true);
            writer.write(text + "\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeOverrideAll(String text) {
        try {
            FileWriter writer = new FileWriter(this.file, false);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readLine(int line) {
        String str = "";
        try {
            java.util.Scanner scn = new java.util.Scanner(this.file);
            for (int i = 1; i <= line; i++) {
                if (scn.hasNext()) {
                    str = scn.nextLine();
                }
            }
            scn.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return str;
    }

    public ArrayList<String> readAll() {
        ArrayList<String> lines = new ArrayList<>();
        try {
            java.util.Scanner scn = new java.util.Scanner(this.file);
            while (scn.hasNext()) {
                lines.add(scn.nextLine());
            }
            scn.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }
}
