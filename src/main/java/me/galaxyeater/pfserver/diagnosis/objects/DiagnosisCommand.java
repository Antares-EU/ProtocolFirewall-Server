package me.galaxyeater.pfserver.diagnosis.objects;

import me.galaxyeater.pfserver.diagnosis.utils.Utils;

import java.io.BufferedWriter;

public abstract class DiagnosisCommand extends Utils {

    abstract public boolean execute();

    public final String command;
    public final String[] args;

    public DiagnosisCommand(String command, String[] args) {
        this.command = command;
        this.args = args;
    }

}
