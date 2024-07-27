package Grid_Based_Game;
// Memory Card Game Created by Rishi Shah; Mr Harwood Period 4 Grade 11 University Computer Science
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import hsa2.GraphicsConsole;

public class MemoryCard {
    public static void main (String [] args){
        new MemoryCard();
    }
    // Window Creation
    int winW = 600;
	int winH = 800;
	GraphicsConsole gc = new GraphicsConsole(winW,winH,"Memory Card Game");

    // Mouse Location:
    int mx=-200,my=-200;

    // Calling of the Objects:
    int num_of_images = 16;
    Images [] images = new Images [num_of_images];
    Images [] backImg = new Images [num_of_images];

    // Creation of the Grid
	static final int GRIDW = 4;	 //or call it GRID
	int[][] board = new int[GRIDW][GRIDW];

    // The number of clicks 
    int num_of_clicks = 0;

    // Fruits 
    String [] possiblefruits = {"001-peach.png", "002-grape.png", "003-orange.png", "004-apple.png", "005-banana.png", "006-coconut.png", "007-pineapple.png", "008-eggplant.png"};

    // The Sleep Time
    int sleeptime = 500; 

    // The total amount of matched pairs which will be 8 but this will get added too each time the player gets a pair right
    int num_of_matched_pairs = 0;

    // The 2 card choices
    int card1=-1;
    int card2=-1;

    // The amount of guesses the user has performed
    int num_guesses = 0;
    
    MemoryCard(){
        // The main loop of the game
        setup();
        
        make_images();

        // This makes all fruit images not visable in the beginning
        for (int i = 0; i < num_of_images; i++){
            images [i].visable = false; 
        }

        // This method creates the beginning game screen
        Begin_game();

        while(true){
            // This loop checks if the card is click and draws that onto the screen
            checkCardClick();
            drawGraphics();
            gc.sleep(100);
            // This checks if the user has gotten 8 matched pairs as number of images is 16.
            // If the user has gotten the 8 matched pairs, it will break out of the code and print out the game over screen 
            if (num_of_matched_pairs == num_of_images / 2) {
                break;
            }
        }
        Game_over();
    }

    void setup(){
        // This set ups the entire game 
        gc.setLocationRelativeTo(null);
		gc.setAntiAlias(true);
		gc.setStroke(2);
		gc.setBackgroundColor(Color.decode("#CCDDFF"));
		gc.clear();
		gc.enableMouse(); //enables mouse clicking
    }

    void checkCardClick(){
        // Only lets 2 clicks and will check if the 2 cards are the same and results in too them disappearing 
        if (gc.getMouseClick() == 0) return;

        mx = gc.getMouseX();
        my = gc.getMouseY();

        // First Click 
        if (num_of_clicks == 0) {
            for (int i = 0; i < num_of_images; i++){
                // This says that if any of the images get clicked, and the images are not visable, run this code
                if (images[i].contains(mx, my) && !images[i].visable) {
                    // This shows the image behind the card
                    images[i].visable = true;
                    // This uses the card1 choice and stores the number associated with each of the images
                    card1 = images[i].storenum;
                    // Then the number of clicks goes up to make sure when the player clicks again, it runs a different code instead of this one
                    num_of_clicks++;
                    // The number of guesses goes up as the player has now clicked on their first guess duo. 
                    // To make sure that they don't click the same card 2 times and be able to see all the images behind the cards, the number of guesses goes up on the first click instead of the second click
                    num_guesses++;
                    break;
                }
            }
        }

        // Second Click: Once the player has clicked one time already
        else if (num_of_clicks == 1){
            for ( int i = 0; i < num_of_images; i++){
                // This says that if any of the images get clicked, and the images are not visable, run this code
                if (images[i].contains(mx, my) && !images[i].visable) {
                    // Same thing as the above code with first click
                    images[i].visable = true;
                    drawGraphics();
                    gc.sleep(sleeptime);
                    num_of_clicks++;
                    card2 = images[i].storenum;
                    break;
                }
            }
            // This checks if the card1's value has changed or not. If it has changed, this code will run
            if (card1 != -1 && card2 != -1){
                // This code checks if the 2 card's values are the same 
                if (card1 == card2){
                    for (int i = 0; i < num_of_images; i++) {
                        // Since the values are the same, the card image will now be gone and the fruit image will remain
                        if (images[i].storenum == card1) {
                            backImg[i].visable = false;
                        }
                    }
                    // Since the cards match, the number of matched cards raise because one of the pairs are now gone
                    num_of_matched_pairs++;
                    // This will now reset the amount of clicks to continue the game
                    resetClicks();
                }
                else {
                    // Reset the amount of clicks to continue the game
                    resetClicks();
                    // This now makes the fruit image disapear and the card image show again
                    for (int i =0; i < num_of_images; i ++){
                        if (backImg[i].visable == true){
                            images[i].visable = false;
                        }
                    }
                }
            }
        }
    }       
    

