package org.usfirst.frc.team177.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator {
	/* Elevator Motors */
	private WPI_TalonSRX elevatorMotor1; 
	private WPI_TalonSRX elevatorMotor2; 
	
	private DigitalInput limitSwitch1;  // Top Limit Switch
	private DigitalInput limitSwitch2;	// Bottom Limit Switch

	
	public Elevator() {
		super();
		elevatorMotor1 = new WPI_TalonSRX(RobotMap.elevatorMotor1canID);
		elevatorMotor2 = new WPI_TalonSRX(RobotMap.elevatorMotor2canID);
		
		limitSwitch1 = new DigitalInput(RobotMap.digitalSwitch1);
		limitSwitch2 = new DigitalInput(RobotMap.digitalSwitch2);

		// Motor2 has a magnetic encoder attached
		elevatorMotor2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute,0,0);
		
		reset();
	}	
	
	// CAUTION: there are two motors and one encoder, use correct motor
	public double getEncoderPosition() {
		return elevatorMotor2.getSelectedSensorPosition(0);
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
	

	public void elevate(double speed) {
		elevatorMotor1.set(speed);
		elevatorMotor2.set(speed);
	}
	
	public double getLeftSpeed() {
		return elevatorMotor1.get();
	}
	public double getRightSpeed() {
		return elevatorMotor2.get();
	}

	public boolean upperSwitch() {
		return limitSwitch1.get();
	}
	
	public boolean lowerSwitch() {
		return limitSwitch2.get();
	}
	
	public void displayDashboard() {
	    SmartDashboard.putBoolean("Current Limit Switch1 value:", limitSwitch1.get());
	    SmartDashboard.putBoolean("Current Limit Switch2 value:", limitSwitch2.get());
       	SmartDashboard.putNumber("Current elevator encoder counts: ", getEncoderPosition());
	}

}
