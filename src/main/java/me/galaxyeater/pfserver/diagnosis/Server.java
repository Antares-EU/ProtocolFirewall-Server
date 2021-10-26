package me.galaxyeater.pfserver.diagnosis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private DiagnosisOperation diagnosisOperation;

    private boolean authorized;
    private String authorizationKey;

    private int port;
    private ServerSocket serverSocket;
    private boolean connected = false;
    private boolean running = false;

    public Server(boolean startServer, int port, boolean authorized, String authorizationKey) {
        this.authorized = authorized;
        this.authorizationKey = authorizationKey;

        this.port = port;

        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(startServer) this.startServer();
    }

    public void startServer() {
        try {
            this.running = true;

            while (!this.serverSocket.isClosed()) {
                Socket socket = this.serverSocket.accept();
                if(connected) {
                    socket.close();
                    continue;
                }

                this.diagnosisOperation = new DiagnosisOperation(socket, this.authorized, this.authorizationKey);
                Thread thread = new Thread(this.diagnosisOperation);
                thread.start();

            }

        } catch (IOException ignored) {
            //do nothing
        } finally {
            this.running = false;
        }
    }

    public void stopServer() {
        try {
            if(this.diagnosisOperation != null) this.diagnosisOperation.terminate();
            if (serverSocket != null) serverSocket.close();
            this.running = false;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isConnected() {
        return this.connected;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public DiagnosisOperation getDiagnosisOperation() {
        return diagnosisOperation;
    }

    public int getPort() {
        return port;
    }
}
