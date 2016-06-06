package raymondBots;
import robocode.*;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Gort - a robot by (your name here)
 */
public class Gort extends Robot
{
	/**
	 * run: Gort's default behavior
	 */
	String enemyRobotName;
	boolean keepSearching = true;
	int pwr = 1;

	public void run() {
		// Initialization of the robot
		enemyRobotName = null; // Initialy to not following anyone
		setColors(Color.blue,Color.blue,Color.blue); // body,gun,radar
		int count = 0;
		
		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			//ahead(100);
			//turnGunRight(360);
			//back(100);
			//turnGunRight(66);
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

			
			if(getEnergy() < 30){
				setColors(Color.white,Color.white,Color.blue); // body,gun,radar
			}
			count++;
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		enemyRobotName = e.getName();
		out.println("Found: " + enemyRobotName + " Dist: " + e.getDistance() + "Bearing: " + e.getBearing());	

		// Chase them down!!
		if(e.getDistance() > 30){
			System.out.println("Chase Down:"+e.getDistance());
			turnRight(e.getBearing());
			ahead(e.getDistance() - 50);
		}
		
		if( e.getDistance() < 40 ){
			fire(4);
			pwr = 4;
		}
		else if( e.getDistance() < 100 ){
			fire(2);
		}
		else if( e.getDistance() < 300 ){
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
			dynamicDrive(new char[]{'r','a','l'}, new int[]{90,15,90} );	
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
	
	public void onBulletHit(BulletHitEvent e){
		System.out.println("onBulletHit!");	
		keepSearching=false;
	}
	
	public void onBulletMissed(BulletHitEvent e){
		System.out.println("onBulletMissed!");	
		keepSearching=true;
	}
	
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


//public class DirectionDriveSupport
//{
	
//}
//}
