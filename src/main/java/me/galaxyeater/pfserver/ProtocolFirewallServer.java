package me.galaxyeater.pfserver;

import me.galaxyeater.pfserver.diagnosis.Diagnosis;
import me.galaxyeater.protocolfirewallapi.ProtocolFirewallAPI;
import me.galaxyeater.protocolfirewallapi.configsapi.ConfigsAPI;
import org.bukkit.plugin.java.JavaPlugin;

public class ProtocolFirewallServer extends JavaPlugin {

    private ProtocolFirewallAPI protocolFirewallAPI;
    private ConfigsAPI configsAPI;
    private Diagnosis diagnosis;

    @Override
    public void onEnable() {
        this.protocolFirewallAPI = new ProtocolFirewallAPI();
        this.protocolFirewallAPI.register();

        this.configsAPI = protocolFirewallAPI.getConfigsAPI();

        this.diagnosis = new Diagnosis(40379, false, null);
    }

    @Override
    public void onDisable() {
        this.diagnosis.terminate();
    }

    public ProtocolFirewallAPI getProtocolFirewallAPI() {
        return protocolFirewallAPI;
    }

    public ConfigsAPI getConfigsAPI() {
        return configsAPI;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }
}
