package org.usfirst.frc.team177.robot.commands;

public class AutoFromCenter extends AutoCommand {
	private final double DISTANCE_1 = 30.0;
	private final double DISTANCE_2 = 45.0;
	private final double DISTANCE_3 = 45.0;
	private final double TURN_ANGLE_1 = 69.0;

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
		addSequential(new AutoDriveStraight(DISTANCE_1));
		if (driveRight)
			addSequential(new TurnToAngle(TURN_ANGLE_1));
		else
			addSequential(new TurnToAngle(TURN_ANGLE_1 * -1));
		addSequential(new AutoDriveStraight(DISTANCE_2));
		if (driveRight)
			addSequential(new TurnToAngle(TURN_ANGLE_1 * -1));
		else
			addSequential(new TurnToAngle(TURN_ANGLE_1));
		addSequential(new AutoDriveStraight(DISTANCE_3));
		addSequential(new EjectCube());
	}

}
