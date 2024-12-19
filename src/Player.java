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

    public Player(int mana, int manaMax, int health, int healthMax, int x, int y, int inventory, double money, int speed, PApplet c) {
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
        canvas.fill(20);
        canvas.rect(20, 20, 120, 50);
        canvas.fill(30, 100, 30);
        canvas.rect(30, 30, (float)health/healthMax * 100, 30);
        System.out.println((float)health / healthMax);
        if (attack && right) {
            canvas.noStroke();
            canvas.fill (100, 0, 100);
            canvas.triangle(Xpos, Ypos + 12, Xpos + 20, Ypos + 12, Xpos + 20, Ypos + 22);
            canvas.stroke(0);
            canvas.fill(240, 220, 190);
            canvas.rect(Xpos + 20, Ypos + 13, 5, 4);
            canvas.fill(50, 50, 50);
            canvas.noStroke();
            canvas.rect(Xpos + 20, Ypos - 4, 4, 17);
            canvas.triangle(Xpos + 20, Ypos - 4, Xpos + 24, Ypos - 4, Xpos + 22, Ypos - 6);
        }else if (attack && right == false) {
            canvas.noStroke();
            canvas.fill(100, 0, 100);
            canvas.triangle(Xpos, Ypos + 12, Xpos - 20, Ypos + 12, Xpos - 20, Ypos + 22);
            canvas.stroke(0);
            canvas.fill(240, 220, 190);
            canvas.rect(Xpos - 24, Ypos + 13, 5, 4);
            canvas.fill(50, 50, 50);
            canvas.noStroke();
            canvas.rect(Xpos - 24, Ypos - 4, 4, 17);
            canvas.triangle(Xpos - 24, Ypos - 4, Xpos - 28, Ypos - 4, Xpos - 26, Ypos - 6);
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

    public void change(int newMana, int newHealth, int newInventory, double newMoney) {
        this.mana = this.manaMax = newMana;
        this.health = this.healthMax = newHealth;
        this.inventory = newInventory;
        this.money = newMoney;
    }

    public void changeMoney (int moneyChanged) {
        this.money += moneyChanged;
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

    public void quickMove(int newX, int newY) {
        this.Xpos = newX;
        this.Ypos = newY;
    }

    public int getX() {
        return this.Xpos;
    }

    public int getY() {
        return this.Ypos;
    }
    
    public int getHealthMax() {
        return this.healthMax;
    }
    public int getHealth() {
        return this.health;
    }

    public int getMana() {
        return this.mana;
    }

    public int getManaMax() {
        return this.manaMax;
    }

    public int getInventory() {
        return this.inventory;
    }

    public double getMoney() {
        return this.money;
    }

    public int getSpeed() {
        return this.speed;
    }
}
