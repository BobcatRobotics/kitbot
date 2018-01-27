package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.robot.OI;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PickupCube extends Command {
	private static double leftMotorSpeed = -1.0;
	private static double rightMotorSpeed = 1.0;

	private PickupCube() {
	}

	public PickupCube(boolean direction) {
		super();
		if (!direction) {
			leftMotorSpeed = 1.0;
			rightMotorSpeed = -1.0;
		}
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		OI.cubeLeftMotor.set(leftMotorSpeed);
		OI.cubeRightMotor.set(rightMotorSpeed);

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {

		OI.cubeLeftMotor.stopMotor();
		OI.cubeRightMotor.stopMotor();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {

		OI.cubeLeftMotor.stopMotor();
		OI.cubeRightMotor.stopMotor();
	}
}
