package me.galaxyeater.pfserver.diagnosis;

import com.sun.istack.internal.NotNull;

import javax.annotation.Nullable;

public class Diagnosis {

    private Server server;
    private Thread thread;

    private int port;
    private boolean authorized;
    private String authorizationKey;

    public Diagnosis(@NotNull int port, boolean authorized, @Nullable String authorizationKey) {
        this.port = port;
        this.authorized = authorized;
        this.authorizationKey = authorizationKey;
    }

    public boolean startServer() {
        if(this.server != null) {
            if(this.server.isRunning()) return false;
        }

        this.server = new Server(false, this.port, this.authorized, this.authorizationKey == null ? "none" : this.authorizationKey);

        thread = new Thread(() -> {
            this.server.startServer();
        });
        thread.start();

        return true;
    }

    public void terminate() {
        if(this.server != null) this.server.stopServer();
        if(this.thread != null) this.thread.interrupt();
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public void setAuthorizationKey(String authorizationKey) {
        this.authorizationKey = authorizationKey;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Server getServer() {
        return server;
    }
}
