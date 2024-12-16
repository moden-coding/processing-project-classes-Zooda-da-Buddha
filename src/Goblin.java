import processing.core.PApplet;

public class Goblin {
    final int STARTaCTION = 1;
    final int ENDaCTION = 2;
    final int uP = 3;
    final int lEFT = 4;
    final int dOWN = 5;
    final int rIGHT = 6;

    private int health;
    private int healthMax;

    private int Xpos;
    private int Ypos;

    private int speed;

    private PApplet canvas;

    public Goblin(int health, int x, int y, int speed, PApplet c) {

        this.health = health;
        this.healthMax = health;

        this.Xpos = x;
        this.Ypos = y;

        this.speed = speed;

        canvas = c;
    }

    public void damage(int damageDealt) {
        health -= damageDealt;
    }

    public void refresh() {
        if (health > 0) {
            canvas.fill(70, 110, 30);
            canvas.noStroke();
            canvas.quad(Xpos, Ypos + 20, Xpos + 10, Ypos + 20, Xpos + 10, Ypos + 40, Xpos + 20, Ypos + 40);
            canvas.quad(Xpos, Ypos + 20, Xpos - 10, Ypos + 20, Xpos - 10, Ypos + 40, Xpos - 20, Ypos + 40);
            canvas.circle(Xpos, Ypos - 30, 20);
            canvas.rect(Xpos - 10, Ypos - 20, 20, 40);
        }
    }

    public void moveManager(int whichWay) {
        if (whichWay == lEFT) {
            this.Xpos -= this.speed;
        } else if (whichWay == rIGHT) {
            this.Xpos += this.speed;
        } else if (whichWay == uP) {
            this.Ypos -= this.speed;
        } else if (whichWay == dOWN) {
            this.Ypos += this.speed;
        }
    }

    public void walls(int leftWall, int rightWall, int farWall, int closeWall) {
        if (Xpos < leftWall) {
            Xpos = leftWall;
        } else if (Xpos > rightWall) {
            Xpos = rightWall;
        }

        if (Ypos < farWall) {
            Ypos = farWall;
        } else if (Ypos > closeWall) {
            Ypos = closeWall;
        }
    }

    public int GetX() {
        return this.Xpos;
    }

    public int GetY() {
        return this.Ypos;
    }
    
    public int GetHealth() {
        return this.health;
    }

    public void change(int itsHealth, int newX, int newY, int itsSpeed) {
        this.health = this.healthMax = itsHealth;
        this.Xpos = newX;
        this.speed = itsSpeed;
    }

    public void move(int playerX, int playerY) {
        for (int i = 0; i < speed; i++) {
            if (i % 2 == 0) {
                if (playerX < Xpos) {
                    Xpos -= 1;
                } else if (playerX > Xpos) {
                    Xpos += 1;
                }
            }else{
                if (playerY < Ypos) {
                    Ypos -= 1;
                } else if (playerY > Xpos) {
                    Ypos += 1;
                }
            }
        }
    }
}
