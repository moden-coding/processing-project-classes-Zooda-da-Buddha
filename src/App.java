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

    final double MU0 = 0.01;
    final double MU1 = 51.00;
    final double MU2 = 52.00;
    final double MU4 = 2.05;
    final double MK1 = 1.00;
    final double BF1 = 2.00;
    final double BF2 = 3.00;
    final double NT1 = 51.5;

    boolean first = true;
    boolean pointingRight = true;
    boolean attacking = false;
    int round = 0;
    int progression = 0;
    boolean inStasis = false;
    int stasisX = 0;
    int stasisY = 0;

    Player thePlayer;
    Player stasisPlayer;
    ArrayList<Goblin> goblinMaker = new ArrayList<>();
    ArrayList<Goblin> stasisGoblinMaker = new ArrayList<>();

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {

        thePlayer = new Player(0, 0, 100, 100, 730, 418, 0, 100.00, 15, this);
    }

    public void settings() {
        size(1460, 836);
    }

    public void draw() {
        if (screen == 0.01) {
            mu0();
        } else if (screen == MU1) {
            mu1();
        } else if (screen == MU2) {
            mu2();
        } else if (screen == MU4) {
            mu4();
        } else if (screen == NT1) {
            nt1();
        } else if (screen == MK1) {
            mk1();
        } else if (screen == BF1) {
            bf1();
        } else if (screen == BF2) {
            bf2();
        } else if (screen % 1 == 0.77) {
            mu5();
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
        progression = 0;
    }

    public void standerdRun(int numOfGobs, int putPlayerX, int putPlayerY) {

        // this is my centrel method for the actual gameplay

        // this is where the rooms are reset

        if (first && inStasis == false) {
            // the veriable first is true for only the first time a room is entered

            goblinGenerator(numOfGobs);

            thePlayer.quickMove(putPlayerX, putPlayerY);
        } else if (inStasis) {
            stasis2();
        }
        first = false;

        thePlayer.refresh(attacking, pointingRight);

        if (thePlayer.getHealth() < 0) {
            newScreen(0.02);
        }

        ArrayList<Integer> kills = new ArrayList<>();

        // I changed back to useing arraylist Kills because the way you suggested caused
        // a out of bounds crash I think do to stuff elsewere in the code

        for (Goblin gobs: goblinMaker) {
            gobs.refresh();
        }
        for (int i = 0; i < goblinMaker.size(); i++) {
            Goblin gobs = goblinMaker.get(i);
            if (gobs.GetHealth() >= 1) {
                gobs.move(thePlayer.getX(), thePlayer.getY());

                if (whereIsIt(gobs.GetX(), gobs.GetY(), thePlayer.getX() - 15, thePlayer.getY() - 40, 30, 80)) {
                    thePlayer.damage(1);
                    if (thePlayer.getHealth() <= 0) {
                        screen = MU2;
                    }
                }

                // this manages the player attacking the goblins

                if (attacking) {
                    if (pointingRight && whereIsIt(gobs.GetX(), gobs.GetY(), thePlayer.getX() + 15,
                            thePlayer.getY() - 40, 18, 80)) {
                        System.out.println("goblin prehealth " + gobs.GetHealth());
                        gobs.damage(1);
                        System.out.println("goblin damage R " + gobs.GetHealth());
                    } else if (whereIsIt(gobs.GetX(), gobs.GetY(), thePlayer.getX() - 5, thePlayer.getY() - 40, 18,
                            80)) {
                        System.out.println("goblin prehealth " + gobs.GetHealth());
                        gobs.damage(1);
                        System.out.println("goblin damage L " + gobs.GetHealth());
                    }
                }
            } else {
                kills.add(i);
            }
        }

        for (int toKill : kills) {
            thePlayer.changeMoney(goblinMaker.get(toKill).GetReward());
            goblinMaker.remove(toKill);
            System.out.println("goblin dead");

            if (key == 'm') {
                screen += 0.77;
            }
        }
    }

    public void goblinGenerator(int newGobs) {
        for (int i = 0; i < newGobs; i++) {
            goblinMaker.add(new Goblin(50, 730, 418, 5, 5, this));
        }
    }

    public void stasis1() {
        stasisPlayer = thePlayer;
        for (Goblin gobs : goblinMaker) {
            stasisGoblinMaker.add(gobs);
        }
        inStasis = true;
    }

    public void stasis2() {
        if (inStasis) {
            thePlayer = stasisPlayer;
            for (int i = goblinMaker.size() - 1; i > 0; i -= 1) {
                goblinMaker.remove(i);
            }
            for (Goblin gobs : stasisGoblinMaker) {
                goblinMaker.add(gobs);
            }
        }
        inStasis = false;
    }

    public void saver() {
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

        System.out.println("mouse detected 2");
        if ((whereIsIt(mouseX, mouseY, 40, 756, 440, 40) || whereIsIt(mouseX, mouseY, 500, 756, 440, 40)
                || whereIsIt(mouseX, mouseY, 960, 756, 440, 40)) && mousePressed) {
            int file = 0;
            System.out.println("mouse detected");
            if (whereIsIt(mouseX, mouseY, 40, 756, 440, 40)) {
                file = 1;
            } else if (whereIsIt(mouseX, mouseY, 500, 756, 440, 40)) {
                file = 2;
            } else {
                file = 3;
            }

            try (PrintWriter writer = new PrintWriter(String.valueOf("save" + file + ".txt"))) {
                writer.println(thePlayer.getManaMax());
                writer.println(thePlayer.getHealthMax());
                writer.println(thePlayer.getInventory());
                writer.println(thePlayer.getMoney());
                writer.println(thePlayer.getSpeed());
                writer.close();
                System.out.println("saved");
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }

        }
    }

    // mu stands for Market

    public void mu0() {
        background(1, 1, 1);
        textSize(50);
        fill(200, 200, 200);
        text("Use W A S and D to Move", 100, 100);
        text("Use the key M to go to the", 100, 336);
        text("Use the key B to escape menus", 100, 736);

        if (keyPressed || mousePressed) {
            screen = MU1;
        }
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

    public void mu4() {
        Player[] saves = new Player[3];
        if (first) {
            for (int i = 1; i <= 3; i++) {
                int newMana = 0;
                int newHealth = 0;
                int newInventory = 0;
                double newMoney = 0;
                int newSpeed = 0;
                try (Scanner scanner = new Scanner(Paths.get(String.valueOf("save" + i + ".txt")))) {
                    for (int a = 1; a <= 5; a++) {
                        String row = scanner.nextLine();
                        if (a == 1) {
                            newMana = Integer.valueOf(row);
                        } else if (a == 2) {
                            newHealth = Integer.valueOf(row);
                        } else if (a == 3) {
                            newInventory = Integer.valueOf(row);
                        } else if (a == 4) {
                            newMoney = Double.valueOf(row);
                        } else if (a == 5) {
                            newSpeed = Integer.valueOf(row);
                        }
                    }
                    if (i == 1) {
                        saves[0] = new Player(newMana, newMana, newHealth, newHealth, 730, 418, newInventory, newMoney,
                                newSpeed, this);
                    } else if (i == 2) {
                        saves[1] = new Player(newMana, newMana, newHealth, newHealth, 730, 418, newInventory, newMoney,
                                newSpeed, this);
                    } else if (i == 3) {
                        saves[2] = new Player(newMana, newMana, newHealth, newHealth, 730, 418, newInventory, newMoney,
                                newSpeed, this);
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            mu1();
            if (screen != MU4) {
                screen = MU4;
            }

        }
        stroke(1);
        fill(60, 40, 40);
        rect(20, 20, 1420, 796);
        fill(60);
        rect(40, 40, 1380, 232);
        rect(40, 292, 1380, 232);
        rect(40, 544, 1380, 232);

        textSize(192);
        fill(70);
        if (saves[0].getHealthMax() > 0) {
            fill(20, 20, 20);
        }
        text("Load 1st Save", 60, 210);
        fill(70);
        if (saves[0].getSpeed() > 0) {
            fill(20, 20, 20);
        }
        text("Load 2nd Save", 60, 462);
        ;
        fill(70);
        if (saves[0].getSpeed() > 0) {
            fill(20, 20, 20);
        }
        text("Load 3rd Save", 60, 714);

        if (mousePressed) {
            if (whereIsIt(mouseX, mouseY, 40, 40, 1380, 232) && saves[0].getSpeed() > 0) {
                thePlayer = saves[0];
                screen = MK1;
            } else if (whereIsIt(mouseX, mouseY, 40, 40, 1380, 232) && saves[1].getSpeed() > 0) {
                thePlayer = saves[1];
                screen = MK1;
            } else if (whereIsIt(mouseX, mouseY, 40, 40, 1380, 232) && saves[2].getSpeed() > 0) {
                thePlayer = saves[2];
                screen = MK1;
            }
        }

        if (key == 'b') {
            newScreen(MU1);
        }
    }

    public void mu2() {
        if (first) {
            keyPressed = false;
            first = false;
        }

        background(0, 0, 0);
        textSize(100);
        text("YOU DIED", 550, 368);
        goblinMaker.clear();
        saver();
        if (key == 'b') {
            thePlayer.damage(-thePlayer.getHealthMax());
            newScreen((MU1));
        }
    }

    public void mu5() {
        double oldScreen = screen;
        stasis1();
        screen -= 0.77;
        draw();
        screen = oldScreen;
        stasis2();
        stroke(1);
        fill(60, 40, 40);
        rect(20, 20, 1420, 796);
        saver();
        if (key == 'b') {
            newScreen(screen - 0.77);
        }
    }

    // mk stands for market

    public void mk1() {
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
        if (whereIsIt(thePlayer.getX(), thePlayer.getY(), 0, 100, 200, 120)) {
            fill(40);
            circle(thePlayer.getX() + 28, thePlayer.getY() - 30, 30);
            textSize(25);
            fill(150, 40, 40);
            text("f", thePlayer.getX() + 25, thePlayer.getY() - 20);
            if (key == 'f') {
                newScreen(NT1);
            }
        }

        standerdRun(0, 30, 418);

        if (whereIsIt(thePlayer.getX(), thePlayer.getY(), 1430, 368, 60, 150)) {
            newScreen(BF1);
        }
    }

    // nt stands for interaction

    public void nt1() {
        background(80, 80, 50);
        stasis1();
        fill(240, 220, 190);
        circle(500, 200, 200);
        fill(60, 110, 40);
        rect(400, 300, 200, 1000);
        rect(1200, -5, 500, 846);
        rect(-5, 700, 1470, 200);

        fill(1);
        rect(40, 616, 900, 200);
        textSize(50);
        fill(100);
        text("how can I help you", 60, 680);
        if (stasisPlayer.getMoney() < 100.00) {
            fill(1);
            textSize(40);
            rect(960, 616, 480, 46);
            fill(200, 30, 30);
            text("hearts", 970, 651);
            fill(1);
            rect(960, 692, 480, 46);
            fill(200, 30, 30);
            text("speed", 970, 727);
            fill(1);
            rect(960, 768, 480, 46);
            fill(200, 30, 30);
            text("Inventory", 970, 803);
        } else {
            fill(1);
            textSize(40);
            rect(960, 616, 480, 46);
            fill(100);
            text("hearts", 970, 651);
            fill(1);
            rect(960, 692, 480, 46);
            fill(100);
            text("speed", 970, 727);
            fill(1);
            rect(960, 768, 480, 46);
            fill(100);
            text("Mana", 970, 803);
            if (mousePressed) {
                if (whereIsIt(mouseX, mouseY, 960, 616, 480, 46)) {
                    
                    stasisPlayer.change(stasisPlayer.getMana(), stasisPlayer.getHealthMax() + 20, stasisPlayer.getSpeed(), stasisPlayer.getInventory(), stasisPlayer.getMoney() - 100);
                    
                    thePlayer.change(thePlayer.getMana(), thePlayer.getHealthMax() + 20, thePlayer.getSpeed(), thePlayer.getInventory(), thePlayer.getMoney() - 100);   



                } else if (whereIsIt(mouseX, mouseY, 960, 692, 480, 466)) {
                    
                    stasisPlayer.change(stasisPlayer.getMana(), stasisPlayer.getHealthMax(), stasisPlayer.getSpeed() + 1, stasisPlayer.getInventory(), stasisPlayer.getMoney() - 100);
                    
                    thePlayer.change(thePlayer.getMana(), thePlayer.getHealthMax(), thePlayer.getSpeed() + 1, thePlayer.getInventory(), thePlayer.getMoney() - 100);   



                } else if (whereIsIt(mouseX, mouseY, 960, 768, 480, 46)) {
                    
                    stasisPlayer.change(stasisPlayer.getMana(), stasisPlayer.getHealthMax(), stasisPlayer.getSpeed() + 1, stasisPlayer.getInventory(), stasisPlayer.getMoney() - 100);
                    
                    thePlayer.change(thePlayer.getMana() + 1, thePlayer.getHealthMax(), thePlayer.getSpeed(), thePlayer.getInventory(), thePlayer.getMoney() - 100);   
                }
            }
        }

        if (key == 'b') {
            screen = MK1;
        }
    }

    // bf stands for battlefeild

    public void bf1() {
        background(100, 100, 100);
        standerdRun(1, 30, 418);
        thePlayer.walls(0, 1460, 0, 836);
        if (goblinMaker.size() == 0) {
            fill(60);
            rect(1400, 388, 200, 100);
            if (whereIsIt(thePlayer.getX(), thePlayer.getY(), 1400, 388, 200, 100)) {
                newScreen(BF2);
            }
        }
        if (goblinMaker.size() == 0) {
            fill(60);
            rect(0, 388, 60, 100);
            if (whereIsIt(thePlayer.getX(), thePlayer.getY(), 0, 388, 200, 60)) {
                newScreen(BF2);
            }
        }
    }

    public void bf2() {
        background(100, 100, 100);
        standerdRun(2, 30, 418);
        thePlayer.walls(0, 1460, 0, 836);
        rect(1400, 388, 200, 100);
        if (round >= 3) {
            if (whereIsIt(thePlayer.getX(), thePlayer.getY(), 1400, 388, 200, 100)) {
                newScreen(MK1);
            }
        } else if (goblinMaker.size() == 0) {
            round++;
            goblinGenerator(2);
        }
    }

}