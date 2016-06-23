package raymondBots;
import robocode.*;
import java.awt.Color;
//For using Random
import robocode.util.Utils;
import java.util.Random;

/**
 * Hal3000 - a robot by (your name here)
 */
public class Hal3000 extends Robot
{
	/**
	 * run: Hal3000's default behavior
	 */
	double totWidth;
	double totHieght;
	int halfhieght;
	Random rand;
	
	public void run() {
		totWidth = getBattleFieldWidth();
		totHieght = getBattleFieldHeight();
		rand = Utils.getRandom();

		// Initialization of the robot should be put here
		setColors(Color.pink,Color.blue,Color.green); // body,gun,radar
		
		//find nearest wall
		int halfwidth = (int) totWidth / 2;
		halfhieght = (int) totHieght / 2;
		
		boolean bottom = false;
		int yToGo = (int) ( totHieght - getY() );
		if( getY() < halfhieght){ //check if bottom half
			bottom = true;
		}

		double cHead = getHeading();
		double cMod = cHead % 180;
		System.out.println("Goto Bottom:"+bottom+"  by:"+yToGo+" head:"+cHead);
		System.out.println("cMod:"+cMod+" left:"+( cMod - 180 )+" ahead:"+(yToGo-5));
		turnLeft( cMod - 180 );
		ahead(yToGo-5);
		turnRight(90);
		
		if( Utils.isNear( getHeading(), 90.0 ) ){
			turnGunRight(90);
		} else {
			turnGunLeft(90);
		}

		// Robot main loop
		int cnt=0;
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			turnGunRight(60);			
			turnGunLeft(120);
			turnGunRight(60);
			
			int mv = rand.nextInt(halfwidth);
			if(cnt % 2 == 0){
				// move to right side, check first
				if( (getX() + mv) > totWidth ){
					back(mv);
				} else {
					ahead(mv);
				}

			} else {
				// move to left side, check first
				if( (getX() - mv) < 0 ){
					ahead(mv);
				} else {
					back(mv);
				}
			}
			
			cnt++;
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		if(e.getDistance() < 100){
			fire(2);
		} else if(e.getDistance() < halfhieght){
			fire(1);
		}
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		//back(10);
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		System.out.println("Ouch, Wall!");
	}	
}
