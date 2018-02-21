package org.usfirst.frc.team177.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Elevator {
	
	/* Elevator Motors */
	private WPI_TalonSRX elevatorMotor1; 
	private WPI_TalonSRX elevatorMotor2; 
	
	
	public Elevator() {
		super();
		elevatorMotor1 = new WPI_TalonSRX(RobotMap.elevatorMotor1canID);
		elevatorMotor2 = new WPI_TalonSRX(RobotMap.elevatorMotor2canID);

		// Motor2 has a magnetic encoder attached
		elevatorMotor2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute,0,0);
		
		elevatorMotor1.set(0.0);
		elevatorMotor2.set(0.0);
	}	
	
	public void reset() {
		elevatorMotor2.setSelectedSensorPosition(0,0,0);
		elevatorMotor1.set(0.0);
		elevatorMotor2.set(0.0);
	}

	public void stop () {
		elevatorMotor1.stopMotor();
		elevatorMotor2.stopMotor();
	}
	
	public double getEncoderPosition() {
		return elevatorMotor1.getSelectedSensorPosition(0);
	}

	public void elevate(double leftSpeed,double rightSpeed) {
		elevatorMotor1.set(leftSpeed);
		elevatorMotor2.set(rightSpeed);
	}
	
	public double getLeftSpeed() {
		return elevatorMotor1.get();
	}
	public double getRightSpeed() {
		return elevatorMotor2.get();
	}
}
