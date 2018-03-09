package org.usfirst.frc.team177.lib;

import java.io.File;

import org.usfirst.frc.team177.robot.commands.RobotConstants;

import edu.wpi.first.wpilibj.DriverStation;

public class FileUtils {

	private static String path = File.separator + "home" + File.separator + "lvuser" + File.separator;
	private static String filename = null;
	private static SpeedFile speedFile = null;

	static {
		File newDir = new File(path);
		if (!newDir.exists()) {
			try {
				newDir.mkdir();
			} catch (SecurityException e) {
				String err = "RioLogger Security exception " + e;
				DriverStation.reportError(err, false);
				RioLogger.log(err);
			}
		}
		setFileName(RobotConstants.RECORD_FILE_NAME);
	}

	public static void setFileName(String utilityFile) {
		filename = path + utilityFile;
	}

	public static String getFileName() {
		return filename;
	}

	public static void startRecording() {
		speedFile = new SpeedFile(filename);
		speedFile.startRecording();
	}

	// TODO:: Clean Up add exception processing
	public static void stopRecording() {
		speedFile.stopRecording();
	}

	// TODO :: Cleanup add exception processing
	public static void readRecording() {
		speedFile = new SpeedFile(filename);
		speedFile.readRecordingFile();
	}

	public static SpeedObject getRawData(int index) {
		return speedFile.getRawData(index);
	}

	public static int getNbrOfRows() {
		return speedFile.getNbrOfRows();
	}

	public static void addSpeeds(double leftSpeed, double rightSpeed, double leftDistance, double rightDistance,
			double leftVelocity, double rightVelocity) {
		speedFile.addSpeed(leftSpeed, rightSpeed, leftDistance, rightDistance, leftVelocity, rightVelocity);
	}

	public static double[] getSpeed() {
		return speedFile.getSpeed();
	}

	public static double[] getDistance() {
		return speedFile.getDistance();
	}

	public static double[] getVelocitie() {
		return speedFile.getVelocity();
	}
}
