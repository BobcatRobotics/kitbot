package org.usfirst.frc.team177.robot.commands;

public abstract class AutoFromLeftRight extends AutoCommand {

	protected boolean startFromRight = true;
	private boolean switchRight = true;

	public AutoFromLeftRight(String gameData) {
		super(gameData);
		if (gameData != null) {
			if (autoGameData.charAt(0) == 'L')
				switchRight = false;
		}
	}

	@Override
	protected void initialize() {
		super.initialize();
		setAutoCommands();
	}

	private void setAutoCommands() {
		addSequential(new AutoDriveStraight(RobotConstants.LR_DISTANCE_1));
		// If scale is on the other side then stop autonomous
		if ((startFromRight && !switchRight) ||
			(!startFromRight && switchRight))
			return;
		
		if (switchRight)
			addSequential(new TurnToAngle(RobotConstants.LR_TURN_ANGLE_1));
		else
			addSequential(new TurnToAngle(RobotConstants.LR_TURN_ANGLE_1 * -1));
		addSequential(new AutoDriveStraight(RobotConstants.LR_DISTANCE_2));
		if (switchRight)
			addSequential(new TurnToAngle(RobotConstants.LR_TURN_ANGLE_2));
		else
			addSequential(new TurnToAngle(RobotConstants.LR_TURN_ANGLE_2  * -1));
		// TODO:: XXX Talk to Mark This should be raise elevator
		// addSequential(new EjectCube());
		
		addSequential(new AutoDriveWithSpeed(0.5,RobotConstants.LR_DISTANCE_3));
		addSequential(new EjectCube());
	}

}
