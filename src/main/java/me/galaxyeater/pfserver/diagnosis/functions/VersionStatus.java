package me.galaxyeater.pfserver.diagnosis.functions;

import me.galaxyeater.pfserver.diagnosis.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class VersionStatus extends Utils {

    private List<String> lines;

    public VersionStatus() {
        lines = new ArrayList<>();

        lines.add("Version Status: ");
        lines.add(" Server: " + plugin.getServer().getVersion());
        lines.add(" ProtocolFirewall: " + plugin.getProtocolFirewallAPI().getMain().getDescription().getVersion());
        lines.add(" ProtocolFirewall-Server: " + plugin.getDescription().getVersion());
        lines.add(" Java: " + System.getProperty("java.version"));
        lines.add(" JVM: " + System.getProperty("java.vm.version"));
        lines.add(" JRE: " + System.getProperty("java.specification.version"));
        lines.add(" OS: " + System.getProperty("os.name") + " - " + System.getProperty("os.version") + " - " + System.getProperty("os.arch"));
    }

    public List<String> get() {
        return this.lines;
    }

}
