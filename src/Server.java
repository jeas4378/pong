public class Server {

    private int p1_position = 0;
    private int p2_position = 0;
    private int ball_x = 0;
    private int ball_y = 0;

    public static void main(String[] args) {

    }

    public void setP1_position(int pos) {
        this.p1_position = pos;
    }

    public void setP2_position(int pos) {
        this.p2_position = pos;
    }

    public int getP1_position(){
        return this.p1_position;
    }

    public int getP2_position(){
        return this.p2_position;
    }

    public void setBall_x(int pos){
        this.ball_x = pos;
    }

    public void setBall_y(int pos){
        this.ball_y = pos;
    }

    public int getBall_x(){
        return this.ball_x;
    }

    public int getBall_y(){
        return this.ball_y;
    }
}
