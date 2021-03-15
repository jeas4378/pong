import java.io.*;
import java.net.Socket;

public class ServerConnection implements Runnable {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Server server;


    public ServerConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;

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
                server.getMessage(message);
            }
        }
        catch(IOException io){
            System.err.println(io);
            System.exit(1);
        }

    }

    public synchronized void send(String s) {
        System.out.println("Sänder meddelande");
        this.writer.println(s);
    }
}
