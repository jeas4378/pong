import java.io.IOException;
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

        new Server();
    }

    public Server() {

        try (ServerSocket server = new ServerSocket(DEFAULT_PORT)) {

            System.out.println("Väntar på anslutning");

            Socket connection1 = server.accept();
            ServerConnection p1 = new ServerConnection(connection1, this);
            Thread t1 = new Thread(p1);
            t1.start();

            System.out.println("Spelare 1 ansluten");

            Socket connection2 = server.accept();
            ServerConnection p2 = new ServerConnection(connection2, this);
            Thread t2 = new Thread(p2);
            t2.start();

            System.out.println("Spelare 2 ansluten");

            p1.send("true");
            p2.send("false");

            System.out.print("Server redo");

            while(true) {
                Thread.sleep(25);
            }

        }
        catch (IOException io) {
            System.err.println(io);
        }
        catch (InterruptedException ie) {
            System.err.println(ie);
        }

    }


    public synchronized void getMessage(String s) {
        System.out.println("tog emot meddelande");
        System.out.println(s);
        String[] data = s.split(",");
        if (data[0].equals("p1")){
            setP1_position(Integer.parseInt(data[1]));
            p2.send(this.sendP1());
        }
        else {
            System.out.println("Första värdet är okej");
            setP2_position(Integer.parseInt(data[1]));
            System.out.println("Har uppdaterat värdet" + Integer.toString(getP2_position()));
            p1.send("p2,"+Integer.toString(getP2_position()));
        }

    }
    public static synchronized void setP1_position(int pos) {
        p1_position = pos;
    }

    public static synchronized void setP2_position(int pos) {
        p2_position = pos;
    }

    public static int getP1_position(){
        return p1_position;
    }

    public static int getP2_position(){
        return p2_position;
    }

    public static synchronized void setBall_x(int pos){ ball_x = pos; }

    public static synchronized void setBall_y(int pos){
        ball_y = pos;
    }

    public static int getBall_x(){
        return ball_x;
    }

    public static int getBall_y(){return ball_y; }

    public String sendP1() {
        String s = "p1," + Integer.toString(getP1_position());
        return s;
    }

    public String sendP2() {
        String s = "p2," + Integer.toString(getP2_position());
        return s;
    }

    public String sendBall() {
        String s = "b,"+Integer.toString(getBall_x()) + ", " + Integer.toString(getBall_y());
        return s;
    }

}
