package raymondBots;
import robocode.*;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html
/**
 * Gort - a robot by Jedi Master Raymond
 */
public class Gort extends Robot
{
	/**
	 * run: Gort wants to seek out enemies and destoy them one at a time. Pretty agressive style robot.
	 */
	boolean keepSearching = true;
	int pwr = 1;
	DirectionDriveSupport driver = new DirectionDriveSupport();

	public void run() {
		// Initialization of the robot
		setColors(Color.blue,Color.blue,Color.blue); // body,gun,radar
		int count = 0;
		dynamicDrive(driver.getDirections(), driver.getDistances());

		// Robot main loop
		while(true) {
			// Visually display myself white, when I am getting low on energy
			if(getEnergy() < 30){
				setColors(Color.white,Color.white,Color.blue); // body,gun,radar
			}		


			if(keepSearching){
				turnRight(66);
				ahead(10);
				System.out.println("Keep Searching");	
			}
			else{
				if(pwr > 0){
					fire(pwr);
				}
				if(count > 50){
					count = 0;
					pwr = 0;
					keepSearching = true;
					dynamicDrive(new char[]{'b'}, new int[]{50});
					//back(50);
					System.out.println("Break out Count");	
				}
			}
			count++;
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		EnemyBotRecord ebr = new EnemyBotRecord(e.getName(),e.getEnergy(),e.getDistance(),e.getBearing());
		out.println( ebr.toString() );	

		// Chase them down!!
		if(e.getDistance() > 30){
			System.out.println("Chase Down:"+e.getDistance());
			turnRight(e.getBearing());
			ahead(e.getDistance() - 50);
		}
		
		if( ebr.dangerLevel == 3 ){
			fire(4);
			pwr = 4;
		}
		else if( ebr.dangerLevel == 2 ){
			fire(2);
		}
		else if( ebr.dangerLevel == 1 ){
			pwr = 1;
		} 
		else{
			pwr = 0;
		}
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Check to see if I'm getting hit from straight on?
		double fromDirection = e.getBearing(); 
		if( fromDirection > -10 && fromDirection < 10 ){
			System.out.println("Direct Front");
			driver.evadeFrontalHits();
			dynamicDrive(driver.getDirections(), driver.getDistances());
		}
		else{
			back(10);
		}
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		System.out.println("Wall!");	
		back(20);
	}	
	
	/**
	 * onBulletHit: What to do when get hit by someone else
	 */
	public void onBulletHit(BulletHitEvent e){
		System.out.println("onBulletHit!");	
		keepSearching=false;
	}
	
	/**
	 * onBulletMissed: What to do when you miss, and hit a wall instead
	 */
	public void onBulletMissed(BulletHitEvent e){
		System.out.println("onBulletMissed!");	
		keepSearching=true;
	}
	
	/**
	 * dynamicDrive: accept a list of actions to take in series
	 */
	public void dynamicDrive(char[] directions, int[] distances){
		for(int i=0; i < directions.length; i++){
			switch(directions[i]){
				case 'a': ahead(distances[i]);
				case 'b': back(distances[i]);
				case 'l': turnLeft(distances[i]);
				case 'r': turnRight(distances[i]);
			}
		}
			
	}

	
}

