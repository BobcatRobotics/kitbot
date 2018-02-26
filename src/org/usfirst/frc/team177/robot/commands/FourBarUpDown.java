package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.robot.OI;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FourBarUpDown extends Command {
	public static boolean state = false;

    public FourBarUpDown() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = !state;
    	OI.fourBar.set(state);
    	// DriverStation.reportError("FourBarUpDown Initialize Called state is " + state, false);
   }

    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	// DriverStation.reportError("FourBarUpDown End Called = " + state, false);
    	OI.fourBar.set(state);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	// DriverStation.reportError("FourBarUpDown Interupted Called", false);
    	OI.fourBar.set(state);
   }
}
