package org.usfirst.frc.team177.robot.commands;

import org.usfirst.frc.team177.robot.OI;

import edu.wpi.first.wpilibj.command.Command;

public class PlaybackSpeeds extends Command {

	public PlaybackSpeeds() {
		// TODO Auto-generated constructor stub
	}

	public PlaybackSpeeds(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public PlaybackSpeeds(double timeout) {
		super(timeout);
		// TODO Auto-generated constructor stub
	}

	public PlaybackSpeeds(String name, double timeout) {
		super(name, timeout);
		// TODO Auto-generated constructor stub
	}

    // Called repeatedly when this Command is scheduled to run
	@Override
    protected void execute() {
		OI.playRecordedSpeed();
    }

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		if (OI.getPassCtr() > 998) {
			return true;
		} else {
			return false;
		}
	}
}
