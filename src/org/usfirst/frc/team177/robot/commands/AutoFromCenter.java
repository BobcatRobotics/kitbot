package org.usfirst.frc.team177.robot.commands;

public class AutoFromCenter extends AutoCommand {
	private boolean driveRight = true;

	public AutoFromCenter(String gameData) {
		super(gameData);
		if (gameData != null) {
			if (autoGameData.charAt(0) == 'L')
				driveRight = false;
		}
	}

	@Override
	protected void initialize() {
		super.initialize();
		setAutoCommands();
	}

	private void setAutoCommands() {
		addSequential(new AutoDriveStraight(AutoConstants.MID_DISTANCE_1));
		if (driveRight)
			addSequential(new TurnToAngle(AutoConstants.MID_TURN_ANGLE_1));
		else
			addSequential(new TurnToAngle(AutoConstants.MID_TURN_ANGLE_1 * -1));
		addSequential(new AutoDriveStraight(AutoConstants.MID_DISTANCE_2));
		if (driveRight)
			addSequential(new TurnToAngle(AutoConstants.MID_TURN_ANGLE_2));
		else
			addSequential(new TurnToAngle(AutoConstants.MID_TURN_ANGLE_2 * -1));
		addSequential(new AutoDriveWithSpeed(AutoConstants.MID_MOTOR_SPEED,AutoConstants.MID_DISTANCE_3));
		addSequential(new EjectCube());
	}
}
