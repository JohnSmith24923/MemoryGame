package Grid_Based_Game;
// Rishi Shah; Mr Harwood Period 4 Grade 11 Comp Sci; Brick Breaker Game
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import java.awt.Rectangle;

class Images extends Rectangle {
    BufferedImage img;
    boolean visable = true;
    String fruit;
    int storenum;
    
    Images(int x, int y, String cardImage){ 
        visable = true; 
        this.x = x;
        this.y = y;
        width = 125; 
        height = 175;
        img = loadImage(cardImage);
        
        if (cardImage.equals( "001-peach.png")){
            storenum = 1;
        }
        else if(cardImage.equals( "002-grape.png")){
            storenum = 2;
        }
        else if (cardImage.equals( "003-orange.png")){
            storenum = 3;
        }
        else if (cardImage.equals( "004-apple.png")){
            storenum = 4;
        }
        else if (cardImage.equals( "005-banana.png")){
            storenum = 5;
        }
        else if (cardImage.equals( "006-coconut.png")){
            storenum = 6;
        }
        else if (cardImage.equals( "007-pineapple.png")){
            storenum = 7;
        }
        else if (cardImage.equals( "008-eggplant.png")){
            storenum = 8;
        }
    }

    boolean getVisible(){
        return visable;
    }

    static BufferedImage loadImage(String filename) {
        BufferedImage img = null;			
        try {
            img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null, "An image failed to load: " + filename , "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        //DEBUG
        //if (img == null) System.out.println("null");
        //else System.out.printf("w=%d, h=%d%n",img.getWidth(),img.getHeight());
        
        return img;
    }   
}
