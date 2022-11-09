

public class Backpack {
	
	
	
	
	public static boolean backpackMatch(Object newElement, Property playerProperty, Stack backpack) { 			// When player tries to take a new element, call this!!!
		
		boolean matchMade = false;
		
		
		if(newElement == backpack.peek()) {			// New element is the same as the one on top of the backpack.
			

			if(  (char)newElement =='1' || (char)newElement == '2' || (char)newElement == '3' || (char)newElement == '4' || (char)newElement == '5') {
				
				switch( Character.getNumericValue( (char)newElement ) ) {
				
				case 1:
					backpack.push((char)newElement);
					playerProperty.setPlayerScore(playerProperty.getPlayerScore() + 1 );
					
					break;
				
				case 2:
					// energy 30 s
					backpack.pop();
					playerProperty.setPlayerEnergy( playerProperty.getPlayerEnergy() + 30 );
					playerProperty.setPlayerScore(playerProperty.getPlayerScore() + 5 );
					playerProperty.setEnergyStarted(true );
					playerProperty.setNewEnergyGained(true);
					matchMade = true;
					break;
				
				case 3:
					// give trap device
					backpack.pop();
					backpack.push('=');
					playerProperty.setPlayerScore(playerProperty.getPlayerScore() + 15);
					break;
				
				case 4:
					// energy 240 s
					backpack.pop();
					playerProperty.setPlayerEnergy( playerProperty.getPlayerEnergy() + 240 );
					playerProperty.setPlayerScore(playerProperty.getPlayerScore() + 50);
					playerProperty.setEnergyStarted(true );
					playerProperty.setNewEnergyGained(true);
					 matchMade = true;
					break;
				
				case 5:
					// give warp device
					backpack.pop();
					backpack.push('*');
					playerProperty.setPlayerScore(playerProperty.getPlayerScore() + 150);
					break;
				
				
				} // Switch ends	
			} // If for 1, 2, 3, 4 or 5 ends
			
			else if((char)newElement == '*' || (char)newElement == '=') { // warp or trap were taken
				backpack.push(newElement);
				// bu cihazı çantaya atıldı ama maze de hala duruyor olabilir, unutma bunu ! 
				
			} // If for * or = ends
			
			
		}else { // Nothing matches
			
			if((char)newElement == '1')
				playerProperty.setPlayerScore(playerProperty.getPlayerScore() + 1);
			else if((char)newElement == '2')
				playerProperty.setPlayerScore(playerProperty.getPlayerScore() + 5);
			else if((char)newElement == '3')
				playerProperty.setPlayerScore(playerProperty.getPlayerScore() + 15);
			else if((char)newElement == '4')
				playerProperty.setPlayerScore(playerProperty.getPlayerScore() + 50);
			else if((char)newElement == '5')
				playerProperty.setPlayerScore(playerProperty.getPlayerScore() + 150);
			
			backpack.push(newElement);
		}
		return matchMade;
	
	}
	
	
	
}
