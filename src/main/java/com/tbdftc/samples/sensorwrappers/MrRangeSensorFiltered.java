package com.tbdftc.samples.sensorwrappers;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;

public class MrRangeSensorFiltered {

    private int lastReadDistance;
    private byte[] sensorCache;
    private I2cDevice sensorDevice;
    private I2cDeviceSynch sensorReader;

    public MrRangeSensorFiltered(I2cDevice hardwareMapI2cDevice, int i2cAddress) {
        lastReadDistance = 0;
        sensorDevice = hardwareMapI2cDevice;
        sensorReader = new I2cDeviceSynchImpl(sensorDevice, I2cAddr.create8bit(i2cAddress), false);
        sensorReader.engage();
    }

    public int getUltrasonicCm() {
        sensorCache = sensorReader.read(0x04, 1);
        if((sensorCache[0] & 0xFF) != 255) lastReadDistance = sensorCache[0] & 0xFF; //Filters out sporadic values of 255.
        return lastReadDistance;
    }

}
