package org.usfirst.frc.team177.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class TalonMagEngcoder extends WPI_TalonSRX {

	public TalonMagEngcoder(int deviceNumber) {
		super(deviceNumber);
		
		// Configure Talon
		this.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
	}
	
	public double getSpeed() {
		return this.getSpeed();
	}

	public double getVelocity() {
		return this.getVelocity();
	}
}
