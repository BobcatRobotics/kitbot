/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team177.robot;

import org.usfirst.frc.team177.robot.commands.AutoCommand;
import org.usfirst.frc.team177.robot.commands.AutoDriveNoCorrection;
import org.usfirst.frc.team177.robot.commands.AutoFromCenter;
import org.usfirst.frc.team177.robot.commands.AutoFromLeft;
import org.usfirst.frc.team177.robot.commands.AutoFromRight;
import org.usfirst.frc.team177.robot.commands.DriveWithJoysticks;
import org.usfirst.frc.team177.robot.commands.Elevator;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 2018 Robot Code - Main Class
 */
public class Robot extends TimedRobot {
	/* Controls */
	public static final OI Controls = new OI();
	private boolean shifterSolenoidState;
	private boolean shifterSwitchState;

	// First limit switch (bottom or top? TBD)
	DigitalInput limitswitch1 = new DigitalInput(9);
	
	/* Commands */
	AutoCommand auto;
	DriveWithJoysticks driveJoy;
	Elevator moveElevator;
	
	/* SmartDashboard Information */
	//Creates chooser to allow user to select robot starting position
	public static final String AUTO_ROBOT_LEFT = "aRobotLeft";
	public static final String AUTO_ROBOT_MIDDLE = "aRobotMiddle";
	public static final String AUTO_ROBOT_RIGHT = "aRobotRight";
	private String startPosition = "";
	SendableChooser<String> chooser = new SendableChooser<>();
	
	
	
	/* Sub Systems */ 
	//public static final DriveSubsystem DriveSystem = new DriveSubsystem();
	
	String autoGameData = "LLL"; // Autonomous initial configuration of Plates 
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Robot Starts Left", AUTO_ROBOT_LEFT);
        chooser.addObject("Robot Starts Middle", AUTO_ROBOT_MIDDLE);
        chooser.addObject("Robot Starts Right", AUTO_ROBOT_RIGHT);
        SmartDashboard.putData("Auto mode", chooser);
        startPosition = chooser.getSelected();
        SmartDashboard.putString("Chosen Start Position:", startPosition);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();

		startPosition = chooser.getSelected();
        SmartDashboard.putString("Chosen Start Position:", startPosition);
        SmartDashboard.putNumber("Current Gyro Value:", OI.gyro.getYaw());
        SmartDashboard.putBoolean("Current Limit Switch1 value:", limitswitch1.get());
        //SmartDashboard.putNumber("Right Encoder:", rightEncoder.getDistance());
        //SmartDashboard.putNumber("Left Encoder:", leftEncoder.getDistance());
        
	}

	/**
	 * Determine which side of the switches and scales is our color
	 * Drive there and drop off a cube
	 */
	@Override
	public void autonomousInit() {
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		startPosition = chooser.getSelected();
        SmartDashboard.putString("Chosen Start Position:", startPosition);
        SmartDashboard.putString("Platform Data:", gameData);
        SmartDashboard.putNumber("Right Encoder Distance:", OI.driveTrain.getRightDistance());
        SmartDashboard.putNumber("Left Encoder Distance:", OI.driveTrain.getLeftDistance());
        SmartDashboard.putNumber("Initial Gyro Value:", OI.gyro.getYaw());
		
		// Need to determine if starting from Center, Left or Right
		//AutoCommand auto = null;
		// if left
		//auto = new AutoFromLeft(gameData);
		// if right
		//auto = new AutoFromRight(gameData);
		// if center
		//auto = new AutoFromCenter(gameData);
		
		// Test COde
		Command autoCmd = new AutoDriveNoCorrection(36.0,1.5);
		autoCmd.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. 
		if (auto != null) {
			auto.cancel();
		}
		driveJoy = new DriveWithJoysticks();
		driveJoy.start();
//		moveElevator = new Elevator();
//		moveElevator.start();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		shifterSolenoidState = OI.shifter.get();
		if(shifterSolenoidState)
        	SmartDashboard.putString("Shifter solenoid is:", "ON");
        else
        	SmartDashboard.putString("Shifter solenoid is:",  "OFF");
		
		shifterSwitchState = OI.trigShifter.get();
		if(shifterSwitchState)
        	SmartDashboard.putString("Shifter switch is:", "ON");
        else
        	SmartDashboard.putString("Shifter switch is:",  "OFF");
		
        SmartDashboard.putNumber("Right Encoder Distance:", OI.driveTrain.getRightDistance());
        SmartDashboard.putNumber("Left Encoder Distance:", OI.driveTrain.getLeftDistance());
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
