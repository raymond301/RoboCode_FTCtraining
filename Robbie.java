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
	HashMap<String, Integer> allBots = new HashMap<>();
	int lap = 1;
	/**
	 * run: Robbie's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here
		setColors(Color.green,Color.green,Color.green); // body,gun,radar

		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			ahead(100);
			turnGunRight(360);
			back(100);
			turnGunRight(360);
			System.out.println( lap + " - " + showAllRobotNames() );
			lap++;
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		Integer seenTimes = allBots.get(e.getName());
		if ( seenTimes == null) {
			allBots.put(e.getName(), 1);
		}
		else{
			allBots.put(e.getName(), seenTimes + 1);
		}		
		// Replace the next line with any behavior you would like
		fire(1);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		back(10);
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		back(20);
	}	
	
	public String showAllRobotNames(){
		String out = "";
		for(String n : allBots.keySet()){
			out = n+":"+allBots.get(n)+", "+out;
		}
		return out;
	}

}
