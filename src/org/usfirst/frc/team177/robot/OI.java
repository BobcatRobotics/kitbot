/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team177.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	/* Drive Chain Motors */
	public static DriveTrain driveTrain = new DriveTrain();

	/* Gyro */
	public static NavxGyro gyro;

	/* Joysticks */
	public static Joystick rightStick = new Joystick(0);
	public static Joystick leftStick = new Joystick(1);
	public static Joystick gamePad = new Joystick(2);

	/* Buttons */
	// Button button = new JoystickButton(stick, buttonNumber);

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
	static {
		
		driveTrain.setRightMotors(RobotMap.driveRightMotorFront,RobotMap.driveRightMotorMiddle , RobotMap.driveRightMotorRear);
		driveTrain.setLeftMotors(RobotMap.driveLeftMotorFront,RobotMap.driveLeftMotorMiddle , RobotMap.driveLeftMotorRear);
		
		driveTrain.setLeftMotorsReverse(false);
		driveTrain.setLeftEncoder(new GrayHill(RobotMap.leftEncoderChannel1, RobotMap.leftEncoderChannel2,true));
		driveTrain.setRightEncoder(new GrayHill(RobotMap.rightEncoderChannel1, RobotMap.rightEncoderChannel2,false));

		/* Navx mxp Gyro */
		try {
			/* Communicate w/navX-MXP via the MXP SPI Bus. */
			gyro = new NavxGyro(SPI.Port.kMXP);
			//logFile.log("robotInit() called. navx-mxp initialized");
			// TODO:: Should we still run this code
			int maxCalibrationPasses = 20;
			for (int iCalibrationPasses=0; iCalibrationPasses<maxCalibrationPasses; iCalibrationPasses++) {
				if (!gyro.isCalibrating()) break;
				//logFile.log("robotInit() gyro is calibrating, pass " + iCalibrationPasses);
			}
			// End TODO
			//logFile.log("robotInit() gyro is calibrating " + gyro.isCalibrating());
			if (!gyro.isCalibrating())
				gyro.zeroYaw();
			//logFile.log("robotInit() currentYaw " + gyro.getYaw());
			//dashboard.displayData(gyro);
		} catch (RuntimeException ex) {
			String err = "Error instantiating navX-MXP:  " + ex.getMessage();
			DriverStation.reportError(err, false);
			//logFile.log(err);
		}
	}
}
