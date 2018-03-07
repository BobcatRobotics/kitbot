package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.robot.ElevatorState;
import org.usfirst.frc.team177.robot.OI;
import org.usfirst.frc.team177.robot.RobotMap;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class MoveElevatorWithJoystick extends MoveElevator {

	
	protected double elevatorMotorCommand = 0.0;
	
	public MoveElevatorWithJoystick() {
		super();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		elevatorMotorCommand = OI.gamePad.getRawAxis(RobotMap.gamePadElevatorCommandStick);
		double motorSpeed = RobotConstants.FLIP_ELEV_DIRECTION1*elevatorMotorCommand;

		if (OI.elevatorLimitIsEnabled) {
			ElevatorState state = checkSwitches();
			switch (state) {
			case LIMIT_UP :
				if (motorSpeed > 0.0)
					motorSpeed = 0.0;
				break;
			case LIMIT_DOWN:
				if (motorSpeed < 0.0)
					motorSpeed = 0.0;
				break;
			case NONE: 
				break;
			}
		}
		OI.elevator.elevate(motorSpeed);
    }

}
