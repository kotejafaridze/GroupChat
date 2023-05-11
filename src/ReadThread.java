import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;

public class ReadThread implements  Runnable {

    public final int MAX_SIZE  = 1000;
    public MulticastSocket socket;
    public InetAddress group;
    public int port;

    public ReadThread(MulticastSocket socket, InetAddress group, int port) {
        this.socket = socket;
        this.group = group;
        this.port = port;
    }


    @Override
    public void run() {
        while(!Main.isFinished){
            byte[] buffer = new byte[MAX_SIZE];

            DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, group, port);
            try {
                socket.receive(datagram);
                String message = new String(buffer, 0, datagram.getLength(), StandardCharsets.UTF_8);
                if(!message.startsWith(Main.name)){
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.out.println(e);
            }


        }


    }
}
