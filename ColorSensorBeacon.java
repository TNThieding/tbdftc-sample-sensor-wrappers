package com.tbdftc.sensorhandlers;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.tbdftc.fieldhandlers.AllianceColor;
import com.tbdftc.fieldhandlers.BeaconColor;

import static com.tbdftc.fieldhandlers.BeaconColor.*;

public class ColorSensorBeacon {

    private ColorSensor colorSensor;
    private int virtualZeroRed;
    private int virtualZeroBlue;

    public ColorSensorBeacon(ColorSensor aColorSensor, int i2cAddress) {
        colorSensor = aColorSensor;
        colorSensor.setI2cAddress(I2cAddr.create8bit(i2cAddress));
        colorSensor.enableLed(false);

        virtualZeroRed = 0;
        virtualZeroBlue = 0;
    }

    public BeaconColor getCurrentBeaconColor() {
        if(getCurrentRed() > getCurrentBlue())  return RED_LIGHT;
        else if(getCurrentBlue() > getCurrentRed()) return BLUE_LIGHT;
        else return INDETERMINATE;
    }

    public int getCurrentRed() {
        return colorSensor.red() - virtualZeroRed;
    }

    public int getCurrentBlue() {
        return colorSensor.blue() - virtualZeroBlue;
    }

    public String getRgbText() {
        return "R = " + colorSensor.red() + ", G = " + colorSensor.green() + ", B = " + colorSensor.blue();
    }

    public void zeroSensorValues() {
        virtualZeroRed = colorSensor.red();
        virtualZeroBlue =  colorSensor.blue();
    }

}
