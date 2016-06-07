package raymondBots;

import java.util.HashMap;

/**
 * MyClass - a class by (your name here)
 */
public class DirectionDriveSupport
{
	private char[] directionArr;	
	private int[] distanceArr;
	private RecordCollection botRecords;
	private int width = 1;
	private int halfwidth = 1;
	private int hieght = 1;
	private int halfhieght = 1;
	private int myX = 1;
	private int myY = 1;
	/**
	 * 	   the Field
	 *   |  2  |  4  |
	 *   |  1  |  3  |
	 */
	private int myQuadrant; //LowerLeft=1, UpperLeft=2, LowerRight=3, UpperRight=4


	/**
	 *  Support Constructor
	 */
	public DirectionDriveSupport(){
		directionArr = new char[]{'a'};
		distanceArr = new int[]{20};
		botRecords = new RecordCollection();
	}

	/**
	 *  Getters - Methods to get information
	 */
	public char[] getDirections(){
		return directionArr;
	}
	public int[] getDistances(){
		return distanceArr;
	}
	public String getLocationString(){
		return "X:"+myX+" Y:"+myY+" Quad:"+myQuadrant;
	}


	/**
	 * Setters - Methods to give information, with nothing in return
	 */
	// [0,0] is the lower left corner
	public void myField( double w, double h){
		width = (int) w;
		hieght = (int) h;
		halfwidth = width / 2;
		halfhieght = hieght /2;
	}

	// [0,0] is the lower left corner
	public void myPosition( double x, double y){
		myX = (int) x;
		myY = (int) y;
		myQuadrant = calculateQuadrant(myX,myY);
	}

	public void evadeFrontalHits(String name){
		// use scan information to decide to attack or run.
		directionArr = new char[]{'r','b','l'};
		distanceArr = new int[]{90,30,85};
	}


	/**
	 * Private Methods - Only available in this class
	 */
	private int calculateQuadrant(int x, int y){
		int currentQuadrant = 0;
		if( x < halfwidth && y < halfhieght ){
			currentQuadrant = 1;
		}
		else if( x > halfwidth && y < halfhieght ){
			currentQuadrant = 3;
		}
		else if( x < halfwidth && y > halfhieght ){
			currentQuadrant = 2;
		}
		else if( x > halfwidth && y > halfhieght ){
			currentQuadrant = 4;
		}
		return currentQuadrant;
	}

}

class RecordCollection{
	HashMap<String, EnemyBotRecord> allBotList;

	public RecordCollection(){
		allBotList = new HashMap<>();
	}

	public void checkRecord( String n, double l, double d, double b ){
		EnemyBotRecord now = allBotList.get(n);
		if (now != null) {
			allBotList.put(n, new EnemyBotRecord(n,l,d,b));
		}
		else{
			now.updateAll(l,d,b);
		}
	}
}

class EnemyBotRecord {
	public String name; // bot name
	public double life;
	public double distance;
	public double bearing;
	public int dangerLevel = 0; //rank toughness of enemy bot
	public int consecutiveScan = 0;

	public EnemyBotRecord(String n, double l, double d, double b){
		name = n;
		life = l;
		distance = d;
		bearing = b;

		// Attempt to categorize this enemy bot
		if (life > 99){
			dangerLevel = 3;
		}
		else if( life > 50 && distance < 120){
			dangerLevel = 2;
		}
		else if( life < 50 || distance < 55){
			dangerLevel = 1;
		}

	}

	public void updateAll(double l, double d, double b){
		life = l;
		distance = d;
		bearing = b;
	}

	public String toString(){
		return "Name: "+name+", Energy: "+life+" Dist: "+distance+" Br: "+bearing+" Danger: "+dangerLevel;
	}

}
