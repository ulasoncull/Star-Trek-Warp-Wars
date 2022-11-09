import java.util.Random;

public class Computer {
	
	static int nbrOfCs=0;
	
	public int CursorOfX;
	public int CursorOfY;	
	public char text = 'C';
	public int TargetOfX;
	public int TargetOfY;
	public char target;
	private static int ComputerScore = 0;   // C treasure yedikçe bu değişken artacak uygun kurallar dahilinde.
	
	Computer(int CursorOfNewX, int CursorOfNewY) {
		CursorOfX = CursorOfNewX;
		CursorOfY = CursorOfNewY;
		TargetOfX=0;
		TargetOfY=0;
		nbrOfCs++;
	}
	
	public static int getComputerScore() {
		return ComputerScore;
	}
	
	static Player didCcatchPlayer(int apsisOfC , int ordinatOfC) {
		int apsisOfP = StarTrekWars.player.getPlayerX();
		int ordinatOfP = StarTrekWars.player.getPlayerY();
		
		Player p = StarTrekWars.player;
		
		if(apsisOfC==apsisOfP && ordinatOfC==ordinatOfP) {
			Property.setPlayerLife( Property.getPlayerLife()-1 );
			Player newplayer = new Player();
			return newplayer;
		}else {
			return p;
		}
		
		
		
		
	}
	
	static Computer isCcloseTraps( Computer c) {
		char[][] maze = StarTrekWars.maze;
		int CursorOfX = c.CursorOfX;
		int CursorOfY = c.CursorOfY;
		
		if(maze[CursorOfX-1][CursorOfY]=='*' || maze[CursorOfX-1][CursorOfY]=='=') {
			return null;
		}else if(maze[CursorOfX+1][CursorOfY]=='*' || maze[CursorOfX+1][CursorOfY]=='=') {
			return null;
		}else if(maze[CursorOfX][CursorOfY-1]=='*' || maze[CursorOfX][CursorOfY-1]=='=') {
			return null;
		}else if(maze[CursorOfX][CursorOfY+1]=='*' || maze[CursorOfX][CursorOfY+1]=='=') {
			return null;
		}else if(maze[CursorOfX+1][CursorOfY-1]=='*' || maze[CursorOfX+1][CursorOfY-1]=='=') {
			return null;
		}else if(maze[CursorOfX+1][CursorOfY+1]=='*' || maze[CursorOfX+1][CursorOfY+1]=='=') {
			return null;
		}else if(maze[CursorOfX-1][CursorOfY-1]=='*' || maze[CursorOfX-1][CursorOfY-1]=='=') {
			return null;
		}else if(maze[CursorOfX-1][CursorOfY+1]=='*' || maze[CursorOfX-1][CursorOfY+1]=='=') {
			return null;
		}
		else {
			return c;
		}
		
	}

	void ChooseOneTarget(char [][] maze) {
		// this method just determine one target for current C object. just for it.
		int flag = 0;
		
		for(int i = 0; i < 23; i++)
		{
			   for(int j = 0; j < 55; j++)
			   {
				   if(maze[i][j]=='1'||maze[i][j]=='2'||maze[i][j]=='3'||maze[i][j]=='4'||maze[i][j]=='5') {
					   
					   TargetOfX= i;
					   TargetOfY= j;
					   target = maze[i][j];
					   flag = 1;
					   break;
				   }				   
			   }
			   if(flag==1) {
				   break;
			   }			   
        }
		
	}
	
	


	char[][] RoadForComputer(char[][] maze) { 
		Random random = new Random();
		 	
		 int count =0;		
		 
		 while(count==0) {
			 int direction =9;
			 int up=1;
			 int down=2;
			 int left=3;
			 int right=4;	
			 
			 
			 					   // ulaş !
			 direction = random.nextInt(1,5);  // bu satırda C nin yönü en azından target a doğru olsun, yoksa sarhoş gibi hareket etmek dışında bir şey yapmıyor.    
			 
			 
			 char item;
			 if(direction == up && maze[CursorOfX-1][CursorOfY] != '#' ) {
				 
				 item=maze[CursorOfX-1][CursorOfY];
				 didCgetPoint(item);
				 
				 CursorOfX -= 1;
				 maze[CursorOfX][CursorOfY] = 'C';
				 maze[CursorOfX+1][CursorOfY] = ' ';					
				 
				 // buralara C nin score unu arttırıp arttırmama kodu yazılacak !!!				 
				 count++;					 
			 }
			 else if(direction == down && maze[CursorOfX+1][CursorOfY] != '#' ) {
				 
				 item = maze[CursorOfX+1][CursorOfY];
				 didCgetPoint(item);
				 
				 CursorOfX += 1;
				 maze[CursorOfX][CursorOfY] = 'C';	
				 maze[CursorOfX-1][CursorOfY] = ' ';					
				 			
				 count++;					 
			 }
			 else if(direction == left && maze[CursorOfX][CursorOfY-1]!='#' ) {
				 item=maze[CursorOfX][CursorOfY-1];
				 didCgetPoint(item);
				 
				 CursorOfY -= 1;
				 maze[CursorOfX][CursorOfY] = 'C';	
				 maze[CursorOfX][CursorOfY+1] = ' ';					
				 			
				 count++;					 
			 }
			 
			 else if(direction == right && maze[CursorOfX][CursorOfY+1]!='#' ) {
				 item = maze[CursorOfX][CursorOfY+1];
				 didCgetPoint(item);
				 
				 CursorOfY += 1;
				 maze[CursorOfX][CursorOfY] = 'C';	
				 maze[CursorOfX][CursorOfY-1] = ' ';					
				 			
				 count++;					 
			 }
			 
			 				 				 				 
		 }
		 
		 return maze;
		 
	}
	
	void didCgetPoint(char item ) {
		
		switch(item) {
		case'1':
			Computer.ComputerScore+=2;
			break;
		case'2':
			Computer.ComputerScore+=10;
			break;
		case'3':
			Computer.ComputerScore+=30;
			break;
		case'4':
			Computer.ComputerScore+=100;
			break;
		case'5':
			Computer.ComputerScore+=300;
			break;
		case'=':
			Computer.ComputerScore+=300;
			break;
		case'*':
			Computer.ComputerScore+=300;
			break;
		default:
			break;
			
		}
	}


	

	
	

}
