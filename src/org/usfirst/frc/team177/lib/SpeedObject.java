package org.usfirst.frc.team177.lib;

import edu.wpi.first.wpilibj.Timer;

public class SpeedObject {
	// object keys
	private int id;
	private double totalTime = 0.0;
	private double deltaTime = 0.0;

	// speed characteristics
	private double leftSpeed = 0.0;
	private double rightSpeed = 0.0;
	private double leftDistance = 0.0;
	private double rightDistance = 0.0;
	private double leftVelocity = 0.0;
	private double rightVelocity = 0.0;

	public SpeedObject() {
	}

	public void setSpeedKeys(int id, double startTime, double previousSpeedTime) {
		this.id = id;
		deltaTime = Timer.getFPGATimestamp() - previousSpeedTime;
		totalTime = Timer.getFPGATimestamp() - startTime;
	}

	public void setReadKeys(String speedObjectReadable) {
		String[] result = speedObjectReadable.split("\\s");
		id = new Integer(result[0]);
		totalTime = new Double(result[1]);
		deltaTime = new Double(result[2]);
	}

	public void setSpeed(double leftSpeed, double rightSpeed) {
		this.leftSpeed = leftSpeed;
		this.rightSpeed = rightSpeed;
	}

	public double[] getSpeed() {
		double[] row = new double[2];
		row[0] = leftSpeed;
		row[1] = rightSpeed;
		return row;
	}

	public void setDistance(double leftDistance, double rightDistance) {
		this.leftDistance = leftDistance;
		this.rightDistance = rightDistance;
	}

	public double[] getDistance() {
		double[] row = new double[2];
		row[0] = leftDistance;
		row[1] = rightDistance;
		return row;
	}

	public void setVelocity(double leftVelocity, double rightVelocity) {
		this.leftVelocity = leftVelocity;
		this.rightVelocity = rightVelocity;
	}

	public double[] getVelocity() {
		double[] row = new double[2];
		row[0] = leftVelocity;
		row[1] = rightVelocity;
		return row;
	}

	public SpeedObject endOfFile() {
		id = 999;
		totalTime = 999.0;
		deltaTime = 999.0;
		leftSpeed = 0.0;
		rightSpeed = 0.0;
		leftDistance = 0.0;
		rightDistance = 0.0;
		leftVelocity = 0.0;
		rightVelocity = 0.0;
		return this;
	}

	@Override
	public String toString() {
		return id + " " + totalTime + " " + deltaTime + " " + leftSpeed + " " + rightSpeed + " " + leftDistance + " "
				+ rightDistance + " " + leftVelocity + " " + rightVelocity;
	}
}
