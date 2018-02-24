package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.lib.RioLogger;
import org.usfirst.frc.team177.robot.ElevatorSetPosition;
import org.usfirst.frc.team177.robot.OI;

/**
 *
 */
public class MoveElevatorAuto extends MoveElevator {

	ElevatorSetPosition direction = ElevatorSetPosition.NONE;
	private boolean movingtoSet = false;
	private int movingtoSetDirection = 0;

	public MoveElevatorAuto(ElevatorSetPosition direction) {
		super();
		this.direction = direction;
		if ((this.direction != ElevatorSetPosition.NONE) &&
			(this.direction != ElevatorSetPosition.UP) &&
			(this.direction != ElevatorSetPosition.DOWN))
		{
			movingtoSet = true;
			double currentPosition = OI.elevator.getEncoderPosition();
			if (currentPosition < direction.getPosition()) {
				movingtoSetDirection = 1;
			}
			else {
				movingtoSetDirection = -1;
			}
		}
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		RioLogger.log("MoveElevatorAuto initialize() - setting position" + direction.getPosition());
		OI.elevator.reset();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
    	double speed = 0;
    	switch (direction) {
	    	case UP : speed = 0.3; break;
	    	case DOWN  : speed = -0.3; break;
	    	case POSITION_1:
	    	case POSITION_2:
	    	case POSITION_3: 
				if (movingtoSetDirection == 1)
					speed = 0.3;
				if (movingtoSetDirection == -1)
					speed = -0.3;
				if (movingtoSet) {
		 			double currentPosition = OI.elevator.getEncoderPosition();
		 		    if ((movingtoSetDirection == 1) &&
		 		    	(currentPosition > direction.getPosition())) {
		 		    	movingtoSet = false;
		 		    	movingtoSetDirection = 0;
		 		    }
		 		    if ((movingtoSetDirection == -1) &&
				    	(currentPosition < direction.getPosition())) {
		 		    	movingtoSet = false;
		 		    	movingtoSetDirection = 0;
		 		    }
				}
		 		break;
			default : speed = 0;
    	}
    	
		OI.elevator.elevate(speed);
		checkSwitches();
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
