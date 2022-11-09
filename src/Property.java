import java.util.Scanner;

public class Property {
	
	//private Stack playerBackpack;
	private int playerEnergy;
	private int playerScore;
	static int playerLife=5;
	private long time;
	private boolean energyStarted;
	private long energyStartTime;
	private boolean newEnergyGained;
	
	public boolean isNewEnergyGained() {
		return newEnergyGained;
	}
	public void setNewEnergyGained(boolean newEnergyGained) {
		this.newEnergyGained = newEnergyGained;
	}
	public boolean isEnergyStarted() {
		return energyStarted;
	}
	public void setEnergyStarted(boolean energyStarted) {
		this.energyStarted = energyStarted;
	}
	public long getEnergyStartTime() {
		return energyStartTime;
	}
	public void setEnergyStartTime(long energyStartTime) {
		this.energyStartTime = energyStartTime;
	}
	public int getPlayerEnergy() {
		return playerEnergy;
	}
	public void setPlayerEnergy(int playerEnergy) {
		this.playerEnergy = playerEnergy;
	}
	public int getPlayerScore() {
		return playerScore;
	}
	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}
	public static int getPlayerLife() {
		return playerLife;
	}
	public static void setPlayerLife(int playerL) {
		playerLife = playerL;
	}
	public double getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
    static Object printMazeOptions(Object chosenMaze , Scanner beforeGameScanner ) {
		
		StarTrekWars.clearScreen();
		enigma.console.Console cons = StarTrekWars.cons;
		boolean trueInput = false;
		while(!trueInput) {
		    cons.getTextWindow().setCursorPosition(5, 2);
		    System.out.println(" 1) Default maze");
		    cons.getTextWindow().setCursorPosition(5, 3);
		    System.out.println(" 2) Easy maze");
		    cons.getTextWindow().setCursorPosition(5, 4);
		    System.out.println(" 3) Hard maze");
		    cons.getTextWindow().setCursorPosition(5, 5);
		    System.out.println("Please enter the number of the maze you want to play. If you don't chose, you will play with the default maze.");
		    cons.getTextWindow().setCursorPosition(5, 6);
		    System.out.println("Press 0 (zero) to go back to the menu.");
		    cons.getTextWindow().setCursorPosition(5, 7);
		    chosenMaze = beforeGameScanner.nextLine();
		    
		    if(chosenMaze.equals("0")) {
		    	chosenMaze = 1;
		    	trueInput = true;
		    }
		    else if(chosenMaze.equals("1") || chosenMaze.equals("2") || chosenMaze.equals("3")) {
		    	trueInput = true;
		    	cons.getTextWindow().setCursorPosition(5, 7);
			    System.out.println("You've changed your maze!");
			    cons.getTextWindow().setCursorPosition(5, 8);
			    System.out.println("Press 1 to start the game now!");
		    }
		    else {
		    	cons.getTextWindow().setCursorPosition(5, 7);
				System.out.println("                       ");
			    cons.getTextWindow().setCursorPosition(5, 8);
		    	System.out.println("Please don't enter invalid value.");
		    }
		
		}
	    
		StarTrekWars.clearScreen();
		return chosenMaze;
	}

    static String printGameRules(Scanner beforeGameScanner) {
		
		String doNext = null;
		
		StarTrekWars.clearScreen();
		enigma.console.Console cons = StarTrekWars.cons;

		cons.getTextWindow().setCursorPosition(5, 1);
		System.out.println("HOW TO PLAY:");
		cons.getTextWindow().setCursorPosition(5, 2);
    	System.out.println(" Star Trek Warp Wars Game is a arcade game which is played in a maze.");
	    cons.getTextWindow().setCursorPosition(5, 3);
    	System.out.println(" You can move the player 'P' by arrow keys.");
	    cons.getTextWindow().setCursorPosition(5, 4);
    	System.out.println(" Trap devices (=) stops the numbers and C robots.");
    	cons.getTextWindow().setCursorPosition(5, 5);
	    System.out.println(" Warp devices (*) warps the numbers and C robots.");
	    cons.getTextWindow().setCursorPosition(5, 6);
    	System.out.println(" You can get boost energy and move faster by matching numbers in your backpack.");
    	cons.getTextWindow().setCursorPosition(5, 7);
	    System.out.println(" You can remove items from backpack. And if that item is a trap or warp device,");
    	cons.getTextWindow().setCursorPosition(5, 8);
	    System.out.println("you can put it back to the maze by using WASD keys.");
    	cons.getTextWindow().setCursorPosition(5, 9);
	    System.out.println(" While playing the game, you can exit by pressing E.");					// Add exiting from game with E
	    cons.getTextWindow().setCursorPosition(5, 10);
	    System.out.println("Press 0 (zero) to go back to the menu.");
	    
	    boolean trueInput = false;
		while(!trueInput) {
			cons.getTextWindow().setCursorPosition(5, 11);
			doNext = beforeGameScanner.nextLine();
			if(doNext.equals("0")) {
				StarTrekWars.clearScreen();
				trueInput = true;
			}
			else {
				cons.getTextWindow().setCursorPosition(5, 11);
				System.out.println("                       ");
				cons.getTextWindow().setCursorPosition(5, 12);
		    	System.out.println("Please don't enter invalid value.");
			}
				
		}
	    return doNext;
	}
	
	
	
	public static void propertyWriter(Property playerProperty ,enigma.console.Console cons , Stack backpack , InputQueue inputQueue) {
		Stack backpackTemp = new Stack(8);
		int emptyIndex = 0;
		while(!backpack.isFull()) {
			backpack.push(" ");
			emptyIndex++;
		}
		
		cons.getTextWindow().setCursorPosition(57, 0);
		System.out.println("INPUT");
		cons.getTextWindow().setCursorPosition(57, 1);
		System.out.println("<<<<<<<<<<<<<<<");
		cons.getTextWindow().setCursorPosition(57, 2);
		inputQueue.printInputQueue();
		cons.getTextWindow().setCursorPosition(57, 3);
		System.out.println("<<<<<<<<<<<<<<<");
		
		for(int i = 0; i < 8; i++) {
			backpackTemp.push(backpack.peek());
			cons.getTextWindow().setCursorPosition(60, i + 5);
			System.out.println("| " + backpack.pop() + " |");	
		}
		while(!backpackTemp.isEmpty()) {
			backpack.push(backpackTemp.pop());
		}
		for(int i = 0; i < emptyIndex; i++) {
				backpack.pop();
		}
		
		cons.getTextWindow().setCursorPosition(60, 13);
		System.out.println("+---+");
		cons.getTextWindow().setCursorPosition(60, 14);
		System.out.println("P.Backpack");
		if(playerProperty.getPlayerEnergy() >= 1000) {
			cons.getTextWindow().setCursorPosition(57, 16);
			System.out.println("P.Energy:   " + playerProperty.getPlayerEnergy());
		}
		else if(playerProperty.getPlayerEnergy() >= 100) {
			cons.getTextWindow().setCursorPosition(57, 16);
			System.out.println("P.Energy:   " + playerProperty.getPlayerEnergy() + " ");
		}
		else if(playerProperty.getPlayerEnergy() >= 10) {
			cons.getTextWindow().setCursorPosition(57, 16);
			System.out.println("P.Energy:   " + playerProperty.getPlayerEnergy() + "  ");
		}
		else {
			cons.getTextWindow().setCursorPosition(57, 16);
			System.out.println("P.Energy:   " + playerProperty.getPlayerEnergy() + "   ");
		}
		
		cons.getTextWindow().setCursorPosition(57, 17);
		System.out.println("P.Score :   " + playerProperty.getPlayerScore());
		cons.getTextWindow().setCursorPosition(57, 18);
		System.out.println("P.Life  :   " + playerProperty.getPlayerLife());
		cons.getTextWindow().setCursorPosition(57, 20);
		System.out.println("C.Score :   " + Computer.getComputerScore());
	}
	
	
	public static void namePatternWriter() {
		
		System.out.println(   "  #####  #######    #    ######     ####### ######  ####### #    #    #     #    #    ######  ######     #     #    #    ######   #####      \r\n" +
				 			" #     #    #      # #   #     #       #    #     # #       #   #     #  #  #   # #   #     # #     #    #  #  #   # #   #     # #     #     \r\n" +
				 			" #          #     #   #  #     #       #    #     # #       #  #      #  #  #  #   #  #     # #     #    #  #  #  #   #  #     # #           \r\n" +
				 			"  #####     #    #     # ######        #    ######  #####   ###       #  #  # #     # ######  ######     #  #  # #     # ######   #####      \r\n" +
				 			"       #    #    ####### #   #         #    #   #   #       #  #      #  #  # ####### #   #   #          #  #  # ####### #   #         #     \r\n" +
				 			" #     #    #    #     # #    #        #    #    #  #       #   #     #  #  # #     # #    #  #          #  #  # #     # #    #  #     #     \r\n" +
				 			"  #####     #    #     # #     #       #    #     # ####### #    #     ## ##  #     # #     # #           ## ##  #     # #     #  #####      \r\n" );
		System.out.println("																				   #####     #    #     # #######   " + "\n"+
						   "																				  #     #   # #   ##   ## #         " + "\n"+
						   "																				  #        #   #  # # # # #         " + "\n"+
						   "																				  #  #### #     # #  #  # #####     " + "\n"+
						   "																				  #     # ####### #     # #         " + "\n"+
						   "																				  #     # #     # #     # #         " + "\n"+
						   "																				   #####  #     # #     # #######   ");
		
	 }
	
	
	
}