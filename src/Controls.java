import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * En klass som endast består av en JPanel innehållandes två knappar för att styra paddles upp och ned.
 */

public class Controls extends JPanel implements ActionListener {

    private GameField gameField;

    private JButton up = new JButton("UPP");
    private JButton down = new JButton("NED");

    public Controls() {
        //Skapar en layout bestående av två rader och en kolonn.
        this.setLayout(new GridLayout(2,1));

        //Kopplar klassens actionlistener till knapparna.
        up.addActionListener(this);
        down.addActionListener(this);

        //Lägger till knapparna i denna JPanel.
        this.add(up);
        this.add(down);

    }
    @Override
    public void actionPerformed(ActionEvent e) {

        //Hämtar en representation av knappen som klickades på.
        AbstractButton button = (AbstractButton) e.getSource();

        if (GameField.getReady()) {
            if (button.equals(up)) {
                if (GameField.getPlayer1()) {
                    gameField.decP1();
                }
                else {
                    gameField.decP2();
                }
            } else {
                if (GameField.getPlayer1()) {
                    gameField.incP1();
                }
                else {
                    gameField.incP2();
                }
            }
            //Meddelar att spelplanen gamefield ska ritas om.
            gameField.repaint();
        }

    }

    /**
     * Kopplar klientens gamefield-objekt för att kunna kallas på från Controls.s
     * @param gameField Ett GameField-objekt.
     */
    public void connectGameField(GameField gameField) {
        this.gameField = gameField;
    }
}
