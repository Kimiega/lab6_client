package connection;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

import com.google.gson.stream.JsonToken;
import exceptions.MaxSizeBufferException;
import org.springframework.util.SerializationUtils;
public class CommunicationUDP {
    private final int PACKET_MAX_LENGTH = 1400;
    private DatagramSocket udpSocket;
    private byte[] msgBuffer;
    private int serverPort;
    private InetAddress serverIPAddress;

    public CommunicationUDP(int serverPort, InetAddress serverIPAddress) throws SocketException{
        this.serverPort = serverPort;
        this.serverIPAddress = serverIPAddress;
        udpSocket = new DatagramSocket();
    }
    public void send(NetPackage netPackage) throws MaxSizeBufferException,IOException {
        msgBuffer = SerializationUtils.serialize(netPackage);
        int length = msgBuffer.length;
        if (length > PACKET_MAX_LENGTH) {
            System.err.println("packet len = " + length);
            throw new MaxSizeBufferException();
        }
        DatagramPacket packet = new DatagramPacket(msgBuffer, length);
        packet.setAddress(serverIPAddress);
        packet.setPort(serverPort);
        udpSocket.send(packet);
//        System.out.println("len of sent packet = " + msgBuffer.length);
//        for (byte b : msgBuffer)
//            System.out.print(b);
//        System.out.println();
//        System.out.println("Packet sent");
    }

    public <T> T receive() throws IOException,IllegalArgumentException{
        try{
            msgBuffer = new byte[PACKET_MAX_LENGTH];
            DatagramPacket packet = new DatagramPacket(msgBuffer, PACKET_MAX_LENGTH);
            packet.setAddress(serverIPAddress);
            packet.setPort(serverPort);
            udpSocket.setSoTimeout(5000);
            udpSocket.receive(packet);
//            System.out.println("Packet received");
//            System.out.println("len of received packet = " + packet.getLength());
//            msgBuffer = Arrays.copyOfRange(packet.getData(),0, packet.getLength());
//            for (byte b : msgBuffer)
//                System.out.print(b);
//            System.out.println();
            T response = (T)SerializationUtils.deserialize(packet.getData());
            return response;
        }
        catch (SocketTimeoutException ex){
            throw new SocketTimeoutException();
        }
        catch (IOException ex){
            throw new IOException();
        }
        catch (IllegalArgumentException ex){
            ex.printStackTrace();
            System.err.println("Object couldn't be deserialised");
           throw new IllegalArgumentException();
        }
    }
    public void close(){
        udpSocket.close();
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

