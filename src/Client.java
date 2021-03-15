import javax.swing.*;
import java.awt.*;

public class Client extends JFrame {

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
    }

}
