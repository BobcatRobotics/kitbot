package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.robot.OI;
import org.usfirst.frc.team177.robot.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class MoveClimberArm extends Command {

	protected double climberArmMotorCommand;
	protected double climberArmMotorCommandScaler = 0.6;
	
	public MoveClimberArm() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		DriverStation.reportError("running command to move climber arm.", false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		climberArmMotorCommand = climberArmMotorCommandScaler * OI.gamePad.getRawAxis(RobotMap.gamePadClimberArmCommandStick);
		double armMotorSpeed = RobotConstants.FLIP_ARM_DIRECTION*climberArmMotorCommand;
		OI.climber.setClimberArmMotorSpeed(armMotorSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
		OI.climber.stop();;

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
		OI.climber.stop();

    }
}
