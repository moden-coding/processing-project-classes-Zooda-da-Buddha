import processing.core.*;
import java.util.ArrayList;

public class App extends PApplet {
    double screen = 0.01;

    final int STARTaCTION = 1;
    final int ENDaCTION = 2;
    final int uP = 3;
    final int lEFT = 4;
    final int dOWN = 5;
    final int rIGHT = 6;

    final double MU1 = 0.01;
    final double MK1 = 60.00;
    final double BF1 = 90.00;

    boolean first = true;



    Player thePlayer;
    Goblin[] goblins = new Goblin[20];

    

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {

        thePlayer = new Player(0, 0, 5, 5, 730, 418, 0, 0.00, 10, false, this);
    }

    public void settings() {
        size(1460, 836);
        goblins[0] = new Goblin(0, 0, 0, 0, -100, -100, 0, new PApplet());
        goblins[1] = new Goblin(0, 0, 0, 0, -100, -100, 0, new PApplet());
        goblins[2] = new Goblin(0, 0, 0, 0, -100, -100, 0, new PApplet());
        goblins[3] = new Goblin(0, 0, 0, 0, -100, -100, 0, new PApplet());
        goblins[4] = new Goblin(0, 0, 0, 0, -100, -100, 0, new PApplet());
        goblins[5] = new Goblin(0, 0, 0, 0, -100, -100, 0, new PApplet());
        goblins[6] = new Goblin(0, 0, 0, 0, -100, -100, 0, new PApplet());
        goblins[7] = new Goblin(0, 0, 0, 0, -100, -100, 0, new PApplet());
        goblins[8] = new Goblin(0, 0, 0, 0, -100, -100, 0, new PApplet());
        goblins[9] = new Goblin(0, 0, 0, 0, -100, -100, 0, new PApplet());
        goblins[10] = new Goblin(0, 0, 0, 0, -100, -100, 0, new PApplet());
        goblins[11] = new Goblin(0, 0, 0, 0, -100, -100, 0, new PApplet());
        goblins[12] = new Goblin(0, 0, 0, 0, -100, -100, 0, new PApplet());
        // middle is x = 730 and y = 418

    }

    private void GoblinMaker() {

    }

    public void draw() {
        if (screen == 0.01) {
            mu1();
        } else if (screen == 0.02) {
        } else if (screen == MK1) {
            mkt1();
        } else if (screen == BF1) {
            bf1();
        } else if (screen == 4.0) {
            devstan();
        }
    }

    public void keyPressed() {
        thePlayer.GetX();
        if (screen % 1 == 0) {
            if (key == 'w' || key == UP) {
                thePlayer.moveManager(uP);
            } else if (key == 'a' || key == LEFT) {
                thePlayer.moveManager(lEFT);
            } else if (key == 's' || key == DOWN) {
                thePlayer.moveManager(dOWN);
            } else if (key == 'd' || key == RIGHT) {
                thePlayer.moveManager(rIGHT);
            } else if (key == 'j') {
                screen = 4.0;
            }
        } else if (thePlayer.COMBAT() && key == 'f') {
            thePlayer.attack();
        }

    }

    public boolean whereIsIt(int itsX, int itsY, int leftOfItX, int aboveItY, int targetWidth, int targetHeight) {
        if (itsX > leftOfItX && itsY > aboveItY && itsX < leftOfItX + targetWidth && itsY < aboveItY + targetHeight) {
            System.out.println("center");
            return true;
        } else {
            return false;
        }
    }

    public void newScreen(double screenTo) {
        first = true;
        screen = screenTo;
    }

    public void damageManager() {
    }

    public void devstan() {
        background(30, 30, 30);
        if (first) {
            goblins[0].change(0, 5, 730, 418, 5);
        }
        thePlayer.walls(0, 1460, 0, 836);
        thePlayer.refresh();
        first = false;
    }

    public void mu1() {
        background(70, 110, 30);
        rect(630, 557, 200, 100);
        whereIsIt(mouseX, mouseY, 630, 557, 200, 100);

        if (mousePressed && whereIsIt(mouseX, mouseY, 630, 557, 200, 100)) {
            newScreen(MK1);
        }
    }

    // mk stands for market

    public void mkt1() {
        background(90, 55, 55);
        thePlayer.walls(0, 1460, 0, 836);
        thePlayer.refresh();
        rect(1400, 368, 60, 150);

        if (whereIsIt(thePlayer.GetX(), thePlayer.GetY(), 1400, 368, 60, 150)) {
            newScreen(BF1);
        }
    }

    // bf stands for battlefeild

    public void bf1() {
        if (first == true) {
        }
        background(100, 100, 100);
        thePlayer.walls(0, 1460, 0, 836);
        thePlayer.refresh();
    }

}