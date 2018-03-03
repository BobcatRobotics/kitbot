package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.lib.FileUtils;
import org.usfirst.frc.team177.robot.OI;

import edu.wpi.first.wpilibj.command.Command;

public class PlaybackSpeeds extends Command {
	public boolean atEnd = false;

	private PlaybackSpeeds() {
	}

	public PlaybackSpeeds(String fileName) {
		this();
		FileUtils.setFileName(fileName);
		FileUtils.readRecording();
		atEnd = false;
	}

    // Called repeatedly when this Command is scheduled to run
	@Override
    protected void execute() {
		//double [] 
		double [] speeds = FileUtils.getSpeeds();
		if (speeds[0] > 998.0) {
			atEnd = true;
		}
		
		OI.driveTrain.drive(speeds[1], speeds[2]);
   }

	@Override
	protected boolean isFinished() {
		return atEnd;
	}
}
