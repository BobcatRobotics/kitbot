package org.usfirst.frc.team177.lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SpeedObject {
	private double[] leftSpeeds = new double[FileUtils.MAX_SPEED_OBJECTS];
	private double[] rightSpeeds = new double[FileUtils.MAX_SPEED_OBJECTS];
	private double[] deltaTimes = new double[FileUtils.MAX_SPEED_OBJECTS];
	private int passCtr = 0;
	private int maxCtr = 0;
	private double currentTime = 0.0;
		
	public SpeedObject() {
		currentTime = Timer.getFPGATimestamp();
	}
	
	private void reset() {
		passCtr = 0;
		maxCtr = 0;
		for (int x = 0; x < FileUtils.MAX_SPEED_OBJECTS; x++ ) {
			deltaTimes[x] = 0.0;
			leftSpeeds[x] = 0.0;
			rightSpeeds[x] = 0.0;
		}
	}
	
	public void startRecording() {
		reset();
		currentTime = Timer.getFPGATimestamp();
	}

	public void addSpeed(double leftSpeed,double rightSpeed) {
		if (passCtr < 1000) {
			leftSpeeds[passCtr] = leftSpeed;
			rightSpeeds[passCtr] = rightSpeed;
			
			deltaTimes[passCtr] = Timer.getFPGATimestamp() - currentTime;
			currentTime = Timer.getFPGATimestamp();
			
			maxCtr = passCtr;
			passCtr++;
		}
	}
	
	public void stopRecording () { 
		try {
			File file = new File(FileUtils.getFileName());
			FileWriter fileWriter = new FileWriter(file);
			for (int x = 0; x < maxCtr; x++) {
				fileWriter.write(x + " " + deltaTimes[x] + " " + leftSpeeds[x] + " " + rightSpeeds[x]);
				fileWriter.write("\n");
			}
			fileWriter.write("999 999.0 0.0 0.0\n");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			String err = "FileUtils.stopRecoring() error " + e.getMessage();
			DriverStation.reportError(err, false);
			RioLogger.log(err);	
		}
	}
	
	public double[] getSpeeds() {
		double [] row = new double[3];
				
		if (passCtr < maxCtr) {
			row[0] = deltaTimes[passCtr];
			row[1] = leftSpeeds[passCtr];
			row[2] = rightSpeeds[passCtr];
		}
		else {
			row[0] = 999.0;
			row[1] = 0.0;
			row[2] = 0.0;
		}
		passCtr++;
		return row;
	}
	
	public void readRecordingFile() { 
		reset();

		Scanner sc;
		try {
			sc = new Scanner(new File(FileUtils.getFileName()));
			while (sc.hasNextLine()) {
				String row = sc.nextLine();
				String[] result = row.split("\\s");
				
				SmartDashboard.putNumber("reading counter", passCtr);
				
				deltaTimes[passCtr] = new Double(result[1]);
				leftSpeeds[passCtr] = new Double(result[2]);
				rightSpeeds[passCtr] = new Double(result[3]);
				
				if (deltaTimes[passCtr] > 998.0) {
					break;
				}
				maxCtr = passCtr;
				passCtr++;
				if (passCtr >= FileUtils.MAX_SPEED_OBJECTS)
					break;
			}
			
			//Read the file, now prime the passCounter for driving
			passCtr=0;
		} catch (FileNotFoundException e) {
			String err = "FileUtils.readRecoring() error " + e.getMessage();
			DriverStation.reportError(err, false);
			RioLogger.log(err);				
		}
	}

}
