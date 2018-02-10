package org.usfirst.frc.team177.robot.commands;

public class AutoFromLeft extends AutoFromLeftRight {

	public AutoFromLeft(String gameData) {
		super(gameData);
	}

	@Override
	protected void initialize() {
		startFromRight = false;
		super.initialize();
	}

}
