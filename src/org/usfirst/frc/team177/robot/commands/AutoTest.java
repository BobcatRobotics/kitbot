package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.robot.ElevatorSetPosition;

import edu.wpi.first.wpilibj.DriverStation;

public class AutoTest extends AutoCommand {

	public AutoTest(String gameData) {
		super(gameData);
		setAutoCommands();
	}
	
	@Override
	protected void initialize() {
		super.initialize();
	}

	private void setAutoCommands() {
		//addSequential(new AutoDriveDistance(RobotConstants.MID_DISTANCE_1,true));
		//DriverStation.reportError("after first command", false);
		//addSequential(new AutoDriveDistanceBackwards(RobotConstants.MID_DISTANCE_1,true));
		//DriverStation.reportError("after second command", false);
		//addSequential(new TurnToAngle(RobotConstants.MID_TURN_ANGLE_1));
		//addSequential(new AutoDriveSpeed(RobotConstants.MID_MOTOR_SPEED,RobotConstants.MID_DISTANCE_3));
		addSequential(new PlaybackSpeeds());
		addSequential(new MoveElevatorAuto(ElevatorSetPosition.UP));
		addSequential(new EjectCube());

	}

}
