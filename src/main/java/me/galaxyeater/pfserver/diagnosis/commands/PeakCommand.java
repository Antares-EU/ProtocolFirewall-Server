package me.galaxyeater.pfserver.diagnosis.commands;

import me.galaxyeater.pfserver.diagnosis.objects.DiagnosisCommand;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class PeakCommand extends DiagnosisCommand {
    public PeakCommand(String command, String[] args) {
        super(command, args);
    }

    @Override
    public boolean execute() {

        File mainFolder = plugin.getProtocolFirewallAPI().getMain().getDataFolder();

        if(!mainFolder.exists()) {
            operation.sendLine("Main folder doesn't exist");
            return false;
        }

        if(!mainFolder.isDirectory()) {
            operation.sendLine("The main folder is not a folder ?!");
            return false;
        }

        if(mainFolder.listFiles() == null) {
            operation.sendLine("Files list returned null, it's probably empty");
            return false;
        }

        if(args.length == 1) {
            this.sendFilesList(this.getReadableFiles(mainFolder, null));
            return false;
        }

        String path = args[1];
        File target = new File(mainFolder, path);

        if(path.contains("..")) {
            operation.sendLine("You are not allowed to do that");
            return false;
        }

        if(!target.exists()) {
            operation.sendLine("File doesn't exist");
            return false;
        }

        if(target.isDirectory()) {
            this.sendFilesList(this.getReadableFiles(mainFolder, path));
            return true;
        }

        if(this.isNotReadable(target)) {
            operation.sendLine("This file is not readable");
            return false;
        }

        Scanner reader;

        try {
            reader = new Scanner(target);
        } catch (FileNotFoundException e) {
            operation.sendLine("File not found (Exc)");
            return false;
        }

        operation.sendLine("==================[" + target.getName() + "]==================");

        while(reader.hasNextLine()) {
            String line = reader.nextLine().replaceAll("[0-9]", "x");

            operation.sendLine(line);
        }

        return true;
    }

    public void sendFilesList(List<File> files) {
        for (File file : files) {
            operation.sendLine("[" + file.getName() + "] " + file.getAbsolutePath());
        }
    }

    public boolean isNotReadable(File file) {
        String name = file.getName();

        if(file.isDirectory()) return false;
        return !file.isFile() || !name.contains(".yml") || name.contains(".zip") || name.contains("MySQL");
    }

    public List<File> getReadableFiles(File mainFolder, @Nullable String path) {
        List<File> readableFiles = new ArrayList<>();

        if(path == null) {
            for (File file : Objects.requireNonNull(mainFolder.listFiles())) {
                if(this.isNotReadable(file)) continue;

                readableFiles.add(file);
            }
            return readableFiles;
        }

        File target = new File(mainFolder, path);
        if(!target.exists()) return null;

        for (File file : Objects.requireNonNull(target.listFiles())) {
            if(this.isNotReadable(file)) continue;

            readableFiles.add(file);
        }

        return readableFiles;
    }
}
