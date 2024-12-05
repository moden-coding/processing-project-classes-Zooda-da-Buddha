import processing.core.*;

public class App extends PApplet{
    double screen = 0.01;

    final int STARTaCTION = 1;
    final int ENDaCTION = 2;
    final int uP = 3;
    final int lEFT = 4;
    final int dOWN = 5;
    final int rIGHT = 6;

    final double MU1 = 0.01;
    final double MP1 = 60.00;


    Player thePlayer;
    public static void main(String[] args)  {
        PApplet.main("App");
    }

    public void setup(){

        thePlayer = new Player(0, 0, 5, 5, 50, 50, 0, 0.00, 10, false, this);
    }

    public void settings(){
        size(1460, 836);
//      middle is x = 730 and y = 418
    }






    public void draw(){
        if (screen == 0.01) {
            mu1();
        }else if (screen == MP1) {
            mkt1();
        }
        delay(1);
    }






    public void keyPressed() {
        if (screen % 1 == 0) {
            if (key == 'w' || key == UP) {
                thePlayer.moveManager(uP);
            }else if(key == 'a' || key == LEFT) {
                thePlayer.moveManager(lEFT);
            }else if(key == 's' || key == DOWN) {
                thePlayer.moveManager(dOWN);
            }else if(key == 'd' || key == RIGHT) {
                thePlayer.moveManager(rIGHT);
            }
        }else if(screen % 0.01 == 0) {
            
        }

    }

    public void mousePressed() {
//        if (whereIsIt(mouseX, mouseY, 630, 557, 200, 100)) {
//             screen = MP1;
//
  //      }
    }


    public boolean whereIsIt(int itsX, int itsY, int leftOfItX, int aboveItY, int targetWidth, int targetHeight) {
        if (itsX > leftOfItX && itsY > aboveItY && itsX < leftOfItX + targetWidth && itsY < aboveItY + targetHeight) {
            System.out.println("center");
            return true;
        }else{
            return false;
        }
    }

    public void mu1() {
        background(70, 110, 30);
        rect(630, 557, 200, 100);
        whereIsIt(mouseX, mouseY, 630, 557, 200, 100);
        
        if (mousePressed && whereIsIt(mouseX, mouseY, 630, 557, 200, 100)) {
            screen = MP1;
        }
    }

    public void mkt1() {
        background(90, 55, 55);
        thePlayer.refresh();
    }
}