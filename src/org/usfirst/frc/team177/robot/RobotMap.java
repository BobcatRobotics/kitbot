/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team177.robot;

public class RobotMap {
	// Drive Train
	/**  These are the drive train controllers for 2018 practice bot 
	public static int driveRightMotorFront = 11;
	public static int driveRightMotorMiddle = 22;
	public static int driveRightMotorRear = 23;
	public static int driveLeftMotorFront = 2;
	public static int driveLeftMotorMiddle = 20;
	public static int driveLeftMotorRear = 21;
	**/

	/** These are the drive train controllers for 2018 competition bot **/
	public static int driveRightMotorFront = 2;
	public static int driveRightMotorMiddle = 23;
	public static int driveRightMotorRear = 24;
	public static int driveLeftMotorFront = 1;
	public static int driveLeftMotorMiddle = 21;
	public static int driveLeftMotorRear = 22;
	
	
	// Drive Train Encoders for 2018 practice bot
	public static int leftEncoderChannel1 = 2;
	public static int leftEncoderChannel2 = 3;
	public static int rightEncoderChannel1 = 0;
	public static int rightEncoderChannel2 = 1;
	// Drive train encoders for 2018 competition bot
	
	// Drive Train Solenoids
	public static int driveShiftSolenoid = 0;
	
	// Robot Casters Solenoid -- NOT used anywhere yet, but maybe later
	public static int casterSolenoid = 1;
	
	// Cube Pickup Motors
	public static int cubePickupLeft = 25;   // Competition bot --> Practice bot 5,1
	public static int cubePickupRight = 26;
	
	// Cube Pickup Solenoids
	public static int cubePickupSolenoid = 2;
	
	// Elevator Motors
	public static int elevatorMotor1canID=8; // Competition bot --> change for skate and practice
	public static int elevatorMotor2canID=9;
	
	// Elevator MAG encoders should be on Talon with ID ?
	
	// Four Bar mechanism solenoid
	public static int fourBarSolenoid=3;
	
	// Joy Sticks
	public static int leftJoystick = 0;
	public static int rightJoystick = 1;
	public static int gamePad = 2;
	
	// Joy Stick Buttons
	public static int gamePadCubePickupReverse = 1;
	public static int gamePadCubePickup = 2;
	public static int gamePadCubeArms = 3;
	public static int gamePadFourBarUpDown = 4;
	public static int gamePadClimberUp = 5; 		//left-side bumper
	public static int gamePadClimberDown = 7; 		//left-side trigger 
	public static int gamePadClimberWinchIn = 6;	//right-side bumper
	public static int gamePadClimberWinchOut = 8;	//right-side trigger
	
	// Joy Stick Triggers
	public static int rightJoystickShifter = 3;
	public static int gamePadElevatorCommandStick = 3;
	
	// Limit Switch 
	public static int digitalSwitch1 = 9;
	public static int digitalSwitch2 = 10;
	
}
