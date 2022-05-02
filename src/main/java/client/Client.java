package client;

import cmd.ICommand;
import connection.CommandPackage;
import connection.CommunicationUDP;
import connection.NetPackage;
import connection.NetResponse;
import exceptions.MaxSizeBufferException;
import ioManager.RequestElement;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class Client {
    private Environment env;

    public Client(Environment env) {
        this.env = env;
    }

    public void setCommands() throws MaxSizeBufferException, IOException{
        CommunicationUDP communication = new CommunicationUDP<CommandPackage[]>(env.getServerPort(),env.getServerAddress());
        NetPackage np = new NetPackage();
        np.setCmd("getCmds");
        communication.send(np);
        CommandPackage[] cmds;
        try {
            cmds = (CommandPackage[]) communication.receive();
        }
        catch (SocketTimeoutException ex){
            System.err.println("Время ожидания ответа от сервера вышло");
            throw new SocketTimeoutException();
        }
            for (CommandPackage cmd : cmds){
            ICommand a = new ICommand() {
                @Override
                public String getName() {
                    return cmd.getName();
                }

                @Override
                public String getDescription() {
                    return cmd.getDescription();
                }

                @Override //TODO is working?
                public void execute(Environment env, String arg, NetPackage netPackage) {
                    netPackage.setCmd(cmd.getName());
                    if (cmd.isHasArg())
                        netPackage.setArg(arg);
                    if (cmd.isHasObject())
                        netPackage.setCity(new RequestElement(env.getIn(),env.getOut(),false).readElement());
                }
            };
        }
    }

    public void init() {
        while (env.isRunning()) {
            //env.setTrasmitted(false);
            String s = env.getIn().readline();
            if (s == null)
                if (env.isScript())
                    break;
                else
                    continue;

            String cmd = "";
            String arg = "";

            String[] sArr = s.split("\\s");
            if (sArr.length == 1)
                cmd = sArr[0];
            if (sArr.length > 1) {
                cmd = sArr[0];
                arg = sArr[1];
            }

            NetPackage netPackage = new NetPackage();
            if (env.getCommandMap().containsKey(cmd)) {
                env.getCommandMap().get(cmd).execute(env, arg, netPackage);
                CommunicationUDP communication = new CommunicationUDP<NetResponse>(env.getServerPort(),env.getServerAddress());
                try {
                    communication.send(netPackage);
                } catch (MaxSizeBufferException e) {
                    System.err.println("Размер пакета слишком велик\nПакет не был отправлен");
                    continue;
                } catch (IOException e) {
                    System.err.println("Ошибка отправки");
                    continue;
                }
                try {
                while (true){
                        NetResponse response = (NetResponse) communication.receive();
                        if (response == null){
                            System.err.println("Ошибка во время получения пакета");
                            break;
                        }
                        env.getOut().writeln(response.getMessage());
                        if (response.isFinish())
                            break;
                }
                } catch (SocketTimeoutException e) {
                    System.err.println("Время ожидания ответа от сервера вышло");
                    break;
                }
            }
            else {
                env.getOut().writeln("Command not found");
            }
        }
    }
}

