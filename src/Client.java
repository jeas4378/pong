import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * En klass som initierar spelklienten till Pong.
 */



public class Client extends JFrame {

    private static final int DEFAULT_PORT = 2000;
    private static final String DEFAULT_SERVER = "127.0.0.1";
    private Socket socket;

    public static void main(String[] args) {

        //Skapar ett Client-object. Args skickas med för att eventuellt i framtiden lägga till så att
        //användaren själv kan välja server och port.
        new Client(args);

    }

    public Client(String[] args) {

        //Initierar spelplanen och kontroller för att styra paddles.
        GameField gamefield = new GameField();
        Controls controls = new Controls();

        //För att kontrollerna ska kunna uppdatera paddles i spelplanen måste vi tillhandahålla
        //spelplansobjektet 'gamefields' till objektet 'controls'.
        controls.connectGameField(gamefield);

        //Initierar GUI.
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(2,1));

        this.add(gamefield, BorderLayout.CENTER);
        this.add(controls, BorderLayout.CENTER);

        this.setMinimumSize(new Dimension(800,1000));
        this.pack();

        this.setVisible(true);

        //Upprättar nätverks-koppling till servern med TCP och lägger detta i en separat tråd.
        try {
            socket = new Socket(DEFAULT_SERVER, DEFAULT_PORT);
            ClientConnection clientConnection = new ClientConnection(socket, gamefield);
            Thread network = new Thread(clientConnection);
            network.start();
        }
        catch (UnknownHostException ue){
            System.err.println(ue);
        }
        catch (IOException io) {
            System.err.println(io);
        }
    }

}
