package org.usfirst.frc.team177.robot.commands;

public class AutoFromCenter extends AutoCommand {
	private String fileName;

	public AutoFromCenter(String gameData, boolean gameDataFromField) {
		super(gameData);
		if (autoGameData.charAt(RobotConstants.NEAR_SWITCH) == 'L') {
			fileName = RobotConstants.CENTER_2_LEFT;
		} else {
			fileName = RobotConstants.CENTER_2_RIGHT;
		}
		setAutoCommands(fileName,gameDataFromField);
	}

	@Override
	protected void initialize() {
		super.initialize();
	}

	private void setAutoCommands(String fileName,boolean gameDataFromField) {
		addSequential(new ShiftHigh());
		addSequential(new PlaybackSpeeds(fileName)); 
		//addSequential(new MoveElevatorAuto(ElevatorSetPosition.UP));
		if (gameDataFromField)
			addSequential(new EjectCube());
	}
}
