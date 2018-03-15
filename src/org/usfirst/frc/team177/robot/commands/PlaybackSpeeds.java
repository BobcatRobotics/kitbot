package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.lib.FileUtils;
import org.usfirst.frc.team177.robot.OI;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PlaybackSpeeds extends Command {
	public boolean atEnd = false;

	private PlaybackSpeeds() {
	}

	public PlaybackSpeeds(String fileName) {
		this();
		FileUtils.setFileName(fileName);
		FileUtils.readRecording();
		OI.debugLog("Playback Speeds finished reading " + fileName);
		atEnd = false;
	}

    // Called repeatedly when this Command is scheduled to run
	@Override
    protected void execute() {
		//double [] 
		double [] speeds = FileUtils.getSpeed();
		if (speeds[0] > 998.0) {
			atEnd = true;
			OI.debugLog("in playbackspecial execute, atEnd = true ");
			return;
			
		}
		
		OI.driveTrain.drive(speeds[0], speeds[1]);
		SmartDashboard.putNumber("read value of left side = " , speeds[0]);
   }

	@Override
	protected boolean isFinished() {
		return atEnd;
	}
}
