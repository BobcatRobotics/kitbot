/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.robot.OI;

/**
 * This will drive the with a certain speed
 */
public class AutoDriveWithSpeed extends AutoDrive {
	private double driveSpeed = 0.0;

	private AutoDriveWithSpeed() {
	}

	public AutoDriveWithSpeed(double speed) {
		this();
		driveSpeed = speed;
		driveStraightCorrection = false;
	}

	public AutoDriveWithSpeed(double speed, double length) {
		super(length);
		driveSpeed = speed;
		driveStraightCorrection = false;
	}

	public AutoDriveWithSpeed(double speed, double length, double timeToStop) {
		super(length, timeToStop);
		driveSpeed = speed;
		driveStraightCorrection = false;
	}

	public AutoDriveWithSpeed(double speed, double length, double timeToStop, boolean driveForward) {
		super(length, timeToStop, driveForward);
		driveSpeed = speed;
		driveStraightCorrection = false;
	}

	@Override
	protected void initialize() {
		super.initialize();
		OI.driveTrain.setLeftPower(driveSpeed);
		OI.driveTrain.setRightPower(driveSpeed);
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
