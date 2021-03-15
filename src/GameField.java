import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GameField extends JPanel {

    private HashMap<Point,Color> lines = new HashMap<Point,Color>();

    public GameField() {

        default_lines();

    }

    private void default_lines() {

        Boolean block = false;
        Boolean middle = false;

        for (int y = 0; y < 501; y++) {
            for (int x = 0; x < 1001; x++) {
                Point p = new Point(x,y);
                if (block) {
                    lines.put(p,Color.BLACK);
                }
                else {
                    lines.put(p,Color.WHITE);
                }

                if ((y < 20) || (y > 460)) {
                    block = true;
                }
                else if ((x >= 390) && (x <= 410)) {
                    if (middle) {
                        block = true;
                    }
                    else {
                        block = false;
                    }
                }
                else {
                    block = false;
                }
            }
            if ((y-10) % 20 == 0) {
                middle = middle ? false : true;
            }

        }

        repaint();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Iterator i = lines.entrySet().iterator();

        while(i.hasNext()) {

            Map.Entry pair = (Map.Entry) i.next();
            Point p = (Point)pair.getKey();
            Color c = (Color)pair.getValue();

            g.setColor(c);
            g.fillRect(p.x, p.y, 1, 1);
        }
    }
}
