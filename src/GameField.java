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

        Boolean block = true;

        for (int y = 0; y < 501; y++) {
            for (int x = 0; x < 1001; x++) {
                Point p = new Point(x,y);
                if (block) {
                    lines.put(p,Color.BLACK);
                }
                else {
                    lines.put(p,Color.WHITE);
                }

                if ((block) && (y > 20) && (y < 460)) {
                    block = false;
                }
                else if ((y >= 460) && !(block)) {
                    block = true;
                }
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
