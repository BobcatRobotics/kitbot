package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.robot.OI;
import org.usfirst.frc.team177.robot.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Elevator extends Command {

	protected double Motor1Speed = 0.0;
	protected double Motor2Speed = 0.0;
	protected double elevatorMotorCommand = 0.0;
	
	public Elevator() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		DriverStation.reportError("motor1 = " + Motor1Speed + " motor2 " + Motor2Speed, false);
		//OI.elevatorMotor1.set(Motor1Speed);
		//OI.elevatorMotor2.set(Motor2Speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		elevatorMotorCommand = OI.gamePad.getRawAxis(RobotMap.gamePadElevatorCommandStick);
		Motor1Speed = elevatorMotorCommand;
		Motor2Speed = elevatorMotorCommand;
		//OI.elevatorMotor1.set(Motor1Speed);
		//OI.elevatorMotor2.set(Motor2Speed);
		DriverStation.reportError("motor1 = " + Motor1Speed + " motor2 " + Motor2Speed, false);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
		//OI.elevatorMotor1.stopMotor();
		//OI.elevatorMotor2.stopMotor();

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
		//OI.elevatorMotor1.stopMotor();
		//OI.elevatorMotor2.stopMotor();

    }
}
