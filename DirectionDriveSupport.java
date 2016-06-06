package raymondBots;

import java.util.HashMap;

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

//class RecordCollection{
//	HashMap<String, EnemyBotRecord> allBotList = new HashMap<>();
//}

class EnemyBotRecord {
	public String name; // bot name
	public double life;
	public double distance;
	public double bearing;
	public int dangerLevel = 0; //rank toughness of enemy bot

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
		else if( life < 50 ){
			dangerLevel = 1;
		}

	}

	public String toString(){
		return "Name: "+name+", Energy: "+life+" Dist: "+distance+" Br: "+bearing+" Danger: "+dangerLevel;
	}

}
