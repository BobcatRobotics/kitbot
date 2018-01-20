/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team177.robot;

import org.usfirst.frc.team177.robot.commands.AutoCommand;
import org.usfirst.frc.team177.robot.commands.DriveStraightDistance;
import org.usfirst.frc.team177.robot.commands.DriveWithJoysticks;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * 2018 Robot Code - Main Class
 */
public class Robot extends TimedRobot {
	/* Controls */
	public static final OI Controls = new OI();

	/* Commands */
	AutoCommand auto;
	DriveWithJoysticks driveJoy;
	
	/* Sub Systems */ 
	//public static final DriveSubsystem DriveSystem = new DriveSubsystem();
	
	String autoGameData = "LLL"; // Autonomous initial configuration of Plates 
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
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
	}

	/**
	 * Determine which side of the switches and scales is our color
	 * Drive there and drop off a cube
	 * 
	 */
	@Override
	public void autonomousInit() {
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		AutoCommand auto  = new AutoCommand(gameData);
		OI.driveTrain.setRightPower(0.20);
		OI.driveTrain.setLeftPower(0.20);
		auto.start();
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
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
