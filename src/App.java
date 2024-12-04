import processing.core.*;

public class App extends PApplet{
    double screen = 0.01;

    final int STARTaCTION = 1;
    final int ENDaCTION = 2;
    final int uP = 3;
    final int lEFT = 4;
    final int dOWN = 5;
    final int rIGHT = 6;

    final double mp1 = 60;


    Player thePlayer;
    public static void main(String[] args)  {
        PApplet.main("App");
    }

    public void setup(){

        thePlayer = new Player(0, 0, 5, 5, 50, 50, 0, 0.00, 5, false, this);
    }

    public void settings(){
        size(1460, 836);
//      middle is x = 730 and y = 418
    }

    public void draw(){
        if (screen == 0.01) {
            
        }

    }

    public void keyPressed() {
        if (screen % 1 == 0) {
            if (key == 'w' || key == UP) {
            }else if(key == 'a' || key == LEFT) {
                thePlayer.moveManager(lEFT);
            }else if(key == 's' || key == DOWN) {
            }else if(key == 'd' || key == RIGHT) {
            }
        }

    }


    public boolean whereMouseClick(int rightOfMouseX, int leftOfMouseX, int underMouseY, int aboveMouseY) {
        if (mouseX < rightOfMouseX && mouseX > leftOfMouseX && mouseY < underMouseY && mouseY > aboveMouseY) {
            return true;
        }else{
            return false;
        }
    }

    public void startScreen() {
        background(70, 110, 30);
        
        if (mousePressed && whereMouseClick(630, 830, 557, 667)) {
            double screen = mp1
        }
    }

    public void MP1() {
        background(165, 42, 42);
    }
}
