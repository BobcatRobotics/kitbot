/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.robot.OI;

import edu.wpi.first.wpilibj.Joystick;

/**
 * An example command.  You can replace me with your own command.
 */
public class DriveWithJoysticks extends DriveCommand {
	
	public DriveWithJoysticks() {
		super();
	}

	/// XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	/// this should be called in parent 
	// Called just before this Command runs the first time
//	@Override
//	protected void initialize() {
//		OI.driveTrain.reset();
//	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// Driving
		double left = OI.leftStick.getRawAxis(Joystick.AxisType.kY.value);
		double right = OI.rightStick.getRawAxis(Joystick.AxisType.kY.value);
		OI.driveTrain.drive(left, right);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

}
