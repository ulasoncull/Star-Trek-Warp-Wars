import java.util.Random;

public class InputQueue {
	

	int sizeOfInputQueue = 15;
	Queue probabilityInputQueue = new Queue(40);
	Queue InputQueue = new Queue(sizeOfInputQueue);
	
	// these vaiables need 25sn rule for devices.
	int apsisOfDevice;
	int ordinatOfDevice;
	int leftTimeOfDevice =0;	// = device
	static int nbrOfAllDevices=0;
	
	
	
	InputQueue(int x ,int y ){
		// warp device defination constactor.
		apsisOfDevice= x ;
		ordinatOfDevice =y ;
		leftTimeOfDevice=25;
		nbrOfAllDevices++;
	}
	
	InputQueue(){
		fillUpPropabilityInputQueue();
		
		for(int i =0; i<sizeOfInputQueue ; i++) {
			addItemToInputQueue();
		}
	}
	
	
	static char[][] updateDevices(InputQueue[] devices , char[][]maze){
		// every 1 sec update devices time.
		for(int deviceIndex =0; deviceIndex<devices.length; deviceIndex++) {
			
			try {
				
				if(devices[deviceIndex].leftTimeOfDevice == 0) {
					maze[devices[deviceIndex].apsisOfDevice][devices[deviceIndex].ordinatOfDevice] = ' ';
					devices[deviceIndex] = null;
				}else {
					devices[deviceIndex].leftTimeOfDevice--;
				}

				
			}catch(Exception e) {
				
			}
		}
		return maze;
	}
	
	void printInputQueue() {
		
		System.out.print("-> ");
		for(int i =0; i<sizeOfInputQueue ; i++) {
			Object InputQueueItem = InputQueue.dequeue();
			System.out.print(InputQueueItem );
			InputQueue.enqueue(InputQueueItem);
			
		}
	}
	
	void fillUpPropabilityInputQueue() {
		String[] probabilityInputQueueElements = new String[]{"1", "2","2", "3","3", "4","4","4","4","4", "5","5","5","=","=","*","C","C","5","5","3","3","3","3","2","2","2","2","2","2","1","1","1","1","1","1","1","1","1","1","1"};
		
		// probabilityInputQueue bu şeyin içinde null olmayan kadar donsün aşağidaki loop.
		
		int i =0;
		while( i< 40) {
			probabilityInputQueue.enqueue(probabilityInputQueueElements[i] );
			i++;
		}
	}
	
	void addItemToInputQueue() {					// this func add one item into ınputQueue which we see on the screen.
		Object item = null;
		Random rand = new Random();
		int newItemsOrderForInputQueue = rand.nextInt(40) +1 ;
		Queue tempQ = new Queue( newItemsOrderForInputQueue );
		
		
		for(int k =0; k<newItemsOrderForInputQueue ; k++) {
			item = probabilityInputQueue.dequeue();
			tempQ.enqueue(item);
			
		}
		
		//Ekranda gözüken inputQueue ya bir eleman ekliyorum.
		InputQueue.enqueue(item);
		
		// olasılık queue dan çıkardığım her şeyi gerisin geri yerine ekledim ki her zaman 40 elemanı olsun.
		while(!tempQ.isEmpty()) {
			probabilityInputQueue.enqueue( tempQ.dequeue() );
		}
	}
	
	public static char [][] findApropriatePlaceNearby(char item, int i , int j, char [][] maze) {
		Random rand = new Random();
		boolean flag = true;
		int counter = 0;
		
		while(flag) {
			counter++;
			int direction = rand.nextInt(1, 5);
			   switch(direction) {
			   case 1:
				   if(maze[i+1][j] == ' ') {
					   maze[i+1][j] = item;
					   flag = false;
				   }
				   break;
			   case 2:
				   if(maze[i-1][j] == ' ') {
					   maze[i-1][j] = item;
					   flag = false;
				   }
				   break;
			   case 3:
				   if(maze[i][j+1] == ' ') {
					   maze[i][j+1] = item;
					   flag = false;
				   }
				   break;
			   case 4:
				   if(maze[i][j-1] == ' ') {
				      maze[i][j-1] = item;
				   	  flag = false;
				   }
				   break;
			   }
			   
			   //if a nbr is stack, method have done nothing, sorry.
			   if(counter == 100) {
				   flag = false;
			   }
		}
		return maze;
		
	}
	
	public char[][] refresh4and5(char [][] maze ){
		for(int i = 0; i < 23; i++){
			for(int j = 0; j < 55; j++){

				   char itemFromMaze = maze[i][j];
				   if(itemFromMaze == '4' ) {
					   maze[i][j] = ' ';
					   maze = findApropriatePlaceNearby(itemFromMaze, i, j , maze);
					   
					   }
				   else if(itemFromMaze == '5' ) {
					   maze[i][j] = ' ';
					   maze = findApropriatePlaceNearby(itemFromMaze, i, j , maze);
				   }
			 }
	    }
		return maze;
	}
	
	
	
	

}
