package ravenrobotics.cannonbot.util;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class Telemetry {
    private static final ShuffleboardTab tab = Shuffleboard.getTab("Cannon Bot");

    /**
     * Switches the active Shuffleboard tab to the robot's tab.
     */
    public static void switchToTab() {
        Shuffleboard.selectTab(tab.getTitle());
    }

    /**
     * Creates an entry in the robot's Shuffleboard tab for displaying data.
     *
     * @param entryName    The title/name of the entry.
     * @param widgetType   The type of widget to use (optional).
     * @param defaultValue The default value to put in the entry.
     * @return The entry's GenericEntry for value modification.
     */
    public static GenericEntry createEntry(String entryName, BuiltInWidgets widgetType, Object defaultValue) {
        return tab.add(entryName, defaultValue).withWidget(widgetType).getEntry();
    }
}
