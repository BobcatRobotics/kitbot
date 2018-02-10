package org.usfirst.frc.team177.robot.commands;

public class AutoFromRight extends AutoFromLeftRight {

	public AutoFromRight(String gameData) {
		super(gameData);
	}

	@Override
	protected void initialize() {
		startFromRight = true;
		super.initialize();
	}
}
