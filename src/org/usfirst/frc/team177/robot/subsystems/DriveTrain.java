package org.usfirst.frc.team177.robot.subsystems;

import org.usfirst.frc.team177.robot.commands.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {
	/** Inverts drive direction **/
	private static final double INVERT_MOTOR = -1.0;
	
	private WPI_TalonSRX leftRear;
	private WPI_VictorSPX leftFront;
	private WPI_VictorSPX rightFront;
	private WPI_VictorSPX rightRear;
	private double leftPower = 0.0;
	private double rightPower = 0.0;
	private boolean invertLeftFront = false;
	private boolean invertRightFront = false;
	private boolean invertLeftRear = false;
	private boolean invertRightRear = false;

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWithJoysticks());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }
    
    // Put methods for controlling and this subsystem by calling setters and getters
    // here. Call these from Commands.
	public void setupLeftMotors(int lf, boolean invertlf, int lr, boolean invertlr) {
		leftFront = new WPI_VictorSPX(lf);
		invertLeftFront = invertlf;
		leftRear = new WPI_TalonSRX(lr);
		invertLeftRear = invertlr;
	}
	
	public void setupRightMotors(int rf, boolean invertrf, int rr, boolean invertrr) {
		rightFront = new WPI_VictorSPX(rf);
		invertRightFront = invertrf;
		rightRear = new WPI_VictorSPX(rr);
		invertRightRear = invertrr;

	}
	
	public double getLeftMotorPercent() {
		return leftFront.getMotorOutputPercent();
	}
	
	public double getLeftMotorCurrent() {
		return leftFront.getOutputCurrent();
	}
	
	public double getRightMotorPercent() {
		return rightFront.getMotorOutputPercent();
	}

	public double getRightMotorCurrent() {
		return rightFront.getOutputCurrent();
	}

	public double getLeftPower() {
		return leftPower;
	}

	public void setLeftPower(double leftPwr) {
		if (leftPwr > 1.0)
			leftPwr = 1.0;
		else
		if (leftPwr < -1.0)
			leftPwr = -1.0;
		if ((leftPwr < 0.02) && (leftPwr > -0.02)) {
			leftPwr=0.0;
		}
	
		this.leftPower = leftPwr;
	}

	public double getRightPower() {
		return rightPower;
	}

	public void setRightPower(double rightPwr) {
		if (rightPwr > 1.0)
			rightPwr = 1.0;
		else
		if (rightPwr < -1.0)
			rightPwr = -1.0;
		if ((rightPwr < 0.02) && (rightPwr > -0.02)) {
			rightPwr=0.0;
		}
	
		this.rightPower = rightPwr;
	}

	public void drive() {
		drive(leftPower,rightPower);
	}
	
	public void drive(double leftPwr, double rightPwr) {
		double leftFrontPwr;
		double leftRearPwr;
		double rightFrontPwr;
		double rightRearPwr;
		
		// Front and back motors on each side get the power command for that side:
		leftFrontPwr = leftPwr;
		leftRearPwr = leftPwr;
		rightFrontPwr = rightPwr;
		rightRearPwr = rightPwr;
		
		// Flip direction if needed:
		if (invertLeftFront)  leftFrontPwr = leftPwr * INVERT_MOTOR;
		if (invertLeftRear)   leftRearPwr = leftPwr * INVERT_MOTOR;
		if (invertRightFront) rightFrontPwr = rightPwr * INVERT_MOTOR;
		if (invertRightRear)  rightRearPwr = rightPwr * INVERT_MOTOR;

		// Send power command to speed controllers
		leftFront.set(leftFrontPwr);
		leftRear.set(leftRearPwr);
		rightFront.set(rightFrontPwr);
		rightRear.set(rightRearPwr);
	}

	public void stop() {
		leftPower = 0.0;
		rightPower = 0.0;
		leftFront.set(0.0);
		leftRear.set(0.0);
		rightFront.set(0.0);
		rightRear.set(0.0);
	}

	public void reset() {
		leftPower = 0.0;
		rightPower = 0.0;
	}
	
	public void displayDriveTrainData() {
		SmartDashboard.putNumber("Left Motor power request", getLeftPower());
		SmartDashboard.putNumber("Left Motor output %",      getLeftMotorPercent());
		SmartDashboard.putNumber("Left Motor output amps",   getLeftMotorCurrent());
		SmartDashboard.putBoolean("Left front power request inverted",   invertLeftFront);
		SmartDashboard.putBoolean("Left rear power request inverted",   invertLeftRear);
		
		SmartDashboard.putNumber("Right Motor power request", getRightPower());
		SmartDashboard.putNumber("Right Motor output %",      getRightMotorPercent());
		SmartDashboard.putNumber("Right Motor output amps",   getRightMotorCurrent());
		SmartDashboard.putBoolean("Right front power request inverted",   invertRightFront);
		SmartDashboard.putBoolean("Right rear power request inverted",   invertRightRear);		
	}
}