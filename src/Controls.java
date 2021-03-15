import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controls extends JPanel implements ActionListener {

    private JButton up = new JButton("UPP");
    private JButton down = new JButton("NED");

    public Controls() {
        this.setLayout(new GridLayout(2,1));

        up.addActionListener(this);
        down.addActionListener(this);

        this.add(up);
        this.add(down);

    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
