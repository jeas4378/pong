import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GameField extends JPanel {

    //HashMaps som innehåller grafisk representation av spelplanen, spelare 1 respektive spelare 2 paddel och
    //bollen.
    private HashMap<Point,Color> lines = new HashMap<Point,Color>();
    private HashMap<Point,Color> p1 = new HashMap<Point,Color>();
    private HashMap<Point,Color> p2 = new HashMap<Point,Color>();
    private HashMap<Point,Color> ball = new HashMap<Point,Color>();

    //Spelarnas paddelpositioner. Default-värde 0 placerar paddles ungefär i mitten av spelplanen.
    private static int p1_position = 0;
    private static int p2_position = 0;

    //Hur mycket en paddel ska flytta på sig vid varje increment.
    private static int moveSpeed = 10;

    //Bollen behöver flytta sig i både x- och y-led och har därför två värden.
    private static int ball_x = 0;
    private static int ball_y = 0;

    private static Boolean player1 = false;
    private static Boolean ready = false;

    private ClientConnection clientConnection;

    public GameField() {

        //Konstruktorn för GameField skapar alla grafiska representationer.
        createGraphics();

    }

    /**
     *En metod som skapar alla grafiska objekt som spelplan, spelare 1 och 2s paddel samt bollen.
     */
    private void createGraphics() {

        Boolean block = false;
        Boolean middle = false;
        int height_min = 220;
        int height_max = 280;

        //Traverserar över varenda pixel i hela spelplanen.
        for (int y = 0; y < 501; y++) {
            for (int x = 0; x < 1001; x++) {
                Point p = new Point(x,y);
                if (block) {
                    lines.put(p,Color.BLACK);
                }
                else {
                    //lines.put(p,Color.WHITE);
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
                //Skapar toppen och botten linjen.
                if ((y < 20) || (y > 460)) {
                    block = true;
                }
                //Skapar den vertikala mittlinjen.
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
            //För att få jämna storleken på mittblocken så ändrar vi värdet på variabeln 'middle'
            //var 20e pixel fram och tillbaka mellan true och false.
            if ((y-10) % 20 == 0) {
                middle = middle ? false : true;
            }

        }

        repaint();

    }

    /**
     * Metoden som faktiskt ritar den grafiska representationen.
     * @param g Graphics-objekt.
     */
    public synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Målar över hela spelplanen till vitt.
        this.setBackground(Color.WHITE);

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
            //Paddles position manipuleras genom att lägga till 'p1_position' till y-värdet.
            g.fillRect(p1_pos.x,p1_pos.y + getP1_position(),1,1);
        }

        //Renderar spelare 2s paddel
        Iterator player2 = p2.entrySet().iterator();
        while(player2.hasNext()) {
            Map.Entry pair_p2 = (Map.Entry) player2.next();
            Point p2_pos = (Point)pair_p2.getKey();
            Color p2_col = (Color)pair_p2.getValue();

            g.setColor(p2_col);
            //Paddles position manipuleras genom att lägga till 'p1_position' till y-värdet.
            g.fillRect(p2_pos.x,p2_pos.y  + getP2_position(),1,1);
        }

        //Renderar bollen.
        Iterator ball_iterator = ball.entrySet().iterator();
        while(ball_iterator.hasNext()) {
            Map.Entry pair_ball = (Map.Entry) ball_iterator.next();
            Point ball_pos = (Point)pair_ball.getKey();
            Color ball_col = (Color)pair_ball.getValue();

            g.setColor(ball_col);
            //Bollens position manipuleras genom att lägga till 'ball_x' och 'ball_y' till respektive
            //x- och y-värdet.
            g.fillRect(ball_pos.x + getBall_x(),ball_pos.y + getBall_y() ,1,1);
        }
    }

    /**
     * Tolkar meddelanden som tas emot från spelservern. Då servern är konstruerad att endast skicka meddelanden
     * på formen 'true'/'false', '[spelare],[pos]' eller 'b,x-pos,y-pos' har jag endast skapat fall där dessa fångas upp.
     * Skulle någon skicka något annat så skulle inget hända. Skulle någon som vill förstöra skicka en tom-sträng
     * skulle troligen det bli ett indexOutOfBound-error då den skulle bli problem med 'data[0]'.
     * @param s
     */
    public synchronized void recieveMessage(String s) {
        System.out.println("Klient tog emot meddelande");
        String[] data = s.split(",");
        if (data[0].equals("p1")) {
            setP1_position(Integer.parseInt(data[1]));
        }
        else if (data[0].equals("p2")) {
            setP2_position(Integer.parseInt(data[1]));
        }
        else if ((data[0].equals("true")) || (data[0].equals("false"))) {
            setPlayer1(Boolean.parseBoolean(data[0]));
            setReady(true);
        }
        else if (data[0].equals("b")) {
            setBall_x(Integer.parseInt(data[1]));
            setBall_y(Integer.parseInt(data[2]));
        }
        repaint();
    }

    public static void setP1_position(int pos){
        p1_position = pos;
    }

    public static void setP2_position(int pos) { p2_position = pos; }

    /**
     * Flyttar ned spelare 1 paddle. Så fort en paddle har ändrats skickas en uppdatering spelare 1s paddel
     * till servern.
     */
    public void incP1() {
        if ((getP1_position() < 180)) {
            p1_position += moveSpeed;
        }
        clientConnection.send(sendMessage());
    }

    /**
     * Flyttar ned spelare 2 paddle. Så fort en paddle har ändrats skickas en uppdatering spelare 2s paddel
     * till servern.
     */
    public void incP2() {
        if ((getP2_position() < 180)) {
            p2_position += moveSpeed;
        }
        clientConnection.send(sendMessage());
    }

    /**
     * Flyttar spelare 1 paddel uppåt. Som ovan uppdateras servern.
     */
    public void decP1() {
        if (getP1_position() > -200) {
            p1_position -= moveSpeed;
        }
        clientConnection.send(sendMessage());
    }

    /**
     * Flyttar spelare 2 paddel uppåt. Som ovan uppdateras servern.
     */
    public void decP2() {
        if (getP2_position() > -200) {
            p2_position -= moveSpeed;
        }
        clientConnection.send(sendMessage());
    }

    public int getP1_position() {
        return p1_position;
    }

    public static void setPlayer1(Boolean bool) {
        player1 = bool;
    }

    public static void setReady(Boolean bool) {
        ready = bool;
    }

    public static Boolean getPlayer1() {
        return player1;
    }

    public static Boolean getReady() {
        return ready;
    }

    public int getP2_position(){
        return p2_position;
    }

    public static void setBall_x(int pos) {
        ball_x = pos;
    }

    public static void setBall_y(int pos) {
        ball_y = pos;
    }

    public int getBall_x() {
        return ball_x;
    }

    public int getBall_y() {
        return ball_y;
    }

    /**
     * En metod som inte används i nuläget.
     * @param clientConnection
     */
    public void setClientConnection(ClientConnection clientConnection){
        this.clientConnection = clientConnection;
    }

    /**
     * Formaterar ett meddelande som ska skickas till servern. Då klienten endast skickar information
     * om var sin spelares paddle är kan vi standardisera meddelande på följande vis. Då en spelare inte
     * kan flytta på sin paddle förrän spelet är igång så betyder det att klienten vet om den är spelare 1
     * eller inte. Beroende på vad 'Player1' är satt till formuleras två olika meddelanden.
     * @return En String med information som ska skickas till servern.
     */
    public String sendMessage() {
        String s = null;
        if (getPlayer1()) {
            s = "p1," + Integer.toString(getP1_position());
        }
        else {
            s = "p2," + Integer.toString(getP2_position());
        }
        return s;
    }
}
