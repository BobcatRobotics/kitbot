package org.usfirst.frc.team177.lib;

import org.usfirst.frc.team177.robot.OI;
import org.usfirst.frc.team177.robot.commands.FourBarUpDown;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is used to centralize SmartDashboard operation (get and set)
 * @author bobcat177
 */
public class SmartDash {
	// This method is used to display motors/controllers/switches
	public static void displayControlValues() {
        SmartDashboard.putNumber("Right Encoder Distance:", OI.driveTrain.getRightDistance());
        SmartDashboard.putNumber("Left Encoder Distance:", OI.driveTrain.getLeftDistance());
        SmartDashboard.putNumber("Auto Initial Gyro Value:", OI.AutoInitYawValue);
        SmartDashboard.putNumber("Current Gyro Value:", OI.gyro.getYaw());
         
        boolean shifterSolenoidState = OI.shifter.get();
        SmartDashboard.putString("Shifter solenoid is: ", (shifterSolenoidState ? "ON" :"OFF"));
           
       	boolean shifterSwitchState = OI.trigShifter.get();
       	SmartDashboard.putString("Shifter switch is: ",(shifterSwitchState ?  "ON" : "OFF"));
       	
       	SmartDashboard.putBoolean("Current Four Bar up/down state:",  FourBarUpDown.state);
       	
       	OI.elevator.displayDashboard();
  	}
	
	
	// The following set of methods are used to display
	// runtime game data
	public static void displayStartPosition(String startPosition) {
	       SmartDashboard.putString("Chosen Start Position:", startPosition);

	}
	public static void displayGameData(String gameData) {
		SmartDashboard.putString("Platform Data:", gameData);
	}
	
	public static void displayChooser(SendableChooser<String> chooser) {
		SmartDashboard.putData("Auto mode", chooser);
	}
}
