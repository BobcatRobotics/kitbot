package org.usfirst.frc.team177.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCommand extends CommandGroup {
	private String autoGameData = "LLL";
	//private DriveStraightDistance driveDistance;

	private AutoCommand() {
		super();
	}


	public AutoCommand(String gameData) {
		this();
		if (gameData != null)
			autoGameData = gameData;
		
		if(autoGameData.charAt(0) == 'L')
		{
			dropLeft();
		} else {
			dropRight();
		}
	}
	
	private void dropLeft() {
		addSequential(new DriveStraightDistance(24.0));
		addSequential(new TurnToAngle(-15.0));
	}
	
	private void  dropRight() {
		addSequential(new DriveStraightDistance(24.0));
		addSequential(new TurnToAngle(+15.0));
	}

}
