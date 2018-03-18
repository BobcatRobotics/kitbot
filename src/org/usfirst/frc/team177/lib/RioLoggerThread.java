package org.usfirst.frc.team177.lib;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.wpi.first.wpilibj.Timer;

public class RioLoggerThread {
	public static RioLoggerThread instance = new RioLoggerThread();
	private Thread logThread;

	private String path =  File.separator + "home" + File.separator + "lvuser" + File.separator + "logs";
	private String filename = path + File.separator + new SimpleDateFormat("yyyy-MM-dd_hh.mm.ss'.thread.txt'").format(new Date());

	private static List<String> logs = new ArrayList<String>();
	private static long totalLogTime = 3600 * 1000L; // Default is 1 hour (milliseconds)
	private static long logFrequency = 150 * 1000L;  // Default is 2 minutes 30 seconds
	private static double endTime = 0.0;
	private static boolean isLogging = false;

	private static class LogThread implements Runnable {
		private RioLoggerThread rioLoggerThread;

		LogThread(RioLoggerThread start ) {
			super();
			rioLoggerThread = start;
			rioLoggerThread.createLogDirectory();
			RioLogger.log("RioLoggerThread.LogThread constructor");
		}

		@Override
		public void run() {
			RioLogger.log("RioLoggerThread.LogThread run() started");
			rioLoggerThread.startLogging();
		}
	}

	/**
	 * Returns the instance of the Digit Board
	 * @return DigitBoard
	 */
	public static RioLoggerThread getInstance() {
		return instance;
	}

	private RioLoggerThread() {
		logThread = new Thread(new LogThread(this), "MXP_Display_Board");
		logThread.setPriority((Thread.NORM_PRIORITY + Thread.MAX_PRIORITY) / 2);
		if (isLogging)
			return;
		isLogging = true;
		logThread = new Thread(new LogThread(this));
		logThread.start();
	}

	// Set Logging Time (in seconds)
	public void setLoggingParameters(long totLogTime, long totFreq) {
		totalLogTime = totLogTime * 1000L;
		logFrequency = totFreq *1000L;
		//endTime = System.currentTimeMillis() + totalLogTime;
		//log("current time, end time " +System.currentTimeMillis() + ", " + endTime );
		//RioLogger.log("RioLoggerThread setParms() current time, end time " +System.currentTimeMillis() + ", " + endTime );
		endTime = Timer.getFPGATimestamp() + totalLogTime;
		log("current time, end time " + Timer.getFPGATimestamp() + ", " + endTime );
		RioLogger.log("RioLoggerThread setParms() current time, end time " +Timer.getFPGATimestamp()  + ", " + endTime );
		if (!isLogging) 
			instance.startLogging();
	}

	private void startLogging() {
		RioLogger.log("RioLoggerThread.startLogging() started");
		double cTime = 0.0;
		isLogging = true;
		do {
			try {
					Thread.sleep(logFrequency);
			} catch (InterruptedException e) {
				/* The thread can be interrupted by a request to write the logs */
				RioLogger.log("RioLoggerThread interrupted.");
				e.printStackTrace();
			}
			if (logs.size() > 0) {
				List<String> tempLog = new ArrayList<String>(logs);
				logs.clear();
				writeLog(tempLog);
			} 
			//RioLogger.log("run() current time, end time " +System.currentTimeMillis() + ", " + endTime );
			//RioLogger.log("run() isLogging " + isLogging);
			//cTime = System.currentTimeMillis();
			cTime = Timer.getFPGATimestamp();
		} while (isLogging && (cTime < endTime));
		log("RioLoggerThread ending");
		//cTime = System.currentTimeMillis();
		cTime = Timer.getFPGATimestamp();

		RioLogger.log("RioLoggerThread ending ending current time, end time " + cTime + ", " + endTime );
		writeLog(logs);
		logs.clear();
		isLogging = false;
	}
	
	public void log(String line) {
		logs.add(line);
	}

	public void stopLogging() {
		isLogging = false;
	}

	public boolean isLogging() {
		return isLogging;
	}

	/* This method will interrupt the current thread */
	/* write the log and then resume                 */
	public void writeLog() {
		logThread.interrupt();
	}
	

	private void createLogDirectory() {
		File newDir = new File(path);
		if (!newDir.exists()) {
			try {
				newDir.mkdir();
			} catch (SecurityException e) {
				RioLogger.log("RioLogger Security exception " + e);
				e.printStackTrace();
			}
		}
	}

	private void writeLog(List<String> log) {
		BufferedWriter outputStream;
		try {
			// Open the file
			outputStream = new BufferedWriter(new FileWriter(filename, true));
			for (String line : log) {
				outputStream.write(line);
				outputStream.newLine();
			}
			// Close the file
			outputStream.close();
		} catch (IOException e) {
			RioLogger.log("Error writing log " + e);
			e.printStackTrace();
		}
	}
}
