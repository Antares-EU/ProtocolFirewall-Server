package me.galaxyeater.pfserver.diagnosis.utils;

import me.galaxyeater.pfserver.ProtocolFirewallServer;
import me.galaxyeater.pfserver.diagnosis.DiagnosisOperation;
import me.galaxyeater.protocolfirewallapi.configsapi.ConfigsAPI;
import me.galaxyeater.pfserver.diagnosis.Server;

public class Utils {

    public final ProtocolFirewallServer plugin = ProtocolFirewallServer.getPlugin(ProtocolFirewallServer.class);
    public final ConfigsAPI configsAPI = plugin.getConfigsAPI();
    public final Server server = plugin.getDiagnosis().getServer();
    public final DiagnosisOperation operation = server.getDiagnosisOperation();

    public void consoleMessage(String message) {
        plugin.getServer().getConsoleSender().sendMessage("[ProtocolFirewall Admin]: " + message);
    }

}
