package client;

import cmd.ICommand;
import ioManager.IReadable;
import ioManager.IWritable;

import java.net.InetAddress;
import java.util.HashMap;

public class Environment {
    private HashMap<String, ICommand> commandMap;
    private IReadable in;
    private IWritable out;
    private boolean running;
    private final boolean script;
    private int clientPort;
    private int serverPort;
    private InetAddress serverAddress;
    private boolean trasmitted;

    public Environment(HashMap<String, ICommand> commandMap, IReadable in, IWritable out, boolean isScript, int clientPort, int serverPort, InetAddress serverAddress){
        this.commandMap = commandMap;
        this.in = in;
        this.out = out;
        this.script = isScript;
        this.clientPort = clientPort;
        this.serverPort = serverPort;
        this.serverAddress = serverAddress;
        running = true;
    }

    public HashMap<String, ICommand> getCommandMap() {
        return commandMap;
    }
    public IReadable getIn(){
        return in;
    }
    public IWritable getOut(){
        return out;
    }
    public boolean isRunning(){
        return running;
    }
    public void turnOff(){
        running = false;
    }
    public boolean isScript(){
        return script;
    }

    public int getClientPort() {
        return clientPort;
    }

    public int getServerPort() {
        return serverPort;
    }

    public InetAddress getServerAddress() {
        return serverAddress;
    }

    public void setTrasmitted(boolean trasmitted) {
        this.trasmitted = trasmitted;
    }
    public boolean getTrasmitted() {
        return trasmitted;
    }
}
