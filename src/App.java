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
    final double MU2 = 0.02;
    final double MK1 = 60.00;
    final double BF1 = 61.00;
    final double BF2 = 62.00;



    boolean first = true;
    boolean allDead = true;
    boolean pointingRight = true;
    boolean attacking = false;
    int timer = 0;



    Player thePlayer;
    ArrayList<Goblin> goblinMaker = new ArrayList<>();
    

    

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {

        thePlayer = new Player(0, 0, 500, 500, 730, 418, 0, 0.00, 10, false, this);
    }

    public void settings() {
        size(1460, 836);
    }

    public void draw() {
        if (screen == 0.01) {
            mu1();
        } else if (screen == 0.02) {
            mu2();
        } else if (screen == MK1) {
            mkt1();
        }else if (screen == BF1) {
            bf1();
        }else if (screen == 4.0) {
            devstan();
        }else if (screen == BF2) {
            bf2();
        }
        if (timer == 1000) {
            timer = 0;
        }else{
            timer++;
        }
    }

    public void keyPressed() {
        thePlayer.GetX();
        if (screen % 1 == 0) {
            if (key == 'w' || key == UP) {
                thePlayer.moveManager(uP);
                attacking = false;
            } else if (key == 'a' || key == LEFT) {
                thePlayer.moveManager(lEFT);
                pointingRight = false;
                attacking = false;
            } else if (key == 's' || key == DOWN) {
                thePlayer.moveManager(dOWN);
                attacking = false;
            } else if (key == 'd' || key == RIGHT) {
                pointingRight = true;
                thePlayer.moveManager(rIGHT);
                attacking = false;
            } else if (key == 'j') {
                newScreen(4.0);
            } else if (key == ' ') {
                attacking = true;
            }
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
    
    public void standerdRun(int numOfGobs, int putPlayerX, int putPlayerY) {
        if (first == true) {
            for (int i = 0; i < numOfGobs; i++) {
                goblinMaker.add(new Goblin(5, 730, 418, 5, this));
            }

            thePlayer.quickMove(putPlayerX, putPlayerY);
        }
            first = false;

            thePlayer.refresh(attacking, pointingRight);

        for (Goblin gobs: goblinMaker) {
            if (gobs.GetHealth() > 0) {
                gobs.move(thePlayer.GetX(), thePlayer.GetY());
                if (thePlayer.GetX() == gobs.GetX() && thePlayer.GetY() == gobs.GetY()) {
                    thePlayer.damage(1);
                    System.out.println("Player damage");
                    if (whereIsIt(gobs.GetX(), gobs.GetY(), thePlayer.GetX() + 15, thePlayer.GetY() + 40, 20, 80) && pointingRight)
                    gobs.damage(1);
                    System.out.println("Gob Damage r");
                }else if (whereIsIt(gobs.GetX(), gobs.GetY(), thePlayer.GetX() - 35, thePlayer.GetY() + 40, 20, 80) && pointingRight)
                    gobs.damage(1);
                    System.out.println("Gob Damage l");
                    allDead = false;
                }
                gobs.refresh();
                System.out.println("gob Refresh");
            }

        if (thePlayer.GetHealth() < 0) {
            newScreen(0.02);
        }
    }

    public boolean allDead() {
        boolean alive = false;
        for (Goblin gobs: goblinMaker) {
            if (gobs.GetHealth() > 0) {
                alive = true;
            }
        }
        return alive;
    }

    public void devstan() {
        background(30, 30, 30);
        standerdRun(2, 30, 418);
        thePlayer.walls(0, 1460, 0, 836);
        System.out.println("hi");
    }

    public void mu1() {
        background(70, 110, 30);

        if (whereIsIt(mouseX, mouseY, 0, 0, 1460, 505)) {
            textSize(50);
            fill(50, 50, 50);
            text("Resume game", 20, 450);
            fill(50, 100, 50);
            text("New Game", 20, 510);

            if (mousePressed) {
                newScreen(MK1);
            }
        }else if(whereIsIt(mouseX, mouseY, 0, 505, 1460, 836)) {
            textSize(50);
            fill(50, 100, 50);
            text("Resume game", 20, 450);
            fill(50, 50, 50);
            text("New Game", 20, 510);
        }
    }

    public void mu2() {
        background(0, 0, 0);
        textSize(100);
        text("YOU DIED", 620, 368);
        if (keyPressed) {
            thePlayer.damage(-thePlayer.GetHealthMax());
        }
    }

    // mk stands for market

    public void mkt1() {
        background(80, 50, 50);
        standerdRun(0, 30, 418);
        thePlayer.walls(0, 1460, 0, 836);
        fill(60);
        rect(1430, 368, 60, 150);

        if (whereIsIt(thePlayer.GetX(), thePlayer.GetY(), 1430, 368, 60, 150)) {
            newScreen(BF1);
        }
    }

    // bf stands for battlefeild

    public void bf1() {
        background(100, 100, 100);
        standerdRun(1, 30, 418);
        thePlayer.walls(0, 1460, 0, 836);
        System.out.println("hi");
        if (allDead) {
            rect(1400, 776, 200, 100);
            if (whereIsIt(thePlayer.GetX(), thePlayer.GetY(), 1400, 776, 200, 100)) {
                newScreen(SCREEN);
            }
        }
    }

    public void bf2() {
        background(100, 100, 100);
        standerdRun(2, 30, 418);
        thePlayer.walls(0, 1460, 0, 836);
        System.out.println("hi");
    }

}