    void resetClicks(){
        num_of_clicks = 0;
    }

    void make_images() {
        // Creates the images and will be creating the card that will be covering the images
        int top_start = 12;
        int left_start = 12;
        int spacingx = 60;
        int spacingy = 180;
        
        int[] imageCounts = new int[possiblefruits.length]; // Array to keep track of image counts
    
        for (int i = 0; i < num_of_images; i++) {
            
            int x = (90 + spacingx) * (i % GRIDW) + top_start;
            int y = (20 + spacingy) * (i / GRIDW) + left_start;
    
            // Find an image that has been printed less than twice
            int randomIndex;
            do {
                randomIndex = (int) (Math.random() * possiblefruits.length);
            } while (imageCounts[randomIndex] >= 2);
    
            images[i] = new Images(x, y, possiblefruits[randomIndex]);
            imageCounts[randomIndex]++; // Increment the count of the chosen image
        }

        for (int i = 0; i < num_of_images; i++ ){
            // This puts the card images on top of the fruit images
            int x = (90 + spacingx) * (i % GRIDW) + top_start;
            int y = (20 + spacingy) * (i / GRIDW) + left_start;
            backImg[i] = new Images(x,y,"Card.png");
        }
    }

    void drawGraphics(){		
        synchronized(gc){ 
			gc.clear();

			//Draw grid
			gc.setColor(Color.GRAY);			
			for (int i = 1; i < GRIDW; i++) {
				gc.drawLine(0, i * winH/GRIDW, winW, i * winH/GRIDW);
				gc.drawLine(i * winW/GRIDW, 0, i * winW/GRIDW, winH);
			}

            // Draw images
            for (int i = 0; i < images.length; i++) {
                if (images[i].getVisible()){
                    gc.drawImage(images[i].img, images[i].x, images[i].y, images[i].width, images[i].height);
                }
                else{
                    if (backImg[i].visable != false){
                        gc.drawImage(backImg[i].img, images[i].x, images[i].y, images[i].width, images[i].height);
                    }
                }
            }            
        }
    }

    void Game_over(){
        // This is the game over method that will be called once all the cards are gone
        gc.clear(); 
        gc.setColor(Color.WHITE);
        Font Gameover = new Font ( "Arial", Font.BOLD, 30);
        gc.setFont(Gameover);
        gc.drawString("Congrats on Finishing the Game! ", winW/2-225, winH/2);   
        gc.drawString("Here are your stats: ", winW/2-160, winH/2 + 40);
        stats();     
    }

    boolean Begin_game(){
        // This is the code for the starting screen where you have to press on the screen to load to the main game loop. 
        gc.setColor(new Color(255,255,255));
        Font Startgame = new Font ( "Arial", Font.BOLD, 40);
        gc.setFont(Startgame);
        gc.drawString("Welcome to Memory Card!! ", winW/2-250, winH/2);
        Font Caption = new Font ( "Arial", Font.BOLD, 25);
        gc.setFont(Caption);
        gc.drawString("Click the Screen to Start ", winW/2-160, winH/2+50);
        // The while true loop will keep this text on the screen until the mouse is clicked and then will begin the main loop
        while(true){
            if (gc.getMouseClick() > 0) {
                return true;
            }
            gc.sleep(50);
        }
    }

    void stats(){
        // This calculates the percentage of your accuracy by finding the amount of tries it took you to guess the 8 pairs
        gc.setColor(Color.white);
        Font Stats = new Font ( "Arial", Font.BOLD, 40);
        gc.setFont(Stats);
        double percentage = (double) num_of_matched_pairs / num_guesses * 100;
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedPercentage = df.format(percentage);
        gc.drawString("Guesses: " + num_guesses, winW/2-125, winH-90);
        gc.drawString("Accuracy: " + formattedPercentage + " %", winW/2-175, winH-40);
    }    
}
