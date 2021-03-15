import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends JFrame {

    private static final int DEFAULT_PORT = 2000;
    private static final String DEFAULT_SERVER = "127.0.0.1";
    private Socket socket;

    public static void main(String[] args) {

        new Client(args);

    }

    public Client(String[] args) {

        GameField gamefield = new GameField();
        Controls controls = new Controls();

        controls.connectGameField(gamefield);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(2,1));

        this.add(gamefield, BorderLayout.CENTER);
        this.add(controls, BorderLayout.CENTER);

        this.setMinimumSize(new Dimension(800,1000));
        this.pack();

        this.setVisible(true);

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
