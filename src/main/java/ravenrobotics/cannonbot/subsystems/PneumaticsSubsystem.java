package ravenrobotics.cannonbot.subsystems;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import ravenrobotics.cannonbot.Constants;
import ravenrobotics.cannonbot.util.Telemetry;

public class PneumaticsSubsystem extends SubsystemBase {
    private final Solenoid barrelOne, barrelTwo, barrelThree, barrelFour;

    private boolean isArmed = false;
    private BARRELS activeBarrel = BARRELS.BARREL_ONE;

    private final GenericEntry activeBarrelEntry, isArmedEntry;

    private static PneumaticsSubsystem instance;

    public enum BARRELS {
        BARREL_ONE,
        BARREL_TWO,
        BARREL_THREE,
        BARREL_FOUR
    }

    /**
     * Returns the active instance of PneumaticsSubsystem.
     *
     * @return The active PneumaticsSubsystem instance.
     */
    public static PneumaticsSubsystem getInstance() {
        //If the PneumaticsSubsystem hasn't been created yet, create it.
        if (instance == null) {
            instance = new PneumaticsSubsystem();
        }

        return instance;
    }

    private PneumaticsSubsystem() {
        //Initialize the solenoids with the IDs from the PneumaticsConstants class.
        barrelOne = new Solenoid(Constants.PneumaticsConstants.PCM, PneumaticsModuleType.CTREPCM, Constants.PneumaticsConstants.BARREL_ONE);
        barrelTwo = new Solenoid(Constants.PneumaticsConstants.PCM, PneumaticsModuleType.CTREPCM, Constants.PneumaticsConstants.BARREL_TWO);
        barrelThree = new Solenoid(Constants.PneumaticsConstants.PCM, PneumaticsModuleType.CTREPCM, Constants.PneumaticsConstants.BARREL_THREE);
        barrelFour = new Solenoid(Constants.PneumaticsConstants.PCM, PneumaticsModuleType.CTREPCM, Constants.PneumaticsConstants.BARREL_FOUR);

        //Ensure the solenoids are closed so the system can continue pressurizing.
        barrelOne.set(false);
        barrelTwo.set(false);
        barrelThree.set(false);
        barrelFour.set(false);

        //Create the Shuffleboard entry displaying the active barrel.
        activeBarrelEntry = Telemetry.createEntry("Active Barrel", BuiltInWidgets.kTextView, 1);
        isArmedEntry = Telemetry.createEntry("Armed", BuiltInWidgets.kBooleanBox, false);
    }

    /**
     * Arms the pneumatics subsystem for use.
     */
    public void armPneumatics() {
        isArmed = true;
    }

    /**
     * Disarms the pneumatics subsystem.
     */
    public void disarmPneumatics() {
        isArmed = false;
    }

    /**
     * Sets the active barrel to be fired.
     *
     * @param activeBarrel The new active barrel.
     */
    public void setActiveBarrel(BARRELS activeBarrel) {
        this.activeBarrel = activeBarrel;
    }

    /**
     * Open the active barrel.
     */
    public void openActiveBarrel() {
        //If the pneumatics system isn't armed, exit.
        if (!isArmed) {
            return;
        }

        //Check which barrel is active, and open it.
        switch (activeBarrel) {
            case BARREL_ONE -> barrelOne.set(true);
            case BARREL_TWO -> barrelTwo.set(true);
            case BARREL_THREE -> barrelThree.set(true);
            case BARREL_FOUR -> barrelFour.set(true);
        }

        disarmPneumatics();
    }

    /**
     * Immediately closes all barrels.
     */
    public void closeBarrels() {
        barrelOne.set(false);
        barrelTwo.set(false);
        barrelThree.set(false);
        barrelFour.set(false);

        disarmPneumatics();
    }

    @Override
    public void periodic() {
        activeBarrelEntry.setDouble(activeBarrel.ordinal() + 1);
        isArmedEntry.setBoolean(isArmed);
    }
}
