package org.usfirst.frc.team177.lib;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import edu.wpi.first.wpilibj.DriverStation;

public class RioLogger {
	private static String path =  File.separator + "home" + File.separator + "lvuser" + File.separator + "logs";
	private static String filename = path + "riolog.txt";
	
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

	public static void log(String line) {
		BufferedWriter outputStream;

		try {
			// Open the file
			outputStream = new BufferedWriter(new FileWriter(filename, true));
			outputStream.write(line);
			outputStream.newLine();

			// Close the file
			outputStream.close();
		} catch (IOException e) {
			System.out.print("Error writing log " + e);
			e.printStackTrace();
		}
	}

}
