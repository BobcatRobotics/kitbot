/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team177.robot;

public class RobotMap {
	// Drive Chain Motors
	public static int driveLeftMotorFront = 0;
	public static int driveLeftMotorMiddle = 1;
	public static int driveLeftMotorRear = 2;
	public static int driveRightMotorFront = 4;
	public static int driveRightMotorMiddle = 5;
	public static int driveRightMotorRear = 6;

	// Drive Chain Encoders
	public static int leftEncoderChannel1 = 2;
	public static int leftEncoderChannel2 = 3;
	public static int rightEncoderChannel1 = 4;
	public static int rightEncoderChannel2 = 5;
	
	// Drive Chain Solenoids
	public static int driveShiftSolenoid = 0;
	
	// Cube Pickup Motors
	public static int cubePickupLeft = 7;
	public static int cubePickupRight = 8;
	
	// Cube Pickup Solenoids
	public static int cubePickupSolenoid = 3;
	
	// Joy Sticks
	public static int leftJoystick = 0;
	public static int rightJoystick = 1;
	public static int gamePad = 2;
	
	// Joy Stick Buttons
	public static int gamePadCubePickupReverse = 1;
	public static int gamePadCubePickup = 2;
	public static int gamePadCubeArms = 3;
	
	// Joy Stick Triggers
	public static int rightJoystickShifter = 3;
	
}
