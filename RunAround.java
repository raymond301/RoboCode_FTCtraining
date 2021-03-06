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
		
			int myNum = 95;
			int numberOne = myNum % 90;
			int numberTwo = 90 % myNum;
			System.out.println("myNum="+myNum+" One="+numberOne+" Two="+numberTwo);
			myNum = 3; // reassign variable
			int numberThree = myNum % 2;			
			int numberFour = 2 % myNum;
			System.out.println("myNum="+myNum+" Three="+numberThree+" Four="+numberFour);




			turnGunRight(360);
			ahead(moveAmount);
		
			if(getOthers() < 2){
				//Turn Red if only 1 enemy remains
				setColors(Color.red,Color.white,Color.red); // body,gun,radar
			} // end if
			
		} //end while
		


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
