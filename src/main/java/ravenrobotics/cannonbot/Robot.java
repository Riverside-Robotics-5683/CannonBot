// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package ravenrobotics.cannonbot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import ravenrobotics.cannonbot.subsystems.PneumaticsSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the methods corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot
{
    /**
     * This method is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        new RobotContainer();
    }


    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }


    @Override
    public void autonomousPeriodic() {}


    @Override
    public void teleopPeriodic() {}


    @Override
    public void disabledInit() {
        CommandScheduler.getInstance().cancelAll();
        PneumaticsSubsystem.getInstance().disarmPneumatics();
    }


    @Override
    public void disabledPeriodic() {}


    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
        PneumaticsSubsystem.getInstance().disarmPneumatics();
    }


    @Override
    public void testPeriodic() {}


    @Override
    public void simulationPeriodic() {}
}
