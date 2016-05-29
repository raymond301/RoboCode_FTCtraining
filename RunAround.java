package raymondBots;
import java.util.Random;
import robocode.*;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html


/**
 *  a robot by raymond301
 */
public class RunAround extends Robot
{
	/**
	 * run: default behavior
	 */
	boolean aggressive = false;
	Random rand = new Random();
	int moveAmount = 10; //small default, 10 pxl
	
	
	public void run() {
		// Initialization of the robot.
		//setColors(Color.darkGray,Color.darkGray,Color.darkGray); // body,gun,radar
		setAllColors(Color.darkGray);

		// Initialize typical moveAmount to 1/5 smaller side size
		// You cannot call the getBattleFieldWidth() method before your run()
		moveAmount = (int) Math.min(getBattleFieldWidth(), getBattleFieldHeight()) / 5 ;
	


		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			turnGunRight(360);
			
			ahead(moveAmount);
			double i = (rand.nextDouble() * 15) +5;
			turnRight(i);
			System.out.println( "Forward: "+moveAmount+" - Turn: "+i );
			turnGunRight(360);
			
			if(getOthers() < 2){
				// Last survivors - get aggressive
				aggressive = true;
				setColors(Color.red,Color.white,Color.red); // body,gun,radar
			}

		}
	}
	
	
	


	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		int pwr = 1;
		if(aggressive){
			pwr = 2;
		}		
		fire(pwr);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		back(16);
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		back(moveAmount*2);
	}	
}
