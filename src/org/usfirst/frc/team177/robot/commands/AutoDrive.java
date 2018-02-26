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
 * This will drive the robot  for a certain distance
 */
public abstract class AutoDrive extends DriveCommand {
	private static final double RAMP_CTR_MAX = 25;
	private static final double STOP_RAMP_DISTANCE = 20.0;
	private double averageDistance;
	private double distanceRemaining;
	private double distanceScale;
	private double rampScale;
	private double rampCounter = 0.0;
	private double initialLeftPower = 0.0;
	private double initialRightPower = 0.0;

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
		rampCounter = 0.0;
		initialLeftPower = RobotConstants.INITIAL_LEFT_POWER_FORWARD;
		initialRightPower = RobotConstants.INITIAL_RIGHT_POWER_FORWARD;
		OI.driveTrain.setLeftPower(0.0);
		OI.driveTrain.setRightPower(0.0);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (driveStraightCorrection)
			adjustDriveStraight();
		
		// Ramp up motor speed over RAMP_CTR_MAX passes through this code.  
		if (rampCounter <= RAMP_CTR_MAX) {
			rampScale = rampCounter/RAMP_CTR_MAX;
			rampCounter += 1.0;
		}
		if (rampScale > 1.0) {rampScale=1.0;} // Limit ramp scale factor to be no larger than 1.0;
		if (rampScale < 0.0) {rampScale=0.0;} // Limit ramp scale factor to be no lower than 0.0;
		
		// As the robot is getting closer to the desired distance, ramp
		// the power to the drive train down.
		// Average left and right side distances to get robot distance
		averageDistance = (OI.driveTrain.getLeftDistance() + OI.driveTrain.getRightDistance())/2.0;
		
		// How much distance is left to travel?
		distanceRemaining = distanceToDrive - averageDistance;
		
		// As the remaining distance approaches the distanceToDrive ramp down the motor power
		// starting to ramp when within the STOP_RAMP_DISTANCE.
		distanceScale = distanceRemaining/STOP_RAMP_DISTANCE;
		if (distanceScale > 1.0) {distanceScale=1.0;} // Limit distance scale factor to be no larger than 1.0;
		if (distanceScale < 0.0) {distanceScale=0.0;} // Limit distance scale factor to be no lower than 0.0;

		OI.driveTrain.setLeftPower(initialLeftPower*rampScale*distanceScale);
		OI.driveTrain.setRightPower(initialRightPower*rampScale*distanceScale);
		
		OI.driveTrain.drive();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		boolean stop = false;
		DriverStation.reportError("in autoDrive isFinished, leftdist = " + OI.driveTrain.getLeftDistance() + " distanceToDrive is: " + distanceToDrive, false);
		if ((Math.abs(OI.driveTrain.getLeftDistance()) > distanceToDrive) ||
			(Math.abs(OI.driveTrain.getRightDistance()) > distanceToDrive)) {
			stop = true;
		}
		if (isTimed) {
			if (isTimedOut()) {
				stop=true;
			}
		}
		return stop;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		OI.driveTrain.stop();
	}


	
}
