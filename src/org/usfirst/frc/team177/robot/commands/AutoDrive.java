/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.robot.OI;

/**
 * This will drive the robot straight for a certain distance
 */
public abstract class AutoDrive extends DriveCommand {
	protected boolean isTimed = false;
	protected boolean driveForward = true;
	protected boolean driveStraightCorrection = false;

	protected AutoDrive() {
		super();
	}

	public AutoDrive(double length) {
			distanceToDrive = length;
	}
	
	public AutoDrive(double length,double timeToStop) {
		distanceToDrive = length;
		setTimeout(timeToStop);
		isTimed = true;
	}

	public AutoDrive(double length,double timeToStop,boolean driveForward) {
		distanceToDrive = length;
		setTimeout(timeToStop);
		isTimed = true;
		this.driveForward = driveForward;
	}

	@Override
	protected void initialize() {
		super.initialize();
		if (driveForward) {
			OI.driveTrain.setLeftPower(INITIAL_LEFT_POWER_FORWARD);
			OI.driveTrain.setRightPower(INITIAL_RIGHT_POWER_FORWARD);
		} else {
			OI.driveTrain.setLeftPower(INITIAL_LEFT_POWER_FORWARD * -1.0);
			OI.driveTrain.setRightPower(INITIAL_RIGHT_POWER_FORWARD * -1.0);
		
		}
			
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (driveStraightCorrection)
			adjustDriveStraight();
		OI.driveTrain.drive();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		boolean stop = false;
		if ((Math.abs(OI.driveTrain.getLeftDistance()) > distanceToDrive) ||
			(Math.abs(OI.driveTrain.getRightDistance()) > distanceToDrive))
			stop = true;
		
		if (isTimed)
			if (isTimedOut())
				stop=true;
		
		return stop;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}


	
}
