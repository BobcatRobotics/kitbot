package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.robot.OI;
import org.usfirst.frc.team177.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class MoveClimberArm extends Command {
	public MoveClimberArm() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		double climberArmMotorCommand = RobotConstants.CLIMBER_ARMMOTOR_COMMAND_SCALER * OI.gamePad.getRawAxis(RobotMap.gamePadClimberArmCommandStick);
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
