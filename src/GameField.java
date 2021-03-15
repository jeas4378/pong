import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GameField extends JPanel {

    private HashMap<Point,Color> lines = new HashMap<Point,Color>();
    private HashMap<Point,Color> p1 = new HashMap<Point,Color>();
    private HashMap<Point,Color> p2 = new HashMap<Point,Color>();
    private int p1_position = 0;
    private int p2_position = 0;

    public GameField() {

        default_lines();

    }

    private void default_lines() {

        Boolean block = false;
        Boolean middle = false;
        int height_min = 220;
        int height_max = 280;

        for (int y = 0; y < 501; y++) {
            for (int x = 0; x < 1001; x++) {
                Point p = new Point(x,y);
                if (block) {
                    lines.put(p,Color.BLACK);
                }
                else {
                    lines.put(p,Color.WHITE);
                }
                if ((x > 29) && (x < 40) && (y > height_min) && (y < height_max)) {
                    p1.put(p, Color.BLACK);
                }
                if ((x > 764) && (x < 776) && (y > height_min) && (y < height_max)) {
                    p2.put(p, Color.BLACK);
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

        Iterator player1 = p1.entrySet().iterator();

        while(player1.hasNext()) {
            Map.Entry pair_p1 = (Map.Entry) player1.next();
            Point p1_pos = (Point)pair_p1.getKey();
            Color p1_col = (Color)pair_p1.getValue();

            g.setColor(p1_col);
            g.fillRect(p1_pos.x + getP1_position(),p1_pos.y,1,1);
        }

        Iterator player2 = p2.entrySet().iterator();

        while(player2.hasNext()) {
            Map.Entry pair_p2 = (Map.Entry) player2.next();
            Point p2_pos = (Point)pair_p2.getKey();
            Color p2_col = (Color)pair_p2.getValue();

            g.setColor(p2_col);
            g.fillRect(p2_pos.x + getP2_position(),p2_pos.y,1,1);
        }
    }

    public void setP1(int pos) {
        this.p1_position = pos;
    }

    public void setP2(int pos) {
        this.p2_position = pos;
    }

    public int getP1_position() {
        return this.p1_position;
    }

    public int getP2_position(){
        return this.p2_position;
    }
}
