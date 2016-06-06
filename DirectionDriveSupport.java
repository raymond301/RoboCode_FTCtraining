package raymondBots;

/**
 * MyClass - a class by (your name here)
 */
public class DirectionDriveSupport
{
	private char[] directionArr;	
	private int[] distanceArr;
	
	public DirectionDriveSupport(){
		directionArr = new char[]{'a'};
		distanceArr = new int[]{20};
	}

	public char[] getDirections(){
		return directionArr;
	}
	public int[] getDistances(){
		return distanceArr;
	}	

	public void evadeFrontalHits(){
		directionArr = new char[]{'r','b','l'};
		distanceArr = new int[]{90,30,85};
	}

}

