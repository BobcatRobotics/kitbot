package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.robot.OI;
import org.usfirst.frc.team177.robot.RobotMap;

/**
 *
 */
public class MoveElevatorWithJoystick extends MoveElevator {

	protected double elevatorMotorCommand = 0.0;
	
	public MoveElevatorWithJoystick() {
     }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		elevatorMotorCommand = OI.gamePad.getRawAxis(RobotMap.gamePadElevatorCommandStick);
		double motorSpeed = RobotConstants.FLIP_ELEV_DIRECTION1*elevatorMotorCommand;
		OI.elevator.elevate(motorSpeed);
		
		checkSwitches();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
		OI.elevator.stop();
    }
}
