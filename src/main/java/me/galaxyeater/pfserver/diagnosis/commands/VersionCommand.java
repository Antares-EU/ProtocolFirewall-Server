package me.galaxyeater.pfserver.diagnosis.commands;

import me.galaxyeater.pfserver.diagnosis.functions.VersionStatus;
import me.galaxyeater.pfserver.diagnosis.objects.DiagnosisCommand;

public class VersionCommand extends DiagnosisCommand {
    public VersionCommand(String command, String[] args) {
        super(command, args);
    }

    @Override
    public boolean execute() {

        operation.sendLine(" ");
        VersionStatus versionStatus = new VersionStatus();
        for (String line : versionStatus.get()) {
            operation.sendLine(line);
        }

        return true;
    }
}
