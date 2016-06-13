package raymondBots;

import java.util.HashMap;
import java.util.Set;

/**
 * MyClass - a class by (your name here)
 */
public class GortDirectionDriveSupport
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
	private EnemyBotRecord lastScannedBot;
	private EnemyBotRecord attackTarget;
	private int consecutiveScan = 0;
	private boolean drive = false;
	private boolean shoot = false;
	/**
	 * 	   the Field
	 *   |  2  |  4  |
	 *   |  1  |  3  |
	 */
	private int myQuadrant; //LowerLeft=1, UpperLeft=2, LowerRight=3, UpperRight=4


	/**
	 *  Support Constructor
	 */
	public GortDirectionDriveSupport(){
		directionArr = new char[]{'a'};
		distanceArr = new int[]{5};
		botRecords = new RecordCollection();
	}

	/**
	 *  Getters - Methods to get information
	 */
	public String getCourseComing(){
		String out = "";
		for(int i=0; i < directionArr.length; i++){
			out = out+directionArr[i]+":"+distanceArr[i]+", ";
		}
		return out;
	}
	public char[] getDirections(){
		return directionArr;
	}
	public int[] getDistances(){
		return distanceArr;
	}
	public boolean getDriveDesion(){
		return drive;
	}
	public boolean getShootingDesion(){
		return shoot;
	}
	public String getLocationString(){
		return "X:"+myX+" Y:"+myY+" Quad:"+myQuadrant;
	}
	public String getMySeenBots(){
		return botRecords.showAllRobotNames();
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
	public void turnOffShooting(){
		shoot = false;
	}
	public void evadeFrontalHits(String name){
		// use scan information to decide to attack or run.
		directionArr = new char[]{'r','b','l'};
		distanceArr = new int[]{90,30,85};
	}

	public void scannedARobot(String n, double l, double d, double b){
		System.out.println(n+" cnt="+botRecords.size());
		botRecords.checkRecord(n,l,d,b);
		// First round, there is no last bot...need to set it.
		if(lastScannedBot == null){
			lastScannedBot = botRecords.getBotByName(n);
		}
		else{
			if(n.equals(lastScannedBot.name)){
				consecutiveScan++;
			}
			else{
				consecutiveScan = 0;
				lastScannedBot = botRecords.getBotByName(n);
			}
		}

		if(attackTarget == null){
			attackTarget = botRecords.getBotByName(n);
			shoot = true;

			if(attackTarget.distance > 55){
				drive = true;
				directionArr = new char[]{'r','a'};
				distanceArr = new int[2];
				distanceArr[0] = (int) attackTarget.bearing;
				distanceArr[1] = (int) attackTarget.distance - 52;
			}
			else{
				drive = false;
			}
		}

	}
	public void robotDemise(String name){
		if( attackTarget.name == name ){
			attackTarget = null;
			shoot = false;
		}
		botRecords.drop(name);
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

	//ignore for now
	private EnemyBotRecord calculateTarget(){
		if(botRecords.size() == 1){
			return botRecords.getOne();
		}
		return null;
	}

}


/**
 *  THIS IS JUST A LIST OF ROBOTS
 */
class RecordCollection{
	HashMap<String, EnemyBotRecord> allBotList;

	public RecordCollection(){
		allBotList = new HashMap<>();
	}

	public EnemyBotRecord getBotByName(String n){
		return allBotList.get(n);
	}
	public EnemyBotRecord getOne(){
		String ns = allBotList.keySet().iterator().next();
		return allBotList.get(ns);
	}
	public void drop(String n){
		allBotList.remove(n);
	}
	public void checkRecord( String n, double l, double d, double b ){
		//System.out.println("Inside: checkRecord. Name="+n);
		EnemyBotRecord now = allBotList.get(n);
		if (now == null) {
			allBotList.put(n, new EnemyBotRecord(n,l,d,b));
		}
		else{
			now.updateAll(l,d,b);
		}
	}

	public String showAllRobotNames(){
		String out = "";
		for(String n : allBotList.keySet()){
			out = n+", "+out;
		}
		return out;
	}

	public int size(){
		return allBotList.size();
	}
}

/**
 * THIS IS AN OBJECT TO STORE ROBOT ATTRIBUTES
 */
class EnemyBotRecord {
	public String name; // bot name
	public double life;
	public double distance;
	public double bearing;
	public int dangerLevel=0; //rank toughness of enemy bot

	public EnemyBotRecord(String n, double l, double d, double b){
		name = n;
		life = l;
		distance = d;
		bearing = b;
	}

	public void updateAll(double l, double d, double b){
		life = l;
		distance = d;
		bearing = b;
	}

	// Attempt to categorize this enemy bot
	public int getDanger(){
		dangerLevel = 4;
		if (life > 110){
			dangerLevel = 1;
		}
		else if(life > 90){
			dangerLevel = 2;
		}
		else if(life > 40){
			dangerLevel = 3;
		}
		return dangerLevel;
	}

	public String toString(){
		return "Name: "+name+", Energy: "+life+" Dist: "+distance+" Br: "+bearing+" Danger: "+dangerLevel;
	}

}
