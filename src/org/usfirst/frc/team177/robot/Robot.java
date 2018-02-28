/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team177.robot;

import org.usfirst.frc.team177.lib.SmartDash;
import org.usfirst.frc.team177.robot.commands.AutoCommand;
import org.usfirst.frc.team177.robot.commands.AutoDriveDistance;
import org.usfirst.frc.team177.robot.commands.DriveWithJoysticks;
import org.usfirst.frc.team177.robot.commands.MoveClimberArm;
import org.usfirst.frc.team177.robot.commands.MoveElevator;
import org.usfirst.frc.team177.robot.commands.MoveElevatorWithJoystick;
import org.usfirst.frc.team177.robot.commands.MoveElevatorAuto;

import org.usfirst.frc.team177.robot.ElevatorSetPosition;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * 2018 Robot Code - Main Class
 */
public class Robot extends TimedRobot {
	/* Controls */
	public static final OI Controls = new OI();
	
	// First limit switch (bottom or top? TBD)
	//DigitalInput limitswitch1 = new DigitalInput(9);
	
	/* Commands */
	AutoCommand auto;
	DriveWithJoysticks driveJoy;
	MoveElevator moveElevator;
	MoveClimberArm moveClimberArm;
	
	/* SmartDashboard Information */
	//Creates chooser to allow user to select robot starting position
	public static final String AUTO_ROBOT_LEFT = "aRobotLeft";
	public static final String AUTO_ROBOT_MIDDLE = "aRobotMiddle";
	public static final String AUTO_ROBOT_RIGHT = "aRobotRight";
	private String startPosition = "";
	private String gameData = "";
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
        
        startPosition = chooser.getSelected();
        SmartDash.displayStartPosition(startPosition);
        SmartDash.displayChooser(chooser);       
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		//gameData = DriverStation.getInstance().getGameSpecificMessage();
		//SmartDash.displayControlValues();
		//OI.gyro.zeroYaw();
		//OI.elevatorMotor1.setSelectedSensorPosition(0,0,0);
		//System.out.println("Made it to disabledInit");
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();

		startPosition = chooser.getSelected();
		SmartDash.displayStartPosition(startPosition);
		SmartDash.displayControlValues();
		SmartDash.displayGameData(gameData);
        
	}

	/**
	 * Determine which side of the switches and scales is our color
	 * Drive there and drop off a cube
	 */
	@Override
	public void autonomousInit() {
		SmartDash.displayControlValues();
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		startPosition = chooser.getSelected();
		SmartDash.displayStartPosition(startPosition);
		SmartDash.displayGameData(gameData);
		
		// Get initial Yaw when Auto mode initializes
		OI.gyro.zeroYaw();
		OI.AutoInitYawValue = OI.gyro.getYaw();

		// Reset Drive Train
		OI.driveTrain.reset();
		
		// Initial Elevator
		OI.elevator.reset();
		
		SmartDash.displayControlValues();
		
		// Need to determine if starting from Center, Left or Right
		//AutoCommand auto = null;
		// if left
		//auto = new AutoFromLeft(gameData);
		// if right
		//auto = new AutoFromRight(gameData);
		// if center
		//auto = new AutoFromCenter(gameData);
		
		// Test COde
		//Command autoCmd = new AutoDriveDistance(36.0,1.5,false);
		Command autoCmd = new AutoDriveDistance(10.0,0.3,false);
		autoCmd.start();
		// Command elevatorCmd = new MoveElevatorAuto(ElevatorSetPosition.UP);
		// elevatorCmd.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		SmartDash.displayControlValues();
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
		moveElevator = new MoveElevatorWithJoystick();
		moveElevator.start();
		moveClimberArm = new MoveClimberArm();
		moveClimberArm.start();
		
		//Show info
		SmartDash.displayControlValues();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		SmartDash.displayControlValues();
		       
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		SmartDash.displayControlValues();
	}
}
