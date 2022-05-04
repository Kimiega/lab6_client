import client.Client;
import client.Environment;
import cmd.*;
import ioManager.ConsoleManager;
import ioManager.IReadable;
import ioManager.IWritable;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

//TODO Unit tests
//TODO javadoc
//TODO make UML diagram
//TODO logger
//TODO prepare to present
//TODO check warnings
public class Main {

    public static void main(String[] args){
        int CLIENT_PORT = 25569;
        try {
            ServerSocket servSoc = new ServerSocket(0);
            CLIENT_PORT = servSoc.getLocalPort();
        }
        catch (IOException ex){
            System.err.println("Не удалось найти свободный порт");
            System.exit(0);
        }

        IReadable in = ConsoleManager.getInstance();
        IWritable out = ConsoleManager.getInstance();
        HashMap<String, ICommand> commandMap = new HashMap<String, ICommand>();
        ExitCommand.register(commandMap);
        HelpCommand.register(commandMap);
        ExecuteScriptCommand.register(commandMap);
        ConnectCommand.register(commandMap);
        DisconnectCommand.register(commandMap);

        Environment env = new Environment(commandMap,in, out, false,CLIENT_PORT);

        Client client = new Client(env);
        client.init();

    }
}
