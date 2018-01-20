/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.robot.OI;

import edu.wpi.first.wpilibj.command.Command;

/**
 * The base command for Driving
 */
public abstract class DriveCommand extends Command {
	protected static final double INITIAL_LEFT_POWER_FORWARD = -0.60;
	protected static final double INITIAL_RIGHT_POWER_FORWARD = -0.46;
	protected static final double INITIAL_LEFT_POWER_BACKWARD = 0.60;
	protected static final double INITIAL_RIGHT_POWER_BACKWARD = 0.46;

	public DriveCommand() {
		// Use requires() here to declare subsystem dependencies
		//requires(Robot.DriveSystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		OI.driveTrain.reset();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

	// Called repeatedly when this Command is scheduled to run
	abstract protected  void execute(); 

	// Make this return true when this Command no longer needs to run execute()
	abstract protected boolean isFinished();

	// Called once after isFinished returns true
	abstract protected void end();
}
