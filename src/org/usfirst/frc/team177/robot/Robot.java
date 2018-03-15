/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team177.robot;

import org.usfirst.frc.team177.lib.FileUtils;
import org.usfirst.frc.team177.lib.SmartDash;
import org.usfirst.frc.team177.robot.commands.AutoCommand;
import org.usfirst.frc.team177.robot.commands.AutoFromCenter;
import org.usfirst.frc.team177.robot.commands.AutoFromLeftRight;
import org.usfirst.frc.team177.robot.commands.AutoTest;
import org.usfirst.frc.team177.robot.commands.DriveWithJoysticks;
import org.usfirst.frc.team177.robot.commands.MoveClimberArm;
import org.usfirst.frc.team177.robot.commands.MoveElevator;
import org.usfirst.frc.team177.robot.commands.MoveElevatorWithJoystick;
import org.usfirst.frc.team177.robot.commands.RobotConstants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * 2018 Robot Code - Main Class
 */
public class Robot extends TimedRobot {
	/* Controls */
	public static final OI Controls = new OI();
	
	// First limit switch (bottom or top? TBD)
	//DigitalInput limitswitch1 = new DigitalInput(9);
	
	/* Commands */
	AutoCommand auto;
	DriveWithJoysticks driveJoy;
	MoveElevator moveElevator;
	MoveClimberArm moveClimberArm;
	
	/* SmartDashboard Information */
	private String gameData = "";
	private String startPosition = "";
	private String autoFileName = "";
	private String allowCrossOver = "";
	private String recordState = "";
	private String enableElevatorLimits = "";
	private String enableClimberPullin = "";
	private boolean gameDataFromField = false;
	
	SendableChooser<String> robotStartPosition = new SendableChooser<>();
	SendableChooser<String> recorder = new SendableChooser<>();
	SendableChooser<String> fileRecorder = new SendableChooser<>();
	SendableChooser<String> crossOver = new SendableChooser<>();
	SendableChooser<String> climberPullin = new SendableChooser<>();
	SendableChooser<String> elevatorLimits = new SendableChooser<>();
	
	
	/* Sub Systems */ 
	//public static final DriveSubsystem DriveSystem = new DriveSubsystem();
	
	String autoGameData = "LLL"; // Autonomous initial configuration of Plates 	
	
	boolean isRecording = false;
	
	// This boolean controls if the robot is in test recording or the robot
	// is running in competition mode
	boolean isCompetition = true;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		robotStartPosition.addDefault("Robot Starts Left", RobotConstants.AUTO_ROBOT_LEFT);
		robotStartPosition.addObject("Robot Starts Middle", RobotConstants.AUTO_ROBOT_MIDDLE);
		robotStartPosition.addObject("Robot Starts Right", RobotConstants.AUTO_ROBOT_RIGHT);

		crossOver.addDefault("Auto - Cross Over", RobotConstants.AUTO_SCALE_CROSS);
		crossOver.addObject("Auto - Do Not Cross Over", RobotConstants.AUTO_NO_SCALE_CROSS);
 
		recorder.addDefault("Do Nothing", "nothing");
		recorder.addObject("Start Recording ", "start");
		recorder.addObject("Stop Recording", "stop");
	
		fileRecorder.addDefault("Center --> Go Right", RobotConstants.CENTER_2_RIGHT);
		fileRecorder.addObject("Center --> Go Left", RobotConstants.CENTER_2_LEFT);
		fileRecorder.addObject("Left --> To Scale", RobotConstants.LEFT_2_SCALE);
		fileRecorder.addObject("Right --> To Scale", RobotConstants.RIGHT_2_SCALE);
		fileRecorder.addObject("Left --> To Scale Right", RobotConstants.LEFT_2_SCALE_RIGHT);
		fileRecorder.addObject("Right --> To Scale Left", RobotConstants.RIGHT_2_SCALE_LEFT);
		
