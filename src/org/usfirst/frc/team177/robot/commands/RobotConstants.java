package org.usfirst.frc.team177.robot.commands;

public class RobotConstants {
	// Speed Constants for Auto and TeleopMode
	protected static final double INITIAL_LEFT_POWER_FORWARD = -0.7;
	protected static final double INITIAL_RIGHT_POWER_FORWARD = -0.7;

	// Constants for Starting Position Left or Right
	public static final double LR_DISTANCE_1 = 192.0;
	public static final double LR_DISTANCE_2 = 30.0;
	public static final double LR_DISTANCE_3 = 30.0;
	public static final double LR_TURN_ANGLE_1 = 36.0;
	public static final double LR_TURN_ANGLE_2 = -36.0;

	// Constants for Starting  Position Middle
	public static final double MID_MOTOR_SPEED = 0.4;
	public static final double MID_DISTANCE_1 = 30.0;
	public static final double MID_DISTANCE_2 = 45.0;
	public static final double MID_DISTANCE_3 = 45.0;
	public static final double MID_TURN_ANGLE_1 = 69.0;
	public static final double MID_TURN_ANGLE_2 = -69.0;
	
	// Constant to flip elevator motor speed command so
	// that pushing up on gamepad elevator thumb stick
	// makes the elevator go up.  This can also be used if
	// the motors get wired and you only need to flip one.
	//   -1.0 flips direction, 1.0 doesn't flip direction
	public static final double FLIP_ELEV_DIRECTION1 = -1.0;
	public static final double FLIP_ELEV_DIRECTION2 = -1.0;
}
