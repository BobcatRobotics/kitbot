/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team177.robot;

import org.usfirst.frc.team177.robot.commands.PickupCube;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	/* Drive Chain Motors */
	public static DriveTrain driveTrain = new DriveTrain();

	/* Motors */
	public static Victor cubeLeftMotor = new Victor(RobotMap.cubePickupLeft);
	public static Victor cubeRightMotor = new Victor (RobotMap.cubePickupRight);
	
	/* Solenoid */
	Solenoid cubeArms = new Solenoid(0);   /* Controls the Cube Arms */

	/* Gyro */
	public static NavxGyro gyro;

	/* Joysticks */
	public static Joystick rightStick = new Joystick(RobotMap.rightJoystick);
	public static Joystick leftStick = new Joystick(RobotMap.leftJoystick);
	public static Joystick gamePad = new Joystick(RobotMap.gamePad);

	/* Buttons */
	public static Button btnCubePickup = new JoystickButton(gamePad, RobotMap.gamePadCubePickup);
	public static Button btnCubeArms = new JoystickButton(gamePad,RobotMap.gamePadCubeArms);

		
	static {
		
		driveTrain.setRightMotors(RobotMap.driveRightMotorFront,RobotMap.driveRightMotorMiddle , RobotMap.driveRightMotorRear);
		driveTrain.setLeftMotors(RobotMap.driveLeftMotorFront,RobotMap.driveLeftMotorMiddle , RobotMap.driveLeftMotorRear);
		
		driveTrain.setLeftMotorsReverse(false);
		driveTrain.setLeftEncoder(new GrayHill(RobotMap.leftEncoderChannel1, RobotMap.leftEncoderChannel2,true));
		driveTrain.setRightEncoder(new GrayHill(RobotMap.rightEncoderChannel1, RobotMap.rightEncoderChannel2,false));

		btnCubePickup.whileHeld(new PickupCube());
		
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
