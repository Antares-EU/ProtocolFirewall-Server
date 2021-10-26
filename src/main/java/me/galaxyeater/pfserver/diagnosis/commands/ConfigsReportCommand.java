package me.galaxyeater.pfserver.diagnosis.commands;

import me.galaxyeater.pfserver.diagnosis.objects.DiagnosisCommand;
import me.galaxyeater.protocolfirewall.files.ConfigInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConfigsReportCommand extends DiagnosisCommand {
    public ConfigsReportCommand(String command, String[] args) {
        super(command, args);
    }

    @Override
    public boolean execute() {

        List<ConfigInterface> configsList = new ArrayList<>();
        configsList.add(configsAPI.getAntiAltConfig());
        configsList.add(configsAPI.getAntiClientConfig());
        configsList.add(configsAPI.getAntiExploiterConfig());
        configsList.add(configsAPI.getCommandsVanisherConfig());
        configsList.add(configsAPI.getIpAccountProtConfig());
        configsList.add(configsAPI.getIpBlockerConfig());
        configsList.add(configsAPI.getLogsConfig());
        configsList.add(configsAPI.getOpPlayersProtConfig());
        configsList.add(configsAPI.getPasswordsConfig());
        configsList.add(configsAPI.getPluginConfig());
        configsList.add(configsAPI.getProxyCheckerConfig());
        configsList.add(configsAPI.getServerOptimizerConfig());
        configsList.add(configsAPI.getSettingsConfig());
        configsList.add(configsAPI.getTabBlockerConfig());

        if(args.length == 1) {
            this.sendConfigsList(configsList);
            return false;
        }

        int position;
        try {
            position = Integer.parseInt(args[1]);
        } catch (Exception ignored) {
            operation.sendLine("Position not found!");
            return false;
        }

        ConfigInterface config = configsList.get(position);
        String fileName = config.getFileName();
        File file = config.getFile();

        Scanner reader;
        try {
            reader = new Scanner(file);
        } catch (FileNotFoundException e) {
            operation.sendLine("File not found!");
            return false;
        }

        boolean censor = false;
        if(fileName.contains("IpAccountProt") || fileName.contains("Settings")) censor = true;
        while(reader.hasNextLine()) {
            String data = reader.nextLine();

            if(censor) data = data.replaceAll("[0-9]", "x");

            operation.sendLine(data);
        }

        return true;
    }

    public void sendConfigsList(List<ConfigInterface> configsList) {
        int position = 0;
        operation.sendLine("======================[Available Configs]======================");
        for (ConfigInterface cf : configsList) {
            operation.sendLine("[" + position + "] " + cf.getFileName());
            position++;
        }
    }
}
