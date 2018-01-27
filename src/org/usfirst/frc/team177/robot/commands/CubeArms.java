package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.robot.OI;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CubeArms extends Command {
	boolean state = false;

    public CubeArms() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = !state;
    	OI.cubeArms.set(state);
    	DriverStation.reportError("Initialize Called state is " + state, false);
   }
    int counter = 1;
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (counter < 5) {
    		DriverStation.reportError("Execute Called", false);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	DriverStation.reportError("Is Finished Called", false);
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	DriverStation.reportError("End Called", false);
    	OI.cubeArms.set(state);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	DriverStation.reportError("Interupted Called", false);
    	OI.cubeArms.set(state);
   }
}
