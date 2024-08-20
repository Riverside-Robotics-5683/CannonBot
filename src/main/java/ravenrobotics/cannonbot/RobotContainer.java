package ravenrobotics.cannonbot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import ravenrobotics.cannonbot.commands.*;
import ravenrobotics.cannonbot.subsystems.DriveSubsystem;
import ravenrobotics.cannonbot.subsystems.PneumaticsSubsystem;

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

        //Add command for arming the pneumatics from the dashboard (safer than from the controller).
        Shuffleboard.getTab("Cannon Bot").add("ARM PNEUMATICS", new ArmPneumaticsCommand());
        configBindings();
    }

    @SuppressWarnings("SpellCheckingInspection")
    private void configBindings() {
        //Toggle between drive modes when the back button is pressed.
        driveController.back().toggleOnTrue(new InstantCommand(() -> driveMode = true));
        driveController.back().toggleOnFalse(new InstantCommand(() -> driveMode = false));

        //Fire the pneumatics system when the start button is pressed.
        driveController.start().onTrue(new FirePneumaticsCommand());

        //Each button in the YXBA area corresponds to a barrel, and sets it as the active barrel to fire.
        driveController.y().onTrue(new InstantCommand(() -> PneumaticsSubsystem.getInstance().setActiveBarrel(PneumaticsSubsystem.BARRELS.BARREL_ONE), PneumaticsSubsystem.getInstance()));
        driveController.x().onTrue(new InstantCommand(() -> PneumaticsSubsystem.getInstance().setActiveBarrel(PneumaticsSubsystem.BARRELS.BARREL_TWO), PneumaticsSubsystem.getInstance()));
        driveController.b().onTrue(new InstantCommand(() -> PneumaticsSubsystem.getInstance().setActiveBarrel(PneumaticsSubsystem.BARRELS.BARREL_THREE), PneumaticsSubsystem.getInstance()));
        driveController.a().onTrue(new InstantCommand(() -> PneumaticsSubsystem.getInstance().setActiveBarrel(PneumaticsSubsystem.BARRELS.BARREL_FOUR), PneumaticsSubsystem.getInstance()));

        //If either of the triggers is depressed enough, disarm the pneumatics.
        driveController.leftTrigger(0.3).onTrue(new InstantCommand(() -> PneumaticsSubsystem.getInstance().disarmPneumatics(), PneumaticsSubsystem.getInstance()));
        driveController.rightTrigger(0.3).onTrue(new InstantCommand(() -> PneumaticsSubsystem.getInstance().disarmPneumatics(), PneumaticsSubsystem.getInstance()));
    }
}
