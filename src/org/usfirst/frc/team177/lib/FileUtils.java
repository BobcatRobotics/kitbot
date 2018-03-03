package org.usfirst.frc.team177.lib;

import java.io.File;

import edu.wpi.first.wpilibj.DriverStation;

public class FileUtils {
	public static final int MAX_SPEED_OBJECTS = 1000;
	
	private static String path =  File.separator + "home" + File.separator + "lvuser" + File.separator;
	private static String filename = null;
	private static SpeedObject so = new SpeedObject();
	
	static {
		File newDir = new File(path);
		if (!newDir.exists()) {
			try {
				newDir.mkdir();
			} catch (SecurityException e) {
				String err = "RioLogger Security exception " + e;
				DriverStation.reportError(err, false);
				System.out.println(err);
			}
		}
	}
	
	public static void setFileName(String utilityFile) { 
		filename = path + utilityFile;
	}
	
	public static String getFileName() {
		return filename;
	}
	
	public static void startRecording() {
		so.startRecording();
	}
	
	// TODO:: Clean Up add exception processing
	public static void stopRecording() {
		so.stopRecording();
	}
	
	public static void addSpeed(double leftSpeed,double rightSpeed) {
		so.addSpeed(leftSpeed, rightSpeed);
	}
	
	public static double[] getSpeeds() {
		return so.getSpeeds();
	}
	
	// TODO :: Cleanup add exception processing
	public static void readRecording () {
		so.readRecordingFile();
	}

}
	
