package ravenrobotics.cannonbot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMTalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import ravenrobotics.cannonbot.Constants;

public class DriveSubsystem extends SubsystemBase {
    private final DifferentialDrive drive;

    private static DriveSubsystem instance;

    /**
     * Returns the active instance of DriveSubsystem.
     *
     * @return The DriveSubsystem instance.
     */
    public static DriveSubsystem getInstance() {
        //If the DriveSubsystem instance hasn't been created yet, create it.
        if (instance == null) {
            instance = new DriveSubsystem();
        }

        return instance;
    }

    /**
     * The subsystem for controlling the cannon bot's wheels and making it move. <strong>DO NOT USE DIRECTLY</strong>
     */
    private DriveSubsystem() {
        //Create the motors on their respective channels.
        PWMTalonSRX frontLeft = new PWMTalonSRX(Constants.DriveConstants.FRONT_LEFT);
        PWMTalonSRX frontRight = new PWMTalonSRX(Constants.DriveConstants.FRONT_RIGHT);
        PWMTalonSRX backLeft = new PWMTalonSRX(Constants.DriveConstants.BACK_LEFT);
        PWMTalonSRX backRight = new PWMTalonSRX(Constants.DriveConstants.BACK_RIGHT);

        //Add the back motors as followers for their respective sides to simplify interfacing.
        frontLeft.addFollower(backLeft);
        frontRight.addFollower(backRight);

        //Set the left side as inverted to ensure all motors go the same direction.
        frontLeft.setInverted(true);

        //Create the DifferentialDrive object to simplify drive commands.
        drive = new DifferentialDrive(frontLeft, frontRight);
    }

    /**
     * Drives the robot using the <strong>Tank Drive</strong> control scheme.
     *
     * @param leftSpeed  The target speed to give to the left side.
     * @param rightSpeed The target speed to give to the right side.
     */
    public void tankDrive(double leftSpeed, double rightSpeed) {
        drive.tankDrive(leftSpeed, rightSpeed);
    }

    /**
     * Drives the robot using the <strong>Arcade Drive</strong> control scheme.
     *
     * @param forwardSpeed    The target forward/backward speed.
     * @param rotationalSpeed The target rotational speed.
     */
    public void arcadeDrive(double forwardSpeed, double rotationalSpeed) {
        drive.arcadeDrive(forwardSpeed, rotationalSpeed);
    }
}
