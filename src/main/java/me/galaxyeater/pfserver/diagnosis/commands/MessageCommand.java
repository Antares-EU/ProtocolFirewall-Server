package me.galaxyeater.pfserver.diagnosis.commands;

import me.galaxyeater.pfserver.diagnosis.objects.DiagnosisCommand;

public class MessageCommand extends DiagnosisCommand {
    public MessageCommand(String command, String[] args) {
        super(command, args);
    }

    @Override
    public boolean execute() {

        if(args.length == 1) {
            operation.sendLine("Your must write a message!");
            return false;
        }

        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < args.length; i++) {
            if(i == 0) continue;

            builder.append(args[i]).append(" ");
        }

        String msg = builder.toString();
        operation.sendLine(" ");
        operation.sendLine("Message Received: " + msg);
        consoleMessage(msg);

        return true;
    }
}
