import java.io.*;
import java.net.Socket;

public class ClientConnection implements Runnable{

    private static final int DEFAULT_PORT = 2000;
    private static final String DEFAULT_SERVER = "127.0.0.1";
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientConnection(Socket socket){
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
                GameField.recieveMessage(message);
            }
        }
        catch (IOException io) {
            System.err.println(io);
        }

    }

    public void send(String s){
        writer.println(s);
    }
}
