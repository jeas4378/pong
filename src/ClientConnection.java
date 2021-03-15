import java.io.*;
import java.net.Socket;

public class ClientConnection implements Runnable{

    private Socket socket;
    private BufferedReader reader;
    private static PrintWriter writer;
    private GameField gamefield;

    public ClientConnection(Socket socket, GameField gamefield){
        this.gamefield = gamefield;
        this.socket = socket;

        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),
                    "ISO_8859_1"));
            this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),
                    "ISO_8859_1"), true);
        }
        catch (IOException io) {
            System.err.println(io);
        }
    }


    @Override
    public void run() {

        String message = null;

        try {
            while ((message = reader.readLine()) != null) {
                gamefield.recieveMessage(message);
            }
        }
        catch (IOException io) {
            System.err.println(io);
        }

    }

    public static synchronized void send(String s){
        System.out.println("Klient sänder meddelande");
        writer.println(s);
    }
}
