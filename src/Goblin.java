import processing.core.PApplet;

public class Player {
    final int STARTaCTION = 1;
    final int ENDaCTION = 2;
    final int uP = 3;
    final int lEFT = 4;
    final int dOWN = 5;
    final int rIGHT = 6;

    private int mana;

    private int health;

    private int Xpos;
    private int Ypos;

    private int speed;

    private int inventory;


    private PApplet canvas;

    public Player(int mana, int manaMax, int health, int healthMax, int x, int y, int inventory, double money, int speed, boolean combat, PApplet c) {
        this.mana = mana;

        this.health = health;

        this.Xpos = x;
        this.Ypos = y;

        this.inventory = inventory;

        this.speed = speed;

        canvas = c;
    }


    public void damage(int damageDealt) {
        health -= damageDealt;
        if (health <= 0) {
        }
    }

    public void refresh() {
        canvas.fill(50, 100, 50);
        canvas.triangle(Xpos, Ypos, Xpos - 9, Ypos + 24, Xpos + 9, Ypos + 24);
        canvas.ellipse(Xpos, Ypos, 20, 20);
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
    
    public void attack() {
        canvas.quad(Xpos + 10, Ypos, Xpos + 16, Ypos, Xpos, Ypos, Xpos, Ypos);
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

    public int XPOS() {
        return this.Xpos;
    }

    public int YPOS() {
        return this.Ypos;
    }
    
}