package ravenrobotics.cannonbot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import ravenrobotics.cannonbot.subsystems.DriveSubsystem;
import ravenrobotics.cannonbot.subsystems.PneumaticsSubsystem;

public class FirePneumaticsCommand extends Command {
    private final DriveSubsystem driveSubsystem;
    private final PneumaticsSubsystem pneumaticsSubsystem;

    private boolean isFinished;

    public FirePneumaticsCommand() {
        this.driveSubsystem = DriveSubsystem.getInstance();
        this.pneumaticsSubsystem = PneumaticsSubsystem.getInstance();

        addRequirements(this.driveSubsystem, this.pneumaticsSubsystem);
    }

    @Override
    public void initialize() {
        driveSubsystem.tankDrive(0, 0);
    }

    @Override
    public void execute() {
        //Open the active barrel.
        pneumaticsSubsystem.openActiveBarrel();

        //TODO: Make the timing configurable to change range.
        //Wait 400 milliseconds.
        Timer.delay(0.4);

        //Close the barrel.
        pneumaticsSubsystem.closeBarrels();

        //Make sure the pneumatics system is disarmed.
        pneumaticsSubsystem.disarmPneumatics();

        isFinished = true;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
