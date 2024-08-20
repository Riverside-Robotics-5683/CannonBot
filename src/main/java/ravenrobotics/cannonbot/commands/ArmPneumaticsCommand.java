package ravenrobotics.cannonbot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import ravenrobotics.cannonbot.subsystems.PneumaticsSubsystem;

public class ArmPneumaticsCommand extends Command {
    private final PneumaticsSubsystem pneumaticsSubsystem;

    public ArmPneumaticsCommand() {
        pneumaticsSubsystem = PneumaticsSubsystem.getInstance();

        addRequirements(pneumaticsSubsystem);
    }

    @Override
    public void initialize() {
        pneumaticsSubsystem.armPneumatics();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
