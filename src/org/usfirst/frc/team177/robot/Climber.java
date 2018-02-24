package org.usfirst.frc.team177.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber {
	
	/* Elevator Motors */
	private WPI_TalonSRX climberMotor1; 
	private WPI_TalonSRX climberMotor2; 
	private WPI_TalonSRX climberMotor3; 
	
	public Climber() {
		super();
		// TODO:: Add new mappings in RobotMap
		climberMotor1 = new WPI_TalonSRX(RobotMap.elevatorMotor1canID);
		climberMotor2 = new WPI_TalonSRX(RobotMap.elevatorMotor2canID);
		climberMotor3 = new WPI_TalonSRX(RobotMap.elevatorMotor2canID);
	
		reset();
	}	
	
	public void reset() {
		climberMotor2.setSelectedSensorPosition(0,0,0);
		climberMotor1.set(0.0);
		climberMotor2.set(0.0);
	}

	public void stop () {
		climberMotor1.stopMotor();
		climberMotor2.stopMotor();
	}
	

	public void elevate(double leftSpeed,double rightSpeed) {
		climberMotor1.set(leftSpeed);
		climberMotor2.set(rightSpeed);
	}
	
	public double getLeftSpeed() {
		return climberMotor1.get();
	}
	public double getRightSpeed() {
		return climberMotor2.get();
	}

	/**
	public void displayDashboard() {
	    SmartDashboard.putBoolean("Current Limit Switch1 value:", limitSwitch1.get());
       	SmartDashboard.putNumber("Current elevator encoder counts: ", getEncoderPosition());
	}
	*/
}
