package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.robot.OI;
import org.usfirst.frc.team177.robot.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveElevator extends Command {

	protected double elevatorMotorCommand = 0.0;
	
	public MoveElevator() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		DriverStation.reportError("motor1 = " + OI.elevator.getLeftSpeed() + " motor2 " + OI.elevator.getRightSpeed(), false);
		OI.elevator.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		elevatorMotorCommand = OI.gamePad.getRawAxis(RobotMap.gamePadElevatorCommandStick);
		double Motor1Speed = RobotConstants.FLIP_ELEV_DIRECTION1*elevatorMotorCommand;
		double Motor2Speed = RobotConstants.FLIP_ELEV_DIRECTION2*elevatorMotorCommand;
		OI.elevator.elevate(Motor1Speed, Motor2Speed);
		//DriverStation.reportError("motor1 = " + Motor1Speed + " motor2 " + Motor2Speed, false);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
		OI.elevator.stop();

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
		OI.elevator.stop();
    }
}
