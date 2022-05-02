import client.Client;
import client.Environment;
import cmd.*;
import exceptions.MaxSizeBufferException;
import ioManager.ConsoleManager;
import ioManager.IReadable;
import ioManager.IWritable;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

//TODO StreamAPI ?
//TODO Unit tests
//TODO javadoc
//TODO make UML diagram

public class Main {

    public static void main(String[] args){
        int port;
        //address

        IReadable in = ConsoleManager.getInstance();
        IWritable out = ConsoleManager.getInstance();
        HashMap<String, ICommand> commandMap = new HashMap<String, ICommand>();
        ExitCommand.register(commandMap);
        HelpCommand.register(commandMap);
        ExecuteScriptCommand.register(commandMap);

        Environment env = null;
        try {
            env = new Environment(commandMap,in, out, false,25569,25570, InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            System.err.println("Ошибка в адресе");
            System.err.println("Завершение работы клиента");
            System.exit(0);
        }
        Client client = new Client(env);
        try {
            client.setCommands();
        } catch (MaxSizeBufferException | IOException e) {
            System.err.println("Не удалось получить команды от сервера\nРабота клиента завершена");
            System.exit(0);
        }
        client.init();

    }
}
