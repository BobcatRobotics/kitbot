package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.robot.ElevatorSetPosition;

public class AutoFromLeftRight extends AutoCommand {

	protected boolean startFromRight = true;
	private String fileName;

	public AutoFromLeftRight(String gameData,boolean isRobotLeft, boolean isCrossOver, boolean gameDataFromField) {
		super(gameData);
		boolean isScaleLeft = (autoGameData.charAt(RobotConstants.SCALE) == 'L');
		if (isRobotLeft) {
			if (isScaleLeft) {
				fileName = RobotConstants.LEFT_2_SCALE;
			} else {
				if (isCrossOver) {
					fileName = RobotConstants.LEFT_2_SCALE_RIGHT; 
				} else {
					fileName = RobotConstants.LEFT_2_SCALE;
				}
			}
		} 
		if (!isRobotLeft) {
			if (!isScaleLeft) {
				fileName = RobotConstants.RIGHT_2_SCALE;
			} else {
				if (isCrossOver) {
					fileName = RobotConstants.RIGHT_2_SCALE_LEFT; 
				} else {
					fileName = RobotConstants.RIGHT_2_SCALE;
				}
			}
		}
		setAutoCommands(fileName,isCrossOver,gameDataFromField);
	}

	@Override
	protected void initialize() {
		super.initialize();
	}

	private void setAutoCommands(String fileName,boolean isCrossOver,boolean gameDataFromField) {
		addSequential(new ShiftHigh());
		addSequential(new PlaybackSpeeds(fileName)); 
		if (isCrossOver && gameDataFromField) {
			addSequential(new MoveElevatorAuto(ElevatorSetPosition.UP));
			addSequential(new EjectCube());
		}
	}
}
