import java.io.*;
import java.net.Socket;

/**
 * En klass som bara håller i anslutningen till servern.
 */

public class ClientConnection implements Runnable{

    private Socket socket;
    private BufferedReader reader;
    private static PrintWriter writer;
    private GameField gamefield;

    public ClientConnection(Socket socket, GameField gamefield){
        this.gamefield = gamefield;
        this.socket = socket;

        //Skapar en reader och en writer för att kunna läsa och skriva till servern.
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

        //Läser kontinuerligt från servern. Så fort ett meddelande tas emot skickas det till metoden 'receiveMessage'
        //i GameField-objektet.
        try {
            while ((message = reader.readLine()) != null) {
                gamefield.recieveMessage(message);
            }
        }
        catch (IOException io) {
            System.err.println(io);
        }

    }

    /**
     * Skickar meddelanden från klienten till servern.
     * @param s En String med information att skicka till servern.
     */
    public static synchronized void send(String s){
        System.out.println("Klient sänder meddelande");
        writer.println(s);
    }
}
