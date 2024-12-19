import processing.core.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class App extends PApplet {
    double screen = 0.01;

    final int STARTaCTION = 1;
    final int ENDaCTION = 2;
    final int uP = 3;
    final int lEFT = 4;
    final int dOWN = 5;
    final int rIGHT = 6;

    final double MU1 = 51.00;
    final double MU2 = 52.00;
    final double MU3 = 51.5;
    final double MU4 = 2.05;
    final double MK1 = 1.00;
    final double BF1 = 2.00;
    final double BF2 = 3.00;

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

        thePlayer = new Player(0, 0, 500, 500, 730, 418, 0, 0.00, 15, this);
    }

    public void settings() {
        size(1460, 836);
    }

    public void draw() {
        if (screen == 0.01) {
            mu1();
        } else if (screen == MU2) {
            mu2();
        } else if (screen == MU4) {
            mu4();
        } else if (screen == MK1) {
            mkt1();
        } else if (screen == BF1) {
            bf1();
        } else if (screen == 4.0) {
            devstan();
        } else if (screen == BF2) {
            bf2();
        } else if (screen % 1 == 0.77) {
            mu5();
        }
        if (timer == 1000) {
            timer = 0;
        } else {
            timer++;
        }
    }

    public void keyPressed() {
        thePlayer.getX();
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
            } else if (key == 'm') {
                screen += 0.77;
            }
        }

    }

    public boolean whereIsIt(int itsX, int itsY, int leftOfItX, int aboveItY, int targetWidth, int targetHeight) {
        if (itsX > leftOfItX && itsY > aboveItY && itsX < leftOfItX + targetWidth && itsY < aboveItY + targetHeight) {
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
                goblinMaker.add(new Goblin(5, 730, 418, 5, 5, this));
            }

            thePlayer.quickMove(putPlayerX, putPlayerY);
        }
        first = false;
        ArrayList<Integer> kills = new ArrayList<>();

        thePlayer.refresh(attacking, pointingRight);

        for (int i = 0; i < goblinMaker.size(); i++) {
            Goblin gobs = goblinMaker.get(i);
            if (gobs.GetHealth() >= 1) {
                gobs.move(thePlayer.getX(), thePlayer.getY());

                if (whereIsIt(gobs.GetX(), gobs.GetY(), thePlayer.getX() - 15, thePlayer.getY() - 40, 30, 80)) {
                    thePlayer.damage(1);

                    if (pointingRight) {
                        gobs.quickMove(gobs.GetX() + 50, gobs.GetY());
                    } else if (pointingRight == false) {
                        gobs.quickMove(gobs.GetX() - 50, gobs.GetY());
                    }

                    if (whereIsIt(gobs.GetX(), gobs.GetY(), thePlayer.getX() + 15, thePlayer.getY() + 40, 20, 80)
                            && pointingRight && attacking)
                        gobs.damage(1);
                    gobs.quickMove(gobs.GetX() + 50, gobs.GetY());
                    System.out.println("Gob Damage r");

                } else if (whereIsIt(gobs.GetX(), gobs.GetY(), thePlayer.getX() - 35, thePlayer.getY() + 40, 20, 80)
                        && pointingRight && attacking) {
                    gobs.damage(1);
                    gobs.quickMove(gobs.GetX() + 50, gobs.GetY());
                    System.out.println("Gob Damage l");
                }
                if (gobs.GetHealth() < 1) {
                    goblinMaker.remove(i);
                    System.out.println("gob Killed");
                }

                allDead = false;
            } else {
                goblinMaker.remove(i);
                System.out.println("gob Killed");
            }
            gobs.refresh();
        }

        if (thePlayer.getHealth() < 0) {
            newScreen(0.02);
        }

        if (key == 'm') {
            screen += 0.77;
        }
    }

    public boolean allDead() {
        boolean alive = false;
        for (Goblin gobs : goblinMaker) {
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
    }

    public void mu1() {
        background(70, 110, 30);

        if (whereIsIt(mouseX, mouseY, 0, 0, 1460, 505)) {
            textSize(50);
            fill(50, 50, 100);
            text("New game", 20, 450);
            fill(50, 100, 50);
            text("resume Game", 20, 510);

            if (mousePressed) {
                newScreen(MK1);
            }
        } else if (whereIsIt(mouseX, mouseY, 0, 505, 1460, 836)) {
            textSize(50);
            fill(50, 100, 50);
            text("New Game", 20, 450);
            fill(50, 50, 100);
            text("Resume Game", 20, 510);
            if (mousePressed) {
                newScreen(MU4);
            }
        }
    }

    public void mu2() {
        background(0, 0, 0);
        textSize(100);
        text("YOU DIED", 620, 368);
        if (keyPressed) {
            thePlayer.damage(-thePlayer.getHealthMax());
        }
    }

    public void mu5() {

        if ((whereIsIt(X, Y, LEFT, MOVE, rwidth, height) && keyPressed) || (whereIsIt(X, Y, LEFT, MOVE, rwidth, height) && keyPressed) || (whereIsIt(X, Y, LEFT, MOVE, rwidth, height) && keyPressed)) {
            int file = 0;


            if (whereIsIt(X, Y, LEFT, MOVE, rwidth, height)) {
                file = 1;
            }else if (whereIsIt(X, Y, LEFT, MOVE, rwidth, height)) {
                file = 2;
            }else {
                file = 3;
            }
            try (PrintWriter writer = new PrintWriter("save" + file + ".txt")) {
            } catch (IOException e) {
                e.printStackTrace();
            } //this was taken from Chatgpt

            try (PrintWriter writer = new PrintWriter("save" + file + ".txt")){
            writer.println(thePlayer.getManaMax());
            writer.println(thePlayer.getHealthMax());
            writer.println(thePlayer.getInventory());
            writer.println(thePlayer.getMoney());
            writer.println(thePlayer.getSpeed());
            writer.close(); // Closes the writer and saves the file
            System.out.println("Integer saved to file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
    screen -= 0.77;
    draw();
    screen += 0.77;

    stroke(1);
    fill(60, 40, 40);
    rect(20, 20, 1420, 796);
    fill(120, 80, 64);
    textSize(30);
    rect(40, 756, 440, 40);
    rect(500, 756, 440, 40);
    rect(960, 756, 440, 40);
    fill(40, 120, 40);
    fill(200, 200, 200);
    stroke(0);
    text("First save", 75, 785);
    text("Second save", 535, 785);
    text("Third save", 995, 785);
    noStroke();


}
        
    

    public void mu4() {
        Player[] saves = new Player[3];
        if (first) {
            for (int i = 1; i <= 3; i++) {
                int newMana = 0;
                int newHealth = 0;
                int newInventory = 0;
                double newMoney = 0;
                int newSpeed = 0;
            try (Scanner scanner = new Scanner(Paths.get("save" + i + ".txt"))) {
                // we read the file until all lines have been read
                for (int a = 1; a <= 5; a++) {
                    // we read one line
                    String row = scanner.nextLine();
                    if (a == 1) {
                        newMana = Integer.valueOf(row);
                    }else if (a == 2) {
                        newHealth = Integer.valueOf(row);
                    }else if (a == 3) {
                        newInventory = Integer.valueOf(row);
                    }else if (a == 4) {
                        newMoney = Double.valueOf(row);
                    }else if (a == 5) {
                        newSpeed = Integer.valueOf(row);
                    }
                }
                if (i == 1) {
                    saves[0] = new Player(newMana, newMana, newHealth, newHealth, 730, 418, newInventory, newMoney, newSpeed, this);
                }else if (i == 2) {
                    saves[1] = new Player(newMana, newMana, newHealth, newHealth, 730, 418, newInventory, newMoney, newSpeed, this);
                }else if (i == 3) {
                    saves[2] = new Player(newMana, newMana, newHealth, newHealth, 730, 418, newInventory, newMoney, newSpeed, this);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        mu1();

        }
        stroke(1);
        fill(60, 40, 40);
        rect(20, 20, 1420, 796);
        fill(60);
        rect(40, 40, 1380, 232);
        rect(40, 292, 1380, 232);
        rect(40, 544, 1380, 232);
        if (key == ' ') {
            newScreen(MU1);
        }
    }

    // mk stands for market

    public void mkt1() {
        background(80, 80, 50);
        thePlayer.walls(0, 1460, 170, 836);
        fill(60);
        rect(1430, 368, 60, 150);
        fill(240, 220, 190);
        circle(100, 100, 24);
        fill(60, 110, 40);
        rect(88, 112, 24, 50);

        fill(240, 220, 190);
        circle(300, 100, 24);
        fill(60, 110, 40);
        rect(288, 112, 24, 50);

        rect(200, 0, 60, 160);
        rect(0, 150, 1460, 50);
        if (whereIsIt(thePlayer.getX(), thePlayer.getY(), 0, 170, 200, 50)) {

        }

        standerdRun(0, 30, 418);

        if (whereIsIt(thePlayer.getX(), thePlayer.getY(), 1430, 368, 60, 150)) {
            newScreen(BF1);
        }
    }

    public void mu3() {
        mkt1();
    }

    // bf stands for battlefeild

    public void bf1() {
        background(100, 100, 100);
        standerdRun(1, 30, 418);
        thePlayer.walls(0, 1460, 0, 836);
        if (allDead) {
            rect(1400, 776, 200, 100);
            if (whereIsIt(thePlayer.getX(), thePlayer.getY(), 1400, 776, 200, 100)) {
                newScreen(SCREEN);
            }
        }
    }

    public void bf2() {
        background(100, 100, 100);
        standerdRun(2, 30, 418);
        thePlayer.walls(0, 1460, 0, 836);
    }

}