		climberPullin.addDefault("Climber Pullin Enabled", RobotConstants.CLIMBER_PULLIN_ON);
		climberPullin.addObject("Climber Pullin !!!DISABLED!!!", RobotConstants.CLIMBER_PULLIN_OFF);
		
		elevatorLimits.addDefault("Elevator Limits Enabled", RobotConstants.ELEVATOR_LIMITS_ON);
		elevatorLimits.addObject("Elevator Limits !!!DISABLED!!!", RobotConstants.ELEVATOR_LIMITS_OFF);
		
		SmartDash.displayCompetitionChoosers(robotStartPosition, crossOver, elevatorLimits, climberPullin);
		
        if (!isCompetition)		{
             SmartDash.displayRecordPlaybackChoosers(recorder, fileRecorder);  
        }
  	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		//Clear out the scheduler for testing, since we may have been in teleop before
		//we came int autoInit() change for real use in competition
		Scheduler.getInstance().removeAll();
	}

	@Override
	public void disabledPeriodic() {
		SmartDash.displayControlValues();
		displayAutoData();
		// Reset the climber arm pullin disable flag since we're disabled, and
		// when we re-enable we want to be pulling the arm in again (until an
		// arm command happens
		OI.disableClimberPullIn=false;

	}

	/**
	 * Determine which side of the switches and scales is our color
	 * Drive there and drop off a cube
	 */
	@Override
	public void autonomousInit() {
		//Clear out the scheduler for testing, since we may have been in teleop before
		//we came int autoInit() change for real use in competition
		Scheduler.getInstance().removeAll();
		
		// Get Game Data from field, Driver Station or default to no game data
		gameData = getGameData();
		
		// Get initial Yaw when Auto mode initializes
		OI.gyro.zeroYaw();
		OI.AutoInitYawValue = OI.gyro.getYaw();

		// Reset Drive Train
		OI.driveTrain.reset();
		
		// Initial Elevator
		OI.elevator.reset();
		OI.elevator.resetEncoder();
		
		// Reset the climber arm pullin disable flag since we're starting auto, and
		// we want to be pulling the arm in again (until an arm command happens -- shouldn't in auto)
		OI.disableClimberPullIn=false;

		
		SmartDash.displayControlValues();
		OI.debugLog("Autoinit start position is " + startPosition);
		displayAutoData();
		OI.debugLog("Autoinit start position (2) is " + startPosition);
	
		if (isCompetition) {
			autonomousCompetition();
		} else {
			autonomousTestRecording();
		}
	}


	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		SmartDash.displayControlValues();
		displayAutoData();
	}
	
	@Override
	public void teleopInit() {		
		//Clear out the scheduler for testing, since we may have been in teleop before
		//we came int autoInit() change for real use in competition
		Scheduler.getInstance().removeAll();
		OI.climber.reset();
		// Reset the climber arm pullin disable flag since we're just staring teleop, and
		//  we want to be pulling the arm in again (until an
		// arm command happens)
		OI.disableClimberPullIn=false;


		driveJoy = new DriveWithJoysticks();
		driveJoy.start();
		moveElevator = new MoveElevatorWithJoystick();
		moveElevator.start();
		moveClimberArm = new MoveClimberArm();
		moveClimberArm.start();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		SmartDash.displayControlValues();
		displayAutoData();
		
		// If the robot is not running in competition mode, then this 
		// block will record a new file --> DriverStation.dashboard control
		//
		if (!isCompetition) {
			String dashboardRecMode = recorder.getSelected();
			if (!isRecording && "start".equals(dashboardRecMode)) {
				String autoRecorderName = fileRecorder.getSelected();
				FileUtils.setFileName(autoRecorderName);
				FileUtils.startRecording();
				isRecording = true;
			}
			if (isRecording && ("stop".equals(dashboardRecMode)) ) {
				FileUtils.stopRecording();
				isRecording = false;
			}
			if (isRecording) {
				FileUtils.addSpeeds
				  (OI.driveTrain.getLeftPower(), OI.driveTrain.getRightPower(),
				   OI.driveTrain.getLeftDistance(), OI.driveTrain.getRightDistance(),
				   OI.driveTrain.getLeftRate(), OI.driveTrain.getRightRate());
			}
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		SmartDash.displayControlValues();
		displayAutoData();
	}
	
	private void autonomousCompetition() {
		AutoCommand auto = null;
		OI.debugLog("AutoCompetition start position is " + startPosition);
	
		// Need to determine if starting from Center, Left or Right
		if (RobotConstants.AUTO_ROBOT_MIDDLE.equals(startPosition)) { 
			OI.debugLog("AutoCompetition running center auto");
			auto =  new AutoFromCenter(gameData,gameDataFromField);
		} else {
			OI.debugLog("AutoCompetition running left right auto");
			boolean isLeft = RobotConstants.AUTO_ROBOT_LEFT.equals(startPosition);
			boolean isCrossOver = RobotConstants.AUTO_SCALE_CROSS.equals(crossOver.getSelected());
			OI.debugLog("AutoCompetition running left right auto. isleft, isCrossOVer " + isLeft + " " + isCrossOver);
			auto = new AutoFromLeftRight(gameData,isLeft,isCrossOver,gameDataFromField);
		}
		auto.start();		
	}

	private void autonomousTestRecording() {
		// Need to determine if starting from Center, Left or Right
		//AutoCommand auto = null;
		// if left
		//auto = new AutoFromLeft(gameData);
		// if right
		//auto = new AutoFromRight(gameData);
		// if center
		//auto = new AutoFromCenter(gameData);
		
		// Test COde
		//Command autoCmd = new AutoDriveDistance(36.0,1.5,false);
		//Command autoCmd = new AutoDriveDistance(10.0,0.3,false);
		String autoRecorderName = fileRecorder.getSelected();
		FileUtils.setFileName(autoRecorderName);
		Command autoCmd = new AutoTest(gameData,autoRecorderName);
		//Command autoCmd = new MoveElevatorAuto(ElevatorSetPosition.UP);
		autoCmd.start();
		// Command elevatorCmd = new MoveElevatorAuto(ElevatorSetPosition.UP);
		// elevatorCmd.start();
	}
	
	// This method will attempt to get the game data from the field. If it is
	// invalid or cannot be retrieved then set a flag 
	private String getGameData() {
		final int MAX_GAMEDATA_LOOPS = 10;
		final double DELAY_FOR_GAMEDATA = 0.001;
		String gameData = "";

		// Read game data from driver station
		for (int i = 0; i < MAX_GAMEDATA_LOOPS; i++) {
			gameData = DriverStation.getInstance().getGameSpecificMessage();
			if (gameData != null && !gameData.isEmpty()) {
				gameDataFromField = true;
				break;
			}
			Timer.delay(DELAY_FOR_GAMEDATA);
		}
		return gameData;
	}
	
	private void  displayAutoData () {
		startPosition = robotStartPosition.getSelected();
		//OI.debugLog("Dashboard start position is " + startPosition);
		autoFileName = fileRecorder.getSelected();
		allowCrossOver= crossOver.getSelected();
		recordState = recorder.getSelected();
		enableElevatorLimits = elevatorLimits.getSelected();
		enableClimberPullin = climberPullin.getSelected();
		SmartDash.displayControlValues();
		SmartDash.displayGameData(gameData);
		SmartDash.displayStartPosition(startPosition);
		SmartDash.displayCrossOver(allowCrossOver);
		SmartDash.displayElevatorLimits(enableElevatorLimits);
		SmartDash.displayClimberPullin(enableClimberPullin);
		OI.elevatorLimitIsEnabled = RobotConstants.ELEVATOR_LIMITS_ON.equals(enableElevatorLimits);
		OI.climbPullinIsEnabled = RobotConstants.CLIMBER_PULLIN_ON.equals(enableClimberPullin);
		if (!isCompetition) {
			SmartDash.displayRecordState(recordState);
			SmartDash.displayAutoFileName(autoFileName);
		}
	}

}
