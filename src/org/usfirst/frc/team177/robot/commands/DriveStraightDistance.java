/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.robot.OI;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * This will drive the robot straight for a certain distance
 */
public class DriveStraightDistance extends DriveCommand {
	private static final double INCREASE_CORRECTION = 1.05;
	private static final double DECREASE_CORRECTION = 0.95;

	private double distanceToDrive = 0.0;
	private double prevLeftDistance = 0.0;
	private double prevRightDistance = 0.0;

	private DriveStraightDistance() {
		super();
	}

	public DriveStraightDistance(double length) {
		this();
		distanceToDrive = length;
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		OI.driveTrain.setLeftPower(INITIAL_LEFT_POWER_FORWARD);
		OI.driveTrain.setRightPower(INITIAL_RIGHT_POWER_FORWARD);
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
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
		return stop;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	protected void adjustDriveStraight() {
		double ldist = OI.driveTrain.getLeftDistance();
		double rdist = OI.driveTrain.getRightDistance();
		double leftPower = OI.driveTrain.getLeftPower();
		double rightPower = OI.driveTrain.getRightPower();
 		//logger.log(format(ldist,rdist,leftPower,rightPower));
			
		double leftdiff  = ldist - prevLeftDistance;
		prevLeftDistance = ldist;
		double rightdiff = rdist - prevRightDistance;
		prevRightDistance = rdist;
		
		double ldistChk = Math.abs(leftdiff);
		double rdistChk = Math.abs(rightdiff);
		// XXXXXXXX Do we need a dead band
//		if (Math.abs(ldistChk - rdistChk) < deadBandRange) {
//			return;
//		}
			
		if (ldistChk > rdistChk) {
			rightPower *= INCREASE_CORRECTION;
			leftPower *= DECREASE_CORRECTION;
		} else 
   		if (ldistChk < rdistChk) {
			leftPower *= INCREASE_CORRECTION;
			rightPower *= DECREASE_CORRECTION;
   		}  	
		
		OI.driveTrain.setLeftPower(leftPower);
		OI.driveTrain.setRightPower(rightPower);
	}
	
}
