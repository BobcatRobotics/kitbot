/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.lib.SmartPID;
import org.usfirst.frc.team177.robot.OI;

/**
 * An example command.  You can replace me with your own command.
 */
public class TurnToAngle extends DriveCommand {
	private double turnAngle = 0.0;
	//private double startingYaw = 0.0;
	
	/// XXXXX TODO:: Make these configurable
	private double pidP = 0.02 ;
	private double pidI = 0.003;
	private double pidD = 0.0;
	private double tolerance = 1.0;
	
	/// XXXXXXX TODO::Make Configurable
	private double LEFT_PWR = 0.46;
	private double RIGHT_PWR  = 0.60;
	
	private TurnToAngle() {
		super();
	}

	public TurnToAngle(double angleToTurn) {
		this();
		OI.driveTrain.setLeftPower(LEFT_PWR);
		OI.driveTrain.setRightPower(RIGHT_PWR);
			
		OI.gyro.setGyroPID(new SmartPID(0.0,pidP,pidI,pidD));
		OI.gyro.setToleranceDegrees(tolerance);
		OI.gyro.initTurnController();
		
		double startingYaw = OI.gyro.getYaw();
		double angle = startingYaw + angleToTurn;
		turnAngle = adjustAngleChange(angle);
		
		OI.gyro.turnToAngle(turnAngle);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// Turning
		double rate = OI.gyro.getRotateToAngleRate();
		OI.driveTrain.drive(rate * -1.0, rate);

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		OI.gyro.stopTurn();
		OI.driveTrain.stop();
	}

	
	private double adjustAngleChange(double angle) {
		double newAngle = angle;
		if (angle > 180) {
			newAngle -= 360;
		} else if (angle < -180) {
			newAngle += 360;
		}
		return newAngle;
	}
}
