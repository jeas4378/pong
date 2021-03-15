import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GameField extends JPanel {

    private HashMap<Point,Color> lines = new HashMap<Point,Color>();
    private HashMap<Point,Color> p1 = new HashMap<Point,Color>();
    private HashMap<Point,Color> p2 = new HashMap<Point,Color>();
    private HashMap<Point,Color> ball = new HashMap<Point,Color>();
    private int p1_position = 0;
    private int p2_position = 0;
    private int moveSpeed = 10;
    private int ball_x = 0;
    private int ball_y = 0;

    public GameField() {

        createGraphics();

    }

    private void createGraphics() {

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

                //Skapar spelare 1s paddel.
                if ((x > 29) && (x < 40) && (y > height_min) && (y < height_max)) {
                    p1.put(p, Color.BLACK);
                }
                //Skapar spelare 2s paddel.
                if ((x > 764) && (x < 776) && (y > height_min) && (y < height_max)) {
                    p2.put(p, Color.BLACK);
                }
                //Skapar bollen.
                if ((x > 390) && (x < 410) && (y > 230) && (y < 250)) {
                    ball.put(p, Color.GRAY);
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

    public synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Renderar spelplanen.
        Iterator i = lines.entrySet().iterator();
        while(i.hasNext()) {

            Map.Entry pair = (Map.Entry) i.next();
            Point p = (Point)pair.getKey();
            Color c = (Color)pair.getValue();

            g.setColor(c);
            g.fillRect(p.x, p.y, 1, 1);
        }

        //Renderar spelare 1s paddel
        Iterator player1 = p1.entrySet().iterator();
        while(player1.hasNext()) {
            Map.Entry pair_p1 = (Map.Entry) player1.next();
            Point p1_pos = (Point)pair_p1.getKey();
            Color p1_col = (Color)pair_p1.getValue();

            g.setColor(p1_col);
            g.fillRect(p1_pos.x,p1_pos.y + getP1_position(),1,1);
        }

        //Renderar spelare 2s paddel
        Iterator player2 = p2.entrySet().iterator();
        while(player2.hasNext()) {
            Map.Entry pair_p2 = (Map.Entry) player2.next();
            Point p2_pos = (Point)pair_p2.getKey();
            Color p2_col = (Color)pair_p2.getValue();

            g.setColor(p2_col);
            g.fillRect(p2_pos.x,p2_pos.y  + getP2_position(),1,1);
        }

        //Renderar bollen.
        Iterator ball_iterator = ball.entrySet().iterator();
        while(ball_iterator.hasNext()) {
            Map.Entry pair_ball = (Map.Entry) ball_iterator.next();
            Point ball_pos = (Point)pair_ball.getKey();
            Color ball_col = (Color)pair_ball.getValue();

            g.setColor(ball_col);
            g.fillRect(ball_pos.x + getBall_x(),ball_pos.y + getBall_y() ,1,1);
        }
    }

    public void incP1() {
        if ((getP1_position() < 180)) {
            this.p1_position += moveSpeed;
        }
    }

    public void incP2() {
        if ((getP2_position() < 180)) {
            this.p2_position += moveSpeed;
        }
    }

    public void decP1() {
        if (getP1_position() > -200) {
            this.p1_position -= moveSpeed;
        }
    }

    public void decP2() {
        if (getP2_position() > -200) {
            this.p2_position -= moveSpeed;
        }
    }

    public int getP1_position() {
        return this.p1_position;
    }

    public int getP2_position(){
        return this.p2_position;
    }

    public int getBall_x() {
        return ball_x;
    }

    public int getBall_y() {
        return ball_y;
    }
}
