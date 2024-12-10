import processing.core.PApplet;

public class Player {
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
    
    private boolean combat;

    private PApplet canvas;

    public Player(int mana, int manaMax, int health, int healthMax, int x, int y, int inventory, double money, int speed, boolean combat, PApplet c) {
        this.mana = mana;
        this.manaMax = manaMax;

        this.health = health;
        this.healthMax = healthMax;

        this.Xpos = x;
        this.Ypos = y;

        this.inventory = inventory;

        this.money = money;

        this.speed = speed;

        this.combat = combat;
        canvas = c;
    }

    public void inCombat() {
        combat = true;
    }

    public void damage(int damageDealt) {
        health -= damageDealt;
        if (health <= 0) {
        }
    }

    public void refresh() {
        canvas.noStroke();
        canvas.fill(100, 0, 100);
        canvas.triangle(Xpos, Ypos, Xpos - 15, Ypos + 40, Xpos + 15, Ypos + 40);
        canvas.fill(240, 220, 190);
        canvas.ellipse(Xpos, Ypos, 20, 20);
        canvas.fill(100, 0, 100);
        canvas.triangle(Xpos - 8, Ypos - 6, Xpos + 8, Ypos - 6, Xpos, Ypos - 40);
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

    public int GetX() {
        return this.Xpos;
    }

    public int GetY() {
        return this.Ypos;
    }

    public boolean COMBAT() {
        return combat;
    }
    
    public void quickMove() {
    }
}
