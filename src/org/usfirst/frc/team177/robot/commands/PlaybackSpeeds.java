package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.lib.RioLogger;
import org.usfirst.frc.team177.lib.SpeedFile;
import org.usfirst.frc.team177.robot.OI;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PlaybackSpeeds extends Command {
	private SpeedFile sFile = null;
	public boolean atEnd = false;

	private PlaybackSpeeds() {
	}

	public PlaybackSpeeds(String fileName) {
		this();
		sFile = new SpeedFile(fileName);
		sFile.readRecordingFile();
		RioLogger.debugLog("Playback Speeds finished reading " + fileName);
		atEnd = false;
	}

    // Called repeatedly when this Command is scheduled to run
	@Override
    protected void execute() {
		//double [] 
		double [] speeds = sFile.getSpeed();
		if (speeds[0] > 998.0) {
			atEnd = true;
			RioLogger.debugLog("PlaybackSpeeds execute() atEnd = true");
			return;
		}
		
		OI.driveTrain.drive(speeds[0], speeds[1]);
		SmartDashboard.putNumber("Playback left side power = " , speeds[0]);
		SmartDashboard.putNumber("Playback right side power = " , speeds[1]);
   }

	@Override
	protected boolean isFinished() {
		return atEnd;
	}
}
