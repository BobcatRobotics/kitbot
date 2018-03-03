package org.usfirst.frc.team177.robot.commands;

public class AutoFromCenter extends AutoCommand {
	private boolean driveRight = true;

	public AutoFromCenter(String gameData) {
		super(gameData);
		if (gameData != null) {
			if (autoGameData.charAt(0) == 'L')
				driveRight = false;
		}
		setAutoCommands();
	}

	@Override
	protected void initialize() {
		super.initialize();
	}

	private void setAutoCommands() {
		// Do we need to call AutoDriveWithSpeed here to set MID MOTOR SPEED?
		addSequential(new AutoDriveDistance(RobotConstants.MID_DISTANCE_1,true));
		if (driveRight)
			addSequential(new TurnToAngle(RobotConstants.MID_TURN_ANGLE_1));
		else
			addSequential(new TurnToAngle(RobotConstants.MID_TURN_ANGLE_1 * -1));
		addSequential(new AutoDriveDistance(RobotConstants.MID_DISTANCE_2,true));
		if (driveRight)
			addSequential(new TurnToAngle(RobotConstants.MID_TURN_ANGLE_2));
		else
			addSequential(new TurnToAngle(RobotConstants.MID_TURN_ANGLE_2 * -1));
		addSequential(new AutoDriveSpeed(RobotConstants.MID_MOTOR_SPEED,RobotConstants.MID_DISTANCE_3));
		addSequential(new EjectCube());
	}
}
