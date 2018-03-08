package org.usfirst.frc.team177.lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class SpeedFile {
	private static List<SpeedObject> speeds = new ArrayList<SpeedObject>();
	private static SpeedObject eof = new SpeedObject().endOfFile();
	private String fileName;

	private int passCtr = 0;
	private int maxCtr = 0;
	private double speedEntryTime = 0.0;
	private double startTime = 0.0;

	private SpeedFile() {
		speedEntryTime = Timer.getFPGATimestamp();
	}

	public SpeedFile(String fileName) {
		this();
		this.fileName = fileName;
	}

	private void reset() {
		passCtr = 0;
		maxCtr = 0;
		speedEntryTime = Timer.getFPGATimestamp();
		startTime = speedEntryTime;
	}

	public void startRecording() {
		reset();
		speeds.clear();
	}

	public void stopRecording() {
		try {
			File file = new File(fileName);
			FileWriter fileWriter = new FileWriter(file);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			for (SpeedObject speedObj : speeds) {
				printWriter.println(speedObj.toString());
			}
			printWriter.println(eof.toString());
			printWriter.flush();
			printWriter.close();
		} catch (IOException e) {
			String err = "SpeedFile.stopRecoring() error " + e.getMessage();
			DriverStation.reportError(err, false);
			RioLogger.log(err);
		}
	}

	public void readRecordingFile() {
		reset();
		speeds.clear();

		Scanner sc;
		try {
			sc = new Scanner(new File(fileName));
			while (sc.hasNextLine()) {
				String row = sc.nextLine();
				String[] result = row.split("\\s");
				SpeedObject speedObj = new SpeedObject();
				speedObj.setReadKeys(row);
				speedObj.setSpeed(new Double(result[3]), new Double(result[4]));
				speeds.add(speedObj);
				passCtr++;
			}
			maxCtr = passCtr;
			// File was read, now prime the passCounter for reading back each row
			passCtr = 0;
		} catch (FileNotFoundException e) {
			String err = "SpeedFile.readRecoring() error " + e.getMessage();
			DriverStation.reportError(err, false);
			RioLogger.log(err);
		}
	}

	public SpeedObject getRawData(int index) {
		SpeedObject speedObj = eof;
		if (index < maxCtr) {
			speedObj = speeds.get(index);
		}
		return speedObj;
	}

	public void addSpeed(double leftSpeed, double rightSpeed, double leftDistance, double rightDistance,
			double leftVelocity, double rightVelocity) {
		SpeedObject speedObject = new SpeedObject();
		speedObject.setSpeedKeys(passCtr, startTime, speedEntryTime);
		speedObject.setSpeed(leftSpeed, rightSpeed);
		speedObject.setDistance(leftDistance, rightDistance);
		speedObject.setVelocity(leftVelocity, rightVelocity);
		speeds.add(speedObject);

		speedEntryTime = Timer.getFPGATimestamp();
		passCtr++;
		maxCtr = passCtr;
	}

	public int getNbrOfRows() {
		return maxCtr;
	}

	public double[] getSpeed() {
		SpeedObject speedObj = eof;
		if (passCtr < maxCtr) {
			speedObj = speeds.get(passCtr);
		}
		return speedObj.getSpeed();
	}

	public double[] getDistance() {
		SpeedObject speedObj = eof;
		if (passCtr < maxCtr) {
			speedObj = speeds.get(passCtr);
		}
		return speedObj.getDistance();
	}

	public double[] getVelocity() {
		SpeedObject speedObj = eof;
		if (passCtr < maxCtr) {
			speedObj = speeds.get(passCtr);
		}
		return speedObj.getVelocity();
	}
}
