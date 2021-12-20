// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team3647.frc2021.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import team3647.lib.drivers.SparkMaxFactory;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final IdleMode robotIdleMode = IdleMode.kBrake;
    public static final int leftPin = 0; //arb, need to find
    public static final int rightPin = 1; //arb, need to find
    public static final int maxCurrentLim = 60;
    public static final int stallCurrent = 35;

    
    public static final SparkMaxFactory.Configuration leftConfig = 
        new SparkMaxFactory.Configuration(leftPin, false).currentLimiting(true, maxCurrentLim, stallCurrent).idleMode(IdleMode.kBrake).voltageCompensation(true,12);
        public static final SparkMaxFactory.Configuration rightConfig = 
        new SparkMaxFactory.Configuration(leftPin, true).currentLimiting(true, maxCurrentLim, stallCurrent).idleMode(IdleMode.kBrake).voltageCompensation(true,12);

}
