package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.robot.OI;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public abstract class MoveElevator extends Command {

	/**
	 *  0 = initialized
	 *  1 = reached top
	 *  -1 = reached bottom
	 */
	public int state = 0;
	
	public MoveElevator() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		OI.elevator.reset();
		state = 0;
    }

    protected void checkSwitches() {
    	if (OI.elevator.upperSwitch()) {
    		OI.elevator.stop();
    		state = 1;
    	}
    	if (OI.elevator.lowerSwitch()) {
     		OI.elevator.stop();
    		state = -1;
    	}
    	
    	// TODO:: XXXXXX
    	// set state=0 to simulate no limit switches
    	state = 0;
    } 
    
    // Called repeatedly when this Command is scheduled to run
     protected abstract void execute();

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
