package me.galaxyeater.pfserver.diagnosis.commands;

import me.galaxyeater.pfserver.diagnosis.objects.DiagnosisCommand;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

public class HelpCommand extends DiagnosisCommand {

    public HelpCommand(String command, String[] args) {
        super(command, args);
    }

    @Override
    public boolean execute() {

        operation.sendLine(" ");

        List<String> msg = new ArrayList<>();
        msg.add("===========================[ProtocolFirewall]===========================");
        msg.add("!help");
        msg.add("!version");
        msg.add("!message <message>");
        msg.add("!config <configPosition>");
        msg.add("!peak <filePath>");

        for (String line : msg) {
            operation.sendLine(line);
        }

        return true;
    }
}
