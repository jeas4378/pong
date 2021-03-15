import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static int p1_position = 0;
    private static int p2_position = 0;
    private static int ball_x = 0;
    private static int ball_y = 0;

    private static int ball_x_vector = 0;
    private static int ball_y_vector = 0;
    private ServerConnection p1;
    private ServerConnection p2;

    private static final int DEFAULT_PORT = 2000;

    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(DEFAULT_PORT)) {

            Socket connection1 = server.accept();
            ServerConnection p1 = new ServerConnection(connection1);
            Thread t1 = new Thread(p1);
            t1.start();

            Socket connection2 = server.accept();
            ServerConnection p2 = new ServerConnection(connection2);
            Thread t2 = new Thread(p2);
            t2.start();

            p1.send("true");
            p2.send("false");

        }
        catch (IOException io) {
            System.err.println(io);
        }

    }


    public static synchronized void getMessage(String s) {
        String[] data = s.split(",");
        if (data[0].equals("p1")){
            setP1_position(Integer.parseInt(data[1]));
        }
        else {
            setP2_position(Integer.parseInt(data[1]));
        }

    }
    public static synchronized void setP1_position(int pos) {
        p1_position = pos;
    }

    public static synchronized void setP2_position(int pos) {
        p2_position = pos;
    }

    public int getP1_position(){
        return p1_position;
    }

    public int getP2_position(){
        return p2_position;
    }

    public static synchronized void setBall_x(int pos){ ball_x = pos; }

    public static synchronized void setBall_y(int pos){
        ball_y = pos;
    }

    public int getBall_x(){
        return ball_x;
    }

    public int getBall_y(){return ball_y; }

    public String sendP1() {
        String s = "p1, " + Integer.toString(getP1_position());
        return s;
    }

    public String sendP2() {
        String s = "p2, " + Integer.toString(getP2_position());
        return s;
    }

    public String sendBall() {
        String s = "b, "+Integer.toString(getBall_x()) + ", " + Integer.toString(getBall_y());
        return s;
    }

}
