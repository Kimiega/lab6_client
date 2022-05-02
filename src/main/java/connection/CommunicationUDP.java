package connection;

import java.io.IOException;
import java.net.*;

import exceptions.MaxSizeBufferException;
import org.springframework.util.SerializationUtils;
public class CommunicationUDP<T> {
    private final int PACKET_MAX_LENGTH = 1024;
    private DatagramPacket packet;
    private int serverPort;
    private InetAddress serverIPAddress;

    public CommunicationUDP(int serverPort, InetAddress serverIPAddress){
        this.serverPort = serverPort;
        this.serverIPAddress = serverIPAddress;
    }
    public void send(NetPackage netPackage) throws MaxSizeBufferException,IOException {
        byte[] msgBuffer = SerializationUtils.serialize(netPackage);
        int length = msgBuffer.length;
        if (length > PACKET_MAX_LENGTH) {
            throw new MaxSizeBufferException();
        }
        packet = new DatagramPacket(msgBuffer, length);
        packet.setAddress(serverIPAddress);
        packet.setPort(serverPort);
        try (DatagramSocket udpSocket = new DatagramSocket()) {
            udpSocket.send(packet);}
    }

    public T receive() throws SocketTimeoutException{
        try (DatagramSocket udpSocket = new DatagramSocket()) {
            udpSocket.setSoTimeout(5000);
            udpSocket.receive(packet);
           return (T) SerializationUtils.deserialize(packet.getData());
        }
        catch (SocketTimeoutException ex){
            throw new SocketTimeoutException();
        }
        catch (IOException ex){
            return null;
        }
    }
}


//public class CommunicationUDP<T> {
//    private static int PACKET_MAX_LENGTH = 1024;
//    public static byte[] joinTwoArrays(byte[] a, byte[] b) {
//        Byte[] aB = ArrayUtils.toObject(a);
//    }
//        return  Stream.concat(Arrays.stream(a), Arrays.stream(b)).toArray(byte[]::new);
//
//
//        public static NetResponse  sendAndReceive(NetPackage netPackage, int serverPort, InetAddress serverIPAddress) throws IOException {
//
//        byte[] msgBuffer = SerializationUtils.serialize(netPackage);
//
//        int length = msgBuffer.length;
//        if (length > PACKET_MAX_LENGTH) {
//            length = PACKET_MAX_LENGTH;
//        }
//        DatagramPacket packet = new DatagramPacket(msgBuffer, length);
//        packet.setAddress(serverIPAddress);
//        packet.setPort(serverPort);
//        try(DatagramSocket udpSocket = new DatagramSocket()){
//        udpSocket.send(packet);
//        udpSocket.setSoTimeout(5000);
//        msgBuffer = new byte[PACKET_MAX_LENGTH];
//        packet = new DatagramPacket(msgBuffer, msgBuffer.length);
//        udpSocket.receive(packet);
//        }
//        catch (SocketTimeoutException e){
//            System.err.println("Время ожидания ответа от сервера истекло");
//            return null;
//        }
//        catch (IOException e) {
//            return null;
//        }
//        return (T) SerializationUtils.deserialize(packet.getData());
//    }

//    public static NetResponse receive(int clientPort) {
//        byte[] msgBuffer = new byte[PACKET_MAX_LENGTH];
//
//        DatagramPacket packet = new DatagramPacket(msgBuffer, msgBuffer.length);
//        try(DatagramSocket udpSocket = new DatagramSocket()){
//            udpSocket.setSoTimeout(5000);
//            udpSocket.receive(packet);}
//        catch (SocketTimeoutException e){
//            System.err.println("Время ожидания ответа от сервера истекло");
//            return null;
//        }
//        catch (IOException e){
//            System.err.println("Ошибка чтения пакета");
//            return null;
//        }
//        return (NetResponse) SerializationUtils.deserialize(packet.getData());
//    }

