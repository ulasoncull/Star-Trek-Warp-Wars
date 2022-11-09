import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

import java.util.Random;
import java.util.Scanner; 

import enigma.core.Enigma;

public class StarTrekWars {
	
	static enigma.console.Console cons = Enigma.getConsole("Star Trek Warp Wars", 250, 50);
	Scanner screen = new Scanner(System.in);
	static Player player = new Player();
	static Stack backpack = new Stack(8);
	static Computer[] comps = new Computer[30];
	static InputQueue inputQueue = new InputQueue();
	static InputQueue[] devices = new InputQueue[200];  // 50 may be small . arraylist will be fine .
	static Property playerProperty = new Property();
	static long startTime = System.currentTimeMillis();
	static long startTimeForP = System.currentTimeMillis();
	static boolean isGameStillWork = true;
	public static char[][] maze;
	static int energyCount=1;
	static int times = 1;
	static int times2 = 1;
	static int times3 = 1;
	static int times4 = 1;
	static int times5 = 1;
	static int times6 = 1;
	
	private KeyListener klis; 
	public int keypr;   // key pressed?   =0?
	public int rkey;    // key   (for press/release)
	
	static Scanner beforeGameScanner = new Scanner(System.in);
	Scanner exit = new Scanner(System.in);
    static String chosenMenuOption;
	int chosenMaze = 1;
    
	
	
	
	StarTrekWars(){
		
	    // defining first C element.
	    comps[0] = new Computer( 1 , 30);
	    
		boolean gameContinue = true;
		
		// menu loop
		while(gameContinue) {
			
			cons.getTextWindow().setCursorPosition(0, 5);
			Property.namePatternWriter();
			
			cons.getTextWindow().setCursorPosition(5, 20);
		    System.out.println(" Menu:");
		    System.out.println(" 1) Play");
		    System.out.println(" 2) How to Play?");
		    System.out.println(" 3) Maze Options");
		    System.out.println(" 4) Exit");
		    System.out.println();
		    System.out.println();
		    System.out.println("Please enter a number from the menu.");
		    
		    chosenMenuOption = beforeGameScanner.nextLine();
		    
		    switch(chosenMenuOption) {
		    
		    case "1":
		    	
		    	clearScreen();
		    	long startTime = System.currentTimeMillis();
		    	
		    	playerProperty.setEnergyStarted(true);
				playerProperty.setEnergyStartTime(System.currentTimeMillis());
				playerProperty.setPlayerEnergy(50);
				
				klis=new KeyListener() {
			         public void keyTyped(KeyEvent e) {}
			         public void keyPressed(KeyEvent e) {
			            if(keypr==0) {
			               keypr=1;
			               rkey=e.getKeyCode();
			            }
			         }
			         public void keyReleased(KeyEvent e) {}
			    };
			    cons.getTextWindow().addKeyListener(klis);
				
				playerProperty.setPlayerLife(5);
				
				//static maze was read thanks to this func.
			    maze = createMaze(chosenMaze);
			    if(maze == null) {
			    	System.exit(0);
			    }
			    
			    printTheMaze();
				
			    cons.getTextWindow().setCursorPosition(57, 22);
				System.out.println("Time    :   0.00");			// Because it needs to be like this at start
				
				// display firt 20 item at the begining.
				for(int k =0 ; k<20 ; k++) {
					refreshMazeWithNewItemFromInputQueue();
				}
				
				
				while(gameContinue) {
					
					if(gameOwer()) {
						clearScreen();
						System.out.println(" - -  GAME OVER - - - ");
						System.out.println("End-Game Score of the Human Player  =  " + (playerProperty.getPlayerScore()-Computer.getComputerScore() ) );
						try {
				            Thread.sleep(10_000);
				        } catch (InterruptedException ex) {
				            
				        }
						System.exit(0);
					}
					
					// features which need to happen simultaneously are below.
					
					
					// time ekrana 1sn de bir şekilde bastırılıyor.	
					if(System.currentTimeMillis() - startTime >= (1000 * times)) {
						playerProperty.setTime(times);
						cons.getTextWindow().setCursorPosition(57, 22);
						System.out.println("Time    :   " + playerProperty.getTime());
						InputQueue.updateDevices(devices ,maze);
						times++;
					}    
					
					Property.propertyWriter( playerProperty,cons ,backpack , inputQueue );
					
					// 3 sn de bir ınputQueuedan item ataması yapıyor.
					if(System.currentTimeMillis() - startTime >= (3000*times2)) {
						refreshMazeWithNewItemFromInputQueue();
						times2++;
					}
					
					// 5sn de bir ekrandaki 4 ve 5 treasure ları hareket ediyorlar.
					if(System.currentTimeMillis() - startTime >= (500*times3)) {
						maze = inputQueue.refresh4and5(maze);
						printTheMaze();
						times3++;		
					}
					
					
					// bir tuşa basılıyor mu ? basılıyorsa if e gir.
					if( isKeyPressed() ) {
						// rkey  basılan tuşun kodu.
						
						if( rkey == 37||rkey == 38||rkey == 39||rkey == 40 ) {  // ok tuşları basılmış burada 
							
							if( playerProperty.getPlayerEnergy()>0 && System.currentTimeMillis() - startTimeForP >= (250)) {
								maze = Pmovement(rkey);
								printTheMaze();
								startTimeForP = System.currentTimeMillis();
							}
						    else if(System.currentTimeMillis() - startTimeForP >= (500)) {
								maze = Pmovement(rkey);
								printTheMaze();
								startTimeForP = System.currentTimeMillis();
							}
							
						}else if( rkey == 87||rkey == 65||rkey == 83||rkey == 68) {  //  w=87 a=65 s=83  d=68
							if(!backpack.isEmpty()) {							// when bakpack is not empty, remove the top element and print according to WASD
								maze = removeFromBackpack(rkey);
								Property.propertyWriter( playerProperty,cons ,backpack , inputQueue );
							}
						}
						else if( rkey == 69 || rkey == 101) {

                            cons.getTextWindow().setCursorPosition(5, 25);
                            System.out.println("You are quitting! Goodbye!");
                            gameContinue = false;
                        }
					}
					
					
					if(playerProperty.isNewEnergyGained()) {
						energyCount = 0;
						playerProperty.setNewEnergyGained(false);
					}
					
					if(playerProperty.isEnergyStarted()) {						// Changing energy time every second, if there is energy
						if(System.currentTimeMillis() - playerProperty.getEnergyStartTime() >= (1000 * energyCount)) {
							playerProperty.setPlayerEnergy(playerProperty.getPlayerEnergy() - 1);
							energyCount++;
						}    
						if(playerProperty.getPlayerEnergy() == 0) {
							playerProperty.setEnergyStarted(false);
							energyCount = 0;
						}
					}
					
					
					
					
					
					// C hareketi yarım saniyede bir , enerjisi varsa ona göre if-Bloğuna koyarız.
					if(System.currentTimeMillis() - startTime >= (500*times5)) {
						
						int whichC=0;
						while(whichC<comps.length) {
							try {
								
								if(comps[whichC].TargetOfX == 0 &&  comps[whichC].TargetOfY == 0 ) {
									// target ı yok bunun, önce bir target seç.
									
									comps[whichC].ChooseOneTarget(maze);
									comps[whichC].RoadForComputer( maze);
									
								}else {
									// bir taget için c yi bir br hareket ettirecek.
									comps[whichC].RoadForComputer( maze);
								}
								
								pGetsCloseToC( comps[whichC].CursorOfX , comps[whichC].CursorOfY );
								comps[whichC]= Computer.isCcloseTraps( comps[whichC] );
								player = Computer.didCcatchPlayer(comps[whichC].CursorOfX , comps[whichC].CursorOfY);
								
							}catch(Exception e) {
								
							}
							
							
							whichC++;
						}
						times5++;		
					}
					
					if(gameContinue == false) {
                        try {
							Thread.sleep(1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                        System.exit(0);
                    }
					
				}
				
				
		    	break;
		    	
		    case "2":
		    	String doNext = Property.printGameRules(beforeGameScanner);
		    	if(doNext.equals("1"))
		    		chosenMenuOption = "1";
		    	
		    	break;
		    	
		    
		    	
		    case "3":
		    	Object chosenMazeByPlayer =  Property.printMazeOptions( chosenMaze, beforeGameScanner );
		    	if(chosenMazeByPlayer.equals("1") || chosenMazeByPlayer.equals("2") || chosenMazeByPlayer
		    			.equals("3")) {
		    		cons.getTextWindow().setCursorPosition(5, 30);
		    		System.out.println("You've changed your maze!");
		    		cons.getTextWindow().setCursorPosition(5, 31);
		    		System.out.println("Press 1 to start the game now!");
		    	}
		    	break;
		    	
		    case "4":
		    	gameContinue = false;
		    	System.out.println("Goodbye!!!");
		    	
		    	break;
		    	
		    default:
		    	System.out.println("Please enter valid option ! \n\n");
		    	break;
		    	
		    
		    }
		}
		
		
		
	}
	//FUNCTIONS HERE.
	
	
	boolean gameOwer() {
		// eğer oyun bitm koşullarından biri sağlanıyorsa return true ve oyun biter kendiliğinden
		
		int k=0;
		boolean filledUpMaze=true;
		
		for(int i = 0; i < 23; i++){
			   for(int j = 0; j < 55; j++) {
				   if(maze[i][j]==' ') {
					   filledUpMaze= false;
					   k=1;
					   break;
				   }
				   
			   }
			   if(k==1) {
				   break;
			   }
		}
		
		
		if(Property.getPlayerLife()==0) {
			return true;
		}else if(filledUpMaze) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	static void pGetsCloseToC(int CursorOfX, int CursorOfY) {
		
		if(maze[CursorOfX - 1][CursorOfY - 1] == 'P') {
			backpack.pop();
			backpack.pop();
		}
		else if(maze[CursorOfX][CursorOfY - 1] == 'P') {
			backpack.pop();
			backpack.pop();
		}
		else if(maze[CursorOfX + 1][CursorOfY - 1] == 'P') {
			backpack.pop();
			backpack.pop();
		}
		else if(maze[CursorOfX - 1][CursorOfY] == 'P') {
			backpack.pop();
			backpack.pop();
		}
		else if(maze[CursorOfX + 1][CursorOfY] == 'P') {
			backpack.pop();
			backpack.pop();
		}
		else if(maze[CursorOfX - 1][CursorOfY + 1] == 'P') {
			backpack.pop();
			backpack.pop();
		}
		else if(maze[CursorOfX][CursorOfY + 1] == 'P') {
			backpack.pop();
			backpack.pop();
		}
		else if(maze[CursorOfX + 1][CursorOfY + 1] == 'P') {
			backpack.pop();
			backpack.pop();
		}
	
	
	}
	
	
	
	public static void clearScreen() { 
		
		for(int j = 0; j < 100; j++) {
			 System.out.println();
		 } 
			 
	 }
	
	public boolean isKeyPressed()
	   {
		   if(this.keypr == 1)
		   {
			   this.keypr = 0;
			   return true;
		   }
		   return false;
	   }
	
	
	static char[][] Pmovement(int key ){
		char item;
		
		if(key == 38  && maze[player.getPlayerX() - 1][player.getPlayerY()] != '#')
		{
			item = maze[player.getPlayerX() - 1][player.getPlayerY()];
			updateBackback(item);
			
			maze[player.getPlayerX()][player.getPlayerY()] = ' ';
			maze[player.getPlayerX() - 1][player.getPlayerY()] = 'P';
			player.setPlayerx( player.getPlayerX()-1 );
			return maze;
			
			
		}else if(key == 40  && maze[player.getPlayerX() + 1][player.getPlayerY()] != '#')
		{
			item = maze[player.getPlayerX() + 1][player.getPlayerY()];
			updateBackback(item);
			
			maze[player.getPlayerX() + 1][player.getPlayerY()] = 'P';
			maze[player.getPlayerX()][player.getPlayerY()] = ' ';
			player.setPlayerx( player.getPlayerX()+1 );
			return maze;
			
			
		}else if(key == 39  && maze[player.getPlayerX()][player.getPlayerY()+1] != '#')
		{
			item = maze[player.getPlayerX()][player.getPlayerY()+1];
			updateBackback(item);
			
			maze[player.getPlayerX()][player.getPlayerY()] = ' ';
			maze[player.getPlayerX()][player.getPlayerY()+1] = 'P';
			player.setPlayerY( player.getPlayerY()+1 );
			return maze;
			
		}else if(key == 37  && maze[player.getPlayerX()][player.getPlayerY()-1] != '#')
		{
			item = maze[player.getPlayerX()][player.getPlayerY()-1];
			updateBackback(item);
			
			maze[player.getPlayerX()][player.getPlayerY()-1] = 'P';
			maze[player.getPlayerX()][player.getPlayerY()] = ' ';
			player.setPlayerY( player.getPlayerY()-1 );
			return maze;
		}else {
			return maze;
		}
	}
	
	public static char[][] removeFromBackpack(int key) { 

		if( backpack.peek().toString().charAt(0)== '*' ||  backpack.peek().toString().charAt(0) == '=') {
			if ((key == 87 || key == 119) && maze[player.getPlayerX() - 1][player.getPlayerY()] == ' ') {
		    	maze[player.getPlayerX() - 1][player.getPlayerY()] = (char) backpack.pop();					// put in one upward in maze.
		    }
		    else if ((key == 65 || key == 97) && maze[player.getPlayerX()][player.getPlayerY() - 1] == ' ') {
		    	maze[player.getPlayerX()][player.getPlayerY() - 1] = (char) backpack.pop();    				// put in one left in maze.
		    }
		    else if ((key == 83 || key == 115) && maze[player.getPlayerX() + 1][player.getPlayerY()] == ' ') {
		    	maze[player.getPlayerX() + 1][player.getPlayerY()] = (char) backpack.pop();    				// put in one downward in maze.
		    }
		    else if ((key == 68 || key == 100) && maze[player.getPlayerX()][player.getPlayerY() + 1] == ' ') {
		    	maze[player.getPlayerX()][player.getPlayerY() + 1] = (char) backpack.pop();    				// put in one right in maze.
		    }
		}
		else {
			backpack.pop();
		}
	    return maze;
	    
}
	
	
	public static void updateBackback( char newElement ) {
		
		if( newElement != ' ' && newElement != 'C' && !backpack.isFull() ) {
			
			// backpackMatch method u üzerine geldiğim şeyin işlemlerini gerçekleştiriyor sadece ve eşleşme varsa true.
			boolean matchMade = Backpack.backpackMatch(newElement, playerProperty, backpack) ;
			if(matchMade == true) {
				playerProperty.setEnergyStartTime(System.currentTimeMillis());
				playerProperty.setEnergyStarted(true);
			}
			
		}
	}
	
	
	public static void  refreshMazeWithNewItemFromInputQueue() {   
		// this func take a item from inputqueue and add it into the maze and print the maze.
		Object newItem = inputQueue.InputQueue.dequeue();
		inputQueue.addItemToInputQueue();
		addNewItemintoMaze( newItem );
		printTheMaze();
	}
	
	
	public static void printTheMaze(){
		cons.getTextWindow().setCursorPosition(0, 0);
		
		for(int i = 0; i < 23; i++)
		{
			   for(int j = 0; j < 55; j++)
			   {
				   System.out.print(maze[i][j]);
			   }
			   System.out.println();
		}
		
	}
	
	
	public static char [][] addNewItemintoMaze( Object item)
	{
		Random rand = new Random();
		while(true)
		{
			int index_X = rand.nextInt(23);
			int index_Y = rand.nextInt(55);
			
			if(maze[index_X][index_Y] == ' ')
			{
				maze[index_X][index_Y] = ( String.valueOf(item) ).charAt(0) ;
				checkIsItImportantItem(( String.valueOf(item) ).charAt(0) ,index_X , index_Y);
				
				return maze;
			}
		}
	}
	
	
	
	
	static void checkIsItImportantItem(char newItem ,int apsis , int ordinat) {
		// new item form inputQueue is C or not , this method clarify this.
		// if new C will be appear at the screen, method create new computer object.
		if(newItem == 'C') {
			
			int nbrOfCs =Computer.nbrOfCs;
			
			// yeni eklenen eleman C ve onun objesi oluşturuluyor.
			comps[nbrOfCs] = new Computer(apsis , ordinat);
		}
		else if(newItem == '*'){
			
			int nbrOfDevices = InputQueue.nbrOfAllDevices ;
			devices[nbrOfDevices] = new InputQueue( apsis, ordinat);
			
		}else if(newItem == '=') {
			int nbrOfDevices = InputQueue.nbrOfAllDevices ;
			devices[nbrOfDevices] = new InputQueue( apsis, ordinat);
			
		}
		
		
	}
	
	
	
	
	public static char[][] createMaze(int chosenMaze) {
		try 
		{
			File file = new File("mazeDefault.txt");
			
			if(chosenMaze == 2 ) {
				file = new File("mazeEasy.txt");
			}
			else if(chosenMaze == 3) {
				file = new File("mazeHard.txt");
			}
			else {					// It is checked already if chosenMaze is something unwanted. So 'else' becomes 'chosenMaze = 1'.
				file = new File("mazeDefault.txt");
			}
			
			
		   FileReader fileReader = new FileReader(file);
		   String line;
		   char[][] maze = new char[23][55];
		
		   BufferedReader br = new BufferedReader(fileReader);
		   
		   int i = 0;
		   int j = 0;
		
		   while((line = br.readLine()) != null)
		   {
			   char[] tempChars = line.toCharArray();
			   while(j < 55)
			   {
				   maze[i][j] = tempChars[j];
				   j++;
			   }
			   i++;
			   j=0;
		   }
		
		   br.close();
		   		   
		   return maze;
		   }
		   catch (Exception e) 
		   {
			   System.out.println("\nAn error occured while maze were being read!");
			   return null;
		   }
	}

}
