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
	GortDirectionDriveSupport driver = new GortDirectionDriveSupport();
	int cnt = 0;
	public void run() {
		// Initialization of the robot
		setAllColors(Color.blue); // body,gun,radar,bullet,scan arc
		// How big is the field I'm on? Save it for Later.
		driver.myField(getBattleFieldWidth(), getBattleFieldHeight());
		System.out.println("FieldWidth: "+getBattleFieldWidth()+"  FieldHeight:"+getBattleFieldHeight());
		driver.myPosition(getX(),getY());

		// Robot main loop
		while(true) {
			System.out.println("Lap:"+cnt);
			cnt++;
			if(!driver.getShootingDesion()){
				System.out.println("Turn Gun");
				turnGunLeft(360);
			}
			else{
				System.out.println("While-Fire!");
				fire(1);
			}			

			driver.myPosition(getX(),getY());
			// Visually display myself white, when I am getting low on energy
			if(getEnergy() < 30){
				setColors(Color.white,Color.white,Color.blue); // body,gun,radar
			}

			System.out.println("Shoot: "+driver.getShootingDesion()+" While-"+driver.getLocationString());
			//System.out.println("While-"+driver.getMySeenBots());
		}// This is the end	of while	

	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {		
		driver.scannedARobot(e.getName(),e.getEnergy(),e.getDistance(),e.getBearing());
		if(driver.getDriveDesion()){
			System.out.println("Drive In Scan.");
			dynamicDrive();
		}
		
		if(driver.getShootingDesion()){
			System.out.println("Scan-Fire!");
			fire(2);
		}
		
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Basically ignore my own damage.
		System.out.println("I've Been Hit!");	
	}
	
	/**
	 * onHitWall: What to do when you hit a wall : want to Avoid this in the future.
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		System.out.println("Wall!");
	}	
	
	/**
	 * onBulletHit: What to do when get hit by someone else
	 */
	public void onBulletHit(BulletHitEvent e){
		System.out.println("Got You! " + e.getName() );	
	}
	
	/**
	 * onBulletMissed: What to do when you miss, and hit a wall instead
	 */
	public void onBulletMissed(BulletHitEvent e){
		System.out.println("onBulletMissed!");
		driver.turnOffShooting();
	}
	
	/**
	 * onRobotDeath: when an enemy dies
	 */
	public void onRobotDeath(RobotDeathEvent e){
		driver.robotDemise(e.getName());	
	}
	
	/**
	 * dynamicDrive: accept a list of actions to take in series
	 */
	public void dynamicDrive(){
		char[] directions = driver.getDirections();
		int[] distances = driver.getDistances();
		
		System.out.print("DRIVE - "+driver.getCourseComing());
		for(int i=0; i < directions.length; i++){
			System.out.print("*"+directions[i]);
			switch(directions[i]){
				case 'a': ahead(distances[i]);
				case 'b': back(distances[i]);
				case 'l': turnLeft(distances[i]);
				case 'r': turnRight(distances[i]);
			}
		}
			
	}

	
}

