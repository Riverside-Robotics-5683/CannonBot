package ravenrobotics.cannonbot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import ravenrobotics.cannonbot.commands.DriveCommand;
import ravenrobotics.cannonbot.subsystems.DriveSubsystem;

public class RobotContainer {
    private final CommandXboxController driveController;

    private boolean driveMode = false;

    public RobotContainer() {
        driveController = new CommandXboxController(Constants.DSConstants.DRIVE_CONTROLLER);

        DriveCommand driveCommand = new DriveCommand(
                driveController::getLeftY,
                driveController::getRightY,
                driveController::getRightX,
                () -> driveMode
        );

        DriveSubsystem.getInstance().setDefaultCommand(driveCommand);

        configBindings();
    }

    private void configBindings() {
        driveController.leftBumper().toggleOnTrue(new InstantCommand(() -> driveMode = true));
        driveController.leftBumper().toggleOnFalse(new InstantCommand(() -> driveMode = false));
    }
}
