package raymondBots;
import robocode.*;
import java.awt.Color;
import java.util.HashMap;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Robbie - a robot by (your name here)
 */
public class Robbie extends Robot
{
	// Data: Store the number of times other bot is scanned.
	//HashMap<String, Integer> allBots = new HashMap<>();
	int lap = 1;
	String lastSeenBot = null;
	int hitCnt = 0;
	int hurtCnt = 0;	
	int wallCnt = 0;
	/**
	 * run: Robbie's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here
		setColors(Color.green,Color.green,Color.green); // body,gun,radar

		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			if(lastSeenBot == null){
				if(lap % 2 == 0){
					turnGunRight(180);
					ahead(100);
				} else{
					turnGunLeft(180);
					back(100);
				}
			} else {
				turnGunRight(15);
				turnGunLeft(30);
				turnGunRight(15);
				// Decide to move if I'm getting hit, and not hiting others
				if( hurtCnt > 0 && hurtCnt > hitCnt ){
					lastSeenBot = null;
					hitCnt = 0;
					hurtCnt = 0;
				}
			}
				
			System.out.println( lap + " - " + lastSeenBot );
			lap++;
			
			if(getEnergy() < 40){
				//Turn Red if only 1 enemy remains
				setColors(Color.white,Color.white,Color.red); // body,gun,radar
			} // end if
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		System.out.println("Scan:"+e.getName());

		if(lastSeenBot == null){
			lastSeenBot = e.getName();
			turnRight(e.getBearing());
			if(e.getDistance() > 65){
				ahead( e.getDistance() - 55 );
			}
			System.out.println("Last=Null: "+lastSeenBot+" dist="+e.getDistance());
		} else {
			if(lastSeenBot == e.getName()){
				turnRight(e.getBearing());
				if(e.getDistance() > 65){
					ahead( e.getDistance() - 55 );
				}
				fire(1);
			}
		}
		
		
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		//back(10);
		System.out.println("I've been Hit!");
		hurtCnt++;
	}
	
	/**
	 * onBulletHit: What to do when get hit by someone else
	 */
	public void onBulletHit(BulletHitEvent e){
		System.out.println("Got You! " + e.getName() );
		hitCnt++;
	}
	
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		System.out.println("Ouch, wall!");
		wallCnt++;
		if( wallCnt > 1){
			if(lap % 2 == 0){
				ahead(30);
			} else{
				back(30);
			}
			wallCnt = 0;
		}

	}	

	/**
	 * onRobotDeath: What to do when someone goes bye-bye
	 */	
	public void onRobotDeath(RobotDeathEvent e){
		lastSeenBot = null;
	} 
	
	

}
