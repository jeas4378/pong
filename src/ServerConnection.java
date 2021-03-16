import java.io.*;
import java.net.Socket;

/**
 * En klass som håller i nätverksanslutningarna till servern.
 */

public class ServerConnection implements Runnable {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Server server;


    public ServerConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;

        //Skapar en reader och en writer för att kunna läsa från och skriva till klienterna.
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

        //Väntar kontinuerligt på meddelanden från klienterna.
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

    //Sänder meddelande till klienten.
    public synchronized void send(String s) {
        System.out.println("Sänder meddelande");
        this.writer.println(s);
    }
}
