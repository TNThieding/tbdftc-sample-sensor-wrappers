package com.tbdftc.sensorhandlers;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.tbdftc.controlhandlers.RollingAverageCalculator;

public class GyroscopeHeading {

    private static final int CLOCKWISE = -1;
    private static final int COUNTERCLOCKWISE = 1;
    private static final int VALUES_TO_AVERAGE = 8;

    private ModernRoboticsI2cGyro gyroscope;
    private RollingAverageCalculator rollingAverage;
    private int virtualHeadingZero;

    public GyroscopeHeading(GyroSensor aGyroscope) {
        gyroscope = (ModernRoboticsI2cGyro) aGyroscope;
        rollingAverage = new RollingAverageCalculator(VALUES_TO_AVERAGE);
        virtualHeadingZero = 0;
        calibrate();
    }

    private void calibrate() {
        gyroscope.calibrate();
    }

    public double getAverageZAxisHeading() {
        rollingAverage.addNumber(CLOCKWISE * (gyroscope.getIntegratedZValue() - virtualHeadingZero));
        return rollingAverage.getAverage();
    }

    public int getZAxisHeading() {
        return CLOCKWISE * (gyroscope.getIntegratedZValue() - virtualHeadingZero);
    }

    public int getZAxisRate() {
        return gyroscope.rawZ();
    }

    private boolean isCalibrated() {
        return !gyroscope.isCalibrating();
    }

    public void resetZAxisHeading() {
        virtualHeadingZero = gyroscope.getIntegratedZValue();
    }

    public void resetZAxisHeadingPhysically() {
        gyroscope.resetZAxisIntegrator();
    }

}
