/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team177.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * This will drive the robot straight for a certain distance
 */
public class AutoDriveNoCorrection extends AutoDrive {

	@SuppressWarnings("unused")
	private AutoDriveNoCorrection() {
	}
	
	public AutoDriveNoCorrection(double length) {
		super(length);
		driveStraightCorrection = false;
	}
	
	public AutoDriveNoCorrection(double length,double timeToStop) {
		super(length,timeToStop);
		driveStraightCorrection = false;
		//DriverStation.reportError("length = " + length + " timeToStop " + timeToStop, false);

		
	}

	public AutoDriveNoCorrection(double length,double timeToStop,boolean driveForward) {
		super(length,timeToStop,driveForward);
		driveStraightCorrection = false;
	}

	@Override
	protected void initialize() {
		super.initialize();
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		super.execute();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		boolean stop = super.isFinished();
		return stop;
	}
	
}
