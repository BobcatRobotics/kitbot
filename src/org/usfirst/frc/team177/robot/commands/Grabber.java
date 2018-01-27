package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.robot.OI;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Grabber extends Command {

	protected double leftMotorSpeed = 0.0;
	protected  double rightMotorSpeed = 0.0;
	
	public Grabber() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		DriverStation.reportError("Left = " + leftMotorSpeed + " right " + rightMotorSpeed, false);
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
