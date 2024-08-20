package ravenrobotics.cannonbot.commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.Command;
import ravenrobotics.cannonbot.Constants;
import ravenrobotics.cannonbot.subsystems.DriveSubsystem;
import ravenrobotics.cannonbot.util.Telemetry;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class DriveCommand extends Command {
    private final DriveSubsystem driveSubsystem;

    private final DoubleSupplier leftSupplier, rightSupplier, rotationSupplier;
    private final SlewRateLimiter leftLimiter, rightLimiter, rotationLimiter;

    private final BooleanSupplier driveModeSupplier;

    private final GenericEntry driveModeEntry;

    /**
     * The command for driving the robot.<br/>
     * Supports driving in the <strong>Tank Drive</strong> control scheme or the <strong>Arcade Drive</strong> control scheme.
     *
     * @param leftSupplier      The DoubleSupplier for the left side of the robot, or the horizontal speed of the robot.
     * @param rightSupplier     The DoubleSupplier for the right side of the robot.
     * @param rotationSupplier  The DoubleSupplier for rotating the robot.
     * @param driveModeSupplier The BooleanSupplier for the active control scheme.
     */
    public DriveCommand(
            DoubleSupplier leftSupplier,
            DoubleSupplier rightSupplier,
            DoubleSupplier rotationSupplier,
            BooleanSupplier driveModeSupplier) {
        //Get the active instance of the drive subsystem for use.
        this.driveSubsystem = DriveSubsystem.getInstance();

        //Initialize the suppliers with the provided ones.
        this.leftSupplier = leftSupplier;
        this.rightSupplier = rightSupplier;
        this.rotationSupplier = rotationSupplier;

        //Initialize the limiters with the slew rates provided in Constants.java.
        this.leftLimiter = new SlewRateLimiter(Constants.DSConstants.LEFT_SLEW_RATE);
        this.rightLimiter = new SlewRateLimiter(Constants.DSConstants.RIGHT_SLEW_RATE);
        this.rotationLimiter = new SlewRateLimiter(Constants.DSConstants.ROTATION_SLEW_RATE);

        //Initialize the drive mode supplier with the provided one.
        this.driveModeSupplier = driveModeSupplier;

        //Create the Shuffleboard entry for the driving mode.
        this.driveModeEntry = Telemetry.createEntry("Drive Mode", BuiltInWidgets.kTextView, "Tank Drive");

        addRequirements(this.driveSubsystem);
    }

    @Override
    public void initialize() {
        //Switch to the cannon bot tab in Shuffleboard so that the computer operator sees the proper data/actions.
        Telemetry.switchToTab();
    }

    @Override
    public void execute() {
        //If the driver is using the arcade drive scheme, send the inputs based off of the relevant suppliers.
        if (driveModeSupplier.getAsBoolean()) {
            double forwardSpeed, rotationSpeed;

            //Filter (rate limit) the forward and rotation inputs so the motors don't over-torque.
            forwardSpeed = leftLimiter.calculate(leftSupplier.getAsDouble());
            rotationSpeed = rotationLimiter.calculate(rotationSupplier.getAsDouble());

            //Send the filtered inputs to the drive subsystem.
            driveSubsystem.arcadeDrive(forwardSpeed, rotationSpeed);

            //Update the drive mode entry on Shuffleboard to display "Arcade Drive".
            driveModeEntry.setString("Arcade Drive");
        } else { //If the driver is using the tank drive scheme, send the inputs based off of the relevant suppliers.
            double leftSpeed, rightSpeed;

            //Filter (rate limit) the left and right inputs so the motors don't over-torque.
            leftSpeed = leftLimiter.calculate(leftSupplier.getAsDouble());
            rightSpeed = rightLimiter.calculate(rightSupplier.getAsDouble());

            //Send the inputs to the drive subsystem.
            driveSubsystem.tankDrive(leftSpeed, rightSpeed);

            //Update the drive mode entry on Shuffleboard to display "Arcade Drive".
            driveModeEntry.setString("Tank Drive");
        }
    }
}
