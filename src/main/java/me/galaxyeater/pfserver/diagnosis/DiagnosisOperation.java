package me.galaxyeater.pfserver.diagnosis;

import me.galaxyeater.pfserver.diagnosis.commands.*;
import me.galaxyeater.pfserver.diagnosis.utils.Encryption;
import me.galaxyeater.pfserver.diagnosis.utils.Utils;

import java.io.*;
import java.net.Socket;

public class DiagnosisOperation extends Utils implements Runnable  {

    private boolean authorized;
    private String authorizationKey;

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public DiagnosisOperation(Socket socket, boolean authorized, String authorizationKey) {
        this.authorized = authorized;
        this.authorizationKey = authorizationKey;
        try {
            this.socket = socket;
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            server.setConnected(true);

            super.consoleMessage("Successfully Connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendLine(String line) {
        try {
            this.writer.write("[Server]: " + line);
            this.writer.newLine();
            this.writer.flush();
        } catch (IOException ex) {
            this.terminate();
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        String line;

        while(socket.isConnected()) {
            try {
                line = this.reader.readLine();

                if(line == null) line = "null";

                if(line.replace(" ", "").equals("null")) break;

                if(this.isCommand(line)) {
                    this.commandExecutor(line);
                } else {
                    sendLine("Unknown Command, try to use \"!help\" in order to see the commands list");
                }

            } catch (IOException ignored) {
                this.terminate();
                break;
            }
        }
        if(server.isConnected()) {
            server.setConnected(false);
        }

        super.consoleMessage("Disconnected");
    }

    public void terminate() {
        try {
            if(writer != null) writer.close();
            if(reader != null) reader.close();
            if(socket != null) socket.close();

            if(server.isConnected()) {
                server.setConnected(false);
            }

            this.authorized = false;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isCommand(String line) {
        return line.toLowerCase().startsWith("!");
    }

    public void commandExecutor(String line) {
        String pureLine = line;
        Encryption encryption = new Encryption();
        line = line.toLowerCase();
        String[] args = line.split(" ");
        String command = args[0].replace("!", "");

        if(!this.authorized) {
            if(!command.equals("login")) {
                sendLine("You must authorize yourself first, !login <key>");
                return;
            } else {
                String password = args[1];
                if(encryption.encrypt(password).equals(this.authorizationKey)) {
                    sendLine("Successfully Authorized!");
                    super.consoleMessage("Successful Authorization");
                    this.authorized = true;
                    return;
                }
                sendLine("Wrong Password!");
                return;
            }
        }

        switch (command) {
            case "help": {
                new HelpCommand(command, args).execute();
                break;
            }
            case "version": {
                new VersionCommand(command, args).execute();
                break;
            }
            case "message": {
                new MessageCommand(command, args).execute();
                break;
            }
            case "config": {
                new ConfigsReportCommand(command, args).execute();
                break;
            }
            case "peak": {
                new PeakCommand(command, pureLine.split(" ")).execute();
                break;
            }
            default: {
                sendLine("Unknown Command, try to use \"!help\" in order to see the commands list");
                break;
            }
        }

    }
}
