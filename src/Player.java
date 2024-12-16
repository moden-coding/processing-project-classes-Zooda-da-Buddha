import processing.core.PApplet;

public class Player{
    final int STARTaCTION = 1;
    final int ENDaCTION = 2;
    final int uP = 3;
    final int lEFT = 4;
    final int dOWN = 5;
    final int rIGHT = 6;

    private int mana;
    private int manaMax;

    private int health;
    private int healthMax;

    private int Xpos;
    private int Ypos;

    private int speed;

    private int inventory;

    private double money;

    private PApplet canvas;

    public Player(int mana, int manaMax, int health, int healthMax, int x, int y, int inventory, double money, int speed, boolean combat, PApplet c) {
        this.mana = mana;
        this.manaMax = manaMax;

        this.health = health;
        this.healthMax = healthMax;

        this.Xpos = x;
        this.Ypos = y;

        this.speed = speed;

        this.inventory = inventory;

        this.money = money;

        this.speed = speed;
        canvas = c;
    }

    public void damage(int damageDealt) {
        health -= damageDealt;
    }

    public void refresh(boolean attack, boolean right) {
        canvas.noStroke();
        canvas.fill(100, 0, 100);
        canvas.triangle(Xpos, Ypos, Xpos - 15, Ypos + 40, Xpos + 15, Ypos + 40);
        canvas.fill(240, 220, 190);
        canvas.ellipse(Xpos, Ypos, 20, 20);
        canvas.fill(100, 0, 100);
        canvas.triangle(Xpos - 8, Ypos - 6, Xpos + 8, Ypos - 6, Xpos, Ypos - 40);
        canvas.stroke(0);
        if (attack && right) {
            canvas.noStroke();
            canvas.triangle(Xpos, Ypos + 12, Xpos + 20, Ypos + 12, Xpos + 20, Ypos + 22);
            canvas.stroke(0);
            canvas.fill
            canvas.rect(Xpos + 20, Ypos + 13, 5, 4);
            System.out.println("attacking right");
        }else if (attack && right == false) {
            canvas.noStroke();
            canvas.triangle(Xpos, Ypos + 12, Xpos - 20, Ypos + 12, Xpos - 20, Ypos + 22);
            System.out.println("attacking left");
            
        }
    }

    public void moveManager(int whichWay) {
        if (whichWay == lEFT) {
            this.Xpos -= this.speed;
        }else if (whichWay == rIGHT) {
            this.Xpos += this.speed;
        }else if (whichWay == uP) {
            this.Ypos -= this.speed;
        }else if (whichWay == dOWN) {
            this.Ypos += this.speed;
        }
    }
    
    public void attack(boolean right) {

    }

    public void walls(int leftWall, int rightWall, int farWall, int closeWall) {
        if (Xpos < leftWall) {
            Xpos = leftWall;
        }else if(Xpos > rightWall) {
            Xpos = rightWall;
        }
        
        if (Ypos < farWall) {
            Ypos = farWall;
        }else if(Ypos > closeWall) {
            Ypos = closeWall;
        }
    }

    public int GetX() {
        return this.Xpos;
    }

    public int GetY() {
        return this.Ypos;
    }
    
    public void quickMove(int newX, int newY) {
        this.Xpos = newX;
        this.Ypos = newY;
    }

    public int GetHealthMax() {
        return this.healthMax;
    }
    public int GetHealth() {
        return this.health;
    }
}
