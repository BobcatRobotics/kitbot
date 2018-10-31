package org.usfirst.frc.team177.robot.subsystems;

import org.usfirst.frc.team177.robot.commands.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;  

public class DriveTrain extends Subsystem {
	/** Inverts drive direction **/
	private static final double INVERT_MOTOR = -1.0;
	
	private WPI_TalonSRX leftFront;
	private WPI_VictorSPX rightFront;
	private WPI_TalonSRX skateBotEncoder;
	private double leftPower = 0.0;
	private double rightPower = 0.0;
	private boolean invertLeft = false;
	private boolean invertRight = false;
	private int mode=1; // 0=raw, 1=velocity, 2=position
	private double mode2time;
	private double mode2dt=0.02;
	
	private AHRS ahrs;

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
	public void setupLeftMotor(int lf, boolean invert) {
		leftFront = new WPI_TalonSRX(lf);
		leftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute,0,0);
		leftFront.setSelectedSensorPosition(0,0,0);
		leftFront.config_kF(0, 0.06, 0);
		leftFront.config_kP(0, 0.06, 0);
		leftFront.config_kI(0, 0.0006, 0);
		leftFront.config_kD(0, 2, 0);
		leftFront.config_IntegralZone(0, 2000, 0);
		invertLeft = invert;
		mode=1;
	}
	
	public void setupRightMotor(int rf, boolean invert) {
		rightFront = new WPI_VictorSPX(rf);
		skateBotEncoder= new WPI_TalonSRX(2);
		rightFront.follow(skateBotEncoder);
		skateBotEncoder.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute,0,0);
		skateBotEncoder.setSelectedSensorPosition(0,0,0);
		skateBotEncoder.config_kF(0, 0.039, 0);
		skateBotEncoder.config_kP(0, 0.02, 0);
		skateBotEncoder.config_kI(0, 0.0003, 0);
		skateBotEncoder.config_kD(0, 1.5, 0);
		skateBotEncoder.config_IntegralZone(0, 4000, 0);
		invertRight = invert;
		mode=1;
	}

    // methods to switch the drivetrain control mode
	public void setmode0() {
		leftFront.config_kF(0, 0.0, 0);
		leftFront.config_kP(0, 0.0, 0);
		leftFront.config_kI(0, 0.0, 0);
		leftFront.config_kD(0, 0.0, 0);
		leftFront.config_IntegralZone(0, 0, 0);
		skateBotEncoder.config_kF(0, 0.0, 0);
		skateBotEncoder.config_kP(0, 0.0, 0);
		skateBotEncoder.config_kI(0, 0.0, 0);
		skateBotEncoder.config_kD(0, 0.0, 0);
		skateBotEncoder.config_IntegralZone(0, 0, 0);
		mode=0;
	}
	public void setmode1() {
		leftFront.config_kF(0, 0.06, 0);
		leftFront.config_kP(0, 0.06, 0);
		leftFront.config_kI(0, 0.0006, 0);
		leftFront.config_kD(0, 2.0, 0);
		leftFront.config_IntegralZone(0, 2000, 0);
		skateBotEncoder.config_kF(0, 0.039, 0);
		skateBotEncoder.config_kP(0, 0.02, 0);
		skateBotEncoder.config_kI(0, 0.0003, 0);
		skateBotEncoder.config_kD(0, 1.5, 0);
		skateBotEncoder.config_IntegralZone(0, 4000, 0);
		// Clear any integral error from other modes
		leftFront.setIntegralAccumulator(0, 0, 0);
		skateBotEncoder.setIntegralAccumulator(0, 0, 0);
		mode=1;
	}
	
	public void setmode2() {
		leftFront.config_kF(0, 0.0, 0);
		leftFront.config_kP(0, 0.6, 0);
		leftFront.config_kI(0, 0.0002, 0);
		leftFront.config_kD(0, 2.0, 0);
		leftFront.config_IntegralZone(0, 2000, 0);
		skateBotEncoder.config_kF(0, 0.0, 0);
		skateBotEncoder.config_kP(0, 0.4, 0);
		skateBotEncoder.config_kI(0, 0.0001, 0);
		skateBotEncoder.config_kD(0, 2.0, 0);
		skateBotEncoder.config_IntegralZone(0, 4000, 0);
		// Reset position sensor to zero (so we don't try to unwind position from other modes)
		leftFront.setSelectedSensorPosition(0,0,0);
		skateBotEncoder.setSelectedSensorPosition(0,0,0);
		// Clear any integral error from other modes
		leftFront.setIntegralAccumulator(0, 0, 0);
		skateBotEncoder.setIntegralAccumulator(0, 0, 0);
		mode=2;
		mode2time=0.0;
	}

	public void setupGyro() {
		/***********************************************************************
		 * navX-MXP:
		 * - Communication via RoboRIO MXP (SPI, I2C, TTL UART) and USB.            
		 * - See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface.
		 * Multiple navX-model devices on a single robot are supported.
		 ************************************************************************/
        ahrs = new AHRS(SPI.Port.kMXP);
    	ahrs.enableLogging(false);
	}
	
	public void zeroGyroYaw() {
		ahrs.zeroYaw();
	}

	public double getLeftDistance() {
		return leftFront.getSelectedSensorPosition(0);
	}

	public double getLeftRate() {
		return leftFront.getSelectedSensorVelocity(0);
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

	public double getRightDistance() {
		return skateBotEncoder.getSelectedSensorPosition(0);
	}

	public double getRightRate() {
		return skateBotEncoder.getSelectedSensorVelocity(0);
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
		if ((leftPwr < 0.05) && (leftPwr > -0.05)) {
			leftPwr=0.0;
			leftFront.setIntegralAccumulator(0, 0, 0);
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
		if ((rightPwr < 0.05) && (rightPwr > -0.05)) {
			rightPwr=0.0;
			skateBotEncoder.setIntegralAccumulator(0, 0, 0);
		}

		this.rightPower = rightPwr;
	}

	public void drive() {
		drive(leftPower,rightPower);
	}
	
	public void drive(double leftPwr, double rightPwr) {
		if (invertLeft) leftPwr *= INVERT_MOTOR;
		if (invertRight) rightPwr *= INVERT_MOTOR;

		if (mode == 0) {
			// Just send basic request from sticks
			leftFront.set(leftPwr);
			skateBotEncoder.set(rightPwr);
		}
		if (mode == 1) {
			// Use request from sticks to set a velocity target
			// 8000 and 13329 are arbitrary limits on +/- velocity in native units/100msec
			// they are different because left side has 3 sensor rotation per 1 wheel rotation
			// and right side has 5 sensor rotations per 1 wheel rotation
			leftFront.set(ControlMode.Velocity, leftPwr*8000.0);
	        //use skateBotEncoder.set(...)  -- note, that this works because rightfront
	        //                                 has been set to follow skateBotEncoder.
			skateBotEncoder.set(ControlMode.Velocity, rightPwr*13329.0);
		}
		if (mode == 2) {
			// Drive to a position target, stomp on power request from sticks.
			// try calculating a curve of position from 0 to 8 rotations (each rotation is about a 12 inches)
            // calc right power first, since forward on skatebot is positive power on the right
			rightPwr=0.032814 - 0.427667*mode2time + 3.07246*mode2time*mode2time - 0.6827695*mode2time*mode2time*mode2time;
			//leftPwr=-1.0*rightPwr;
			leftPwr=rightPwr;
			mode2time = mode2time + mode2dt; // assume 20 msec per pass for now.
			// Make time count up & down from 0.0 to 3.0
			if (mode2time > 3.0) {
				mode2time = 3.0;
				mode2dt = -1.0*mode2dt;
			}
			if (mode2time < 0.0) {
				mode2time = 0.0;
				mode2dt = -1.0*mode2dt;
			}

			// try rotations (where each rotation is 4096 units, and left side
			//                       gearing is such that 3 sensor rotations=1wheel rotation)
	        leftFront.set(ControlMode.Position, leftPwr*4096.0*3.0);
			// try rotations (where each rotation is 4096 units, and right side
			//                       gearing is such that 5 sensor rotations=1wheel rotation)
	        skateBotEncoder.set(ControlMode.Position, rightPwr*4096.0*5.0);		
		}
	}

	public void stop() {
		leftPower = 0.0;
		rightPower = 0.0;
		leftFront.set(0.0);
		skateBotEncoder.set(0.0);
		rightFront.set(0.0);
	}

	public void reset() {
		leftPower = 0.0;
		rightPower = 0.0;
		rightFront.follow(skateBotEncoder);
	}
	
	public void displayMotorData() {
		SmartDashboard.putNumber("Mode", mode);
		SmartDashboard.putNumber("mode2time", mode2time);
		SmartDashboard.putNumber("Left Encoder Distance",    getLeftDistance());
		SmartDashboard.putNumber("Left Encoder Velocity",    getLeftRate());
		SmartDashboard.putNumber("Left Motor power request", getLeftPower());
		SmartDashboard.putNumber("Left Motor output %",      getLeftMotorPercent());
		SmartDashboard.putNumber("Left Motor output amps",   getLeftMotorCurrent());
		SmartDashboard.putBoolean("Left power request inverted",   invertLeft);
		
		SmartDashboard.putNumber("Right Encoder Distance",    getRightDistance());
		SmartDashboard.putNumber("Right Encoder Velocity",    getRightRate());
		SmartDashboard.putNumber("Right Motor power request", getRightPower());
		SmartDashboard.putNumber("Right Motor output %",      getRightMotorPercent());
		SmartDashboard.putNumber("Right Motor output amps",   getRightMotorCurrent());
		SmartDashboard.putBoolean("Right power request inverted",   invertRight);
 
	}
	public void displayGyroData () {
		/* Display 6-axis Processed Angle Data                                      */
		SmartDashboard.putBoolean("IMU_Connected",        ahrs.isConnected());
		SmartDashboard.putBoolean("IMU_IsCalibrating",    ahrs.isCalibrating());
		SmartDashboard.putNumber( "IMU_Yaw",              ahrs.getYaw());
		SmartDashboard.putNumber( "IMU_Pitch",            ahrs.getPitch());
		SmartDashboard.putNumber( "IMU_Roll",             ahrs.getRoll());

		/* Display tilt-corrected, Magnetometer-based heading (requires             */
		/* magnetometer calibration to be useful)                                   */
		//SmartDashboard.putNumber(   "IMU_CompassHeading",   ahrs.getCompassHeading());

		/* Display 9-axis Heading (requires magnetometer calibration to be useful)  */
		//SmartDashboard.putNumber(   "IMU_FusedHeading",     ahrs.getFusedHeading());

		/* These functions are compatible w/the WPI Gyro Class, providing a simple  */
		/* path for upgrading from the Kit-of-Parts gyro to the navx MXP            */
		SmartDashboard.putNumber(   "IMU_TotalYaw",         ahrs.getAngle());
		//SmartDashboard.putNumber(   "IMU_YawRateDPS",       ahrs.getRate());

		/* Display Processed Acceleration Data (Linear Acceleration, Motion Detect) */
		//SmartDashboard.putNumber(   "IMU_Accel_X",          ahrs.getWorldLinearAccelX());
		//SmartDashboard.putNumber(   "IMU_Accel_Y",          ahrs.getWorldLinearAccelY());
		//SmartDashboard.putBoolean(  "IMU_IsMoving",         ahrs.isMoving());
		//SmartDashboard.putBoolean(  "IMU_IsRotating",       ahrs.isRotating());

		/* Display estimates of velocity/displacement.  Note that these values are  */
		/* not expected to be accurate enough for estimating robot position on a    */
		/* FIRST FRC Robotics Field, due to accelerometer noise and the compounding */
		/* of these errors due to single (velocity) integration and especially      */
		/* double (displacement) integration.                                       */
		//SmartDashboard.putNumber(   "Velocity_X",           ahrs.getVelocityX());
		//SmartDashboard.putNumber(   "Velocity_Y",           ahrs.getVelocityY());
		//SmartDashboard.putNumber(   "Displacement_X",       ahrs.getDisplacementX());
		//SmartDashboard.putNumber(   "Displacement_Y",       ahrs.getDisplacementY());

		/* Display Raw Gyro/Accelerometer/Magnetometer Values                       */
		/* NOTE:  These values are not normally necessary, but are made available   */
		/* for advanced users.  Before using this data, please consider whether     */
		/* the processed data (see above) will suit your needs.                     */
		//SmartDashboard.putNumber(   "RawGyro_X",            ahrs.getRawGyroX());
		//SmartDashboard.putNumber(   "RawGyro_Y",            ahrs.getRawGyroY());
		//SmartDashboard.putNumber(   "RawGyro_Z",            ahrs.getRawGyroZ());
		//SmartDashboard.putNumber(   "RawAccel_X",           ahrs.getRawAccelX());
		//SmartDashboard.putNumber(   "RawAccel_Y",           ahrs.getRawAccelY());
		//SmartDashboard.putNumber(   "RawAccel_Z",           ahrs.getRawAccelZ());
		//SmartDashboard.putNumber(   "RawMag_X",             ahrs.getRawMagX());
		//SmartDashboard.putNumber(   "RawMag_Y",             ahrs.getRawMagY());
		//SmartDashboard.putNumber(   "RawMag_Z",             ahrs.getRawMagZ());
		//SmartDashboard.putNumber(   "IMU_Temp_C",           ahrs.getTempC());
		//SmartDashboard.putNumber(   "IMU_Timestamp",        ahrs.getLastSensorTimestamp());

		/* Omnimount Yaw Axis Information                                           */
		/* For more info, see http://navx-mxp.kauailabs.com/installation/omnimount  */
		//AHRS.BoardYawAxis yaw_axis = ahrs.getBoardYawAxis();
		//SmartDashboard.putString(   "YawAxisDirection",     yaw_axis.up ? "Up" : "Down" );
		//SmartDashboard.putNumber(   "YawAxis",              yaw_axis.board_axis.getValue() );

		/* Sensor Board Information                                                 */
		//SmartDashboard.putString(   "FirmwareVersion",      ahrs.getFirmwareVersion());

		/* Quaternion Data                                                          */
		/* Quaternions are fascinating, and are the most compact representation of  */
		/* orientation data.  All of the Yaw, Pitch and Roll Values can be derived  */
		/* from the Quaternions.  If interested in motion processing, knowledge of  */
		/* Quaternions is highly recommended.                                       */
		//SmartDashboard.putNumber(   "QuaternionW",          ahrs.getQuaternionW());
		//SmartDashboard.putNumber(   "QuaternionX",          ahrs.getQuaternionX());
		//SmartDashboard.putNumber(   "QuaternionY",          ahrs.getQuaternionY());
		//SmartDashboard.putNumber(   "QuaternionZ",          ahrs.getQuaternionZ());

		/* Connectivity Debugging Support                                           */
		//SmartDashboard.putNumber(   "IMU_Byte_Count",       ahrs.getByteCount());
		//SmartDashboard.putNumber(   "IMU_Update_Count",     ahrs.getUpdateCount());
	}
}