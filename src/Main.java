import javax.xml.crypto.Data;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static boolean isFinished = false;
    public static String TERMINATE = "exit";
    public static String name;
    public static void main(String[] args) {
        try {
            InetAddress group = InetAddress.getByName("224.0.0.0");
            int port = 3001;

            Scanner scanner = new Scanner(System.in);
            System.out.println("enter your name - ");
            name = scanner.nextLine();


            MulticastSocket socket = new MulticastSocket(port);
            socket.setTimeToLive(0);
            socket.joinGroup(group);

            Thread thread = new Thread(new ReadThread(socket, group, port));

            thread.start();
            System.out.println("you can start chatting...");


            while(true){
                String message  = scanner.nextLine();
                if(message.equals(TERMINATE)){
                    isFinished = true;
                    socket.leaveGroup(group);
                    socket.close();
                    break;
                }
                message = name + " : " + message;
                byte[] buffer = message.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, group, port);
                socket.send(datagramPacket);

            }


        } catch (Exception e) {
            System.out.println(e);;
        }
    }
}