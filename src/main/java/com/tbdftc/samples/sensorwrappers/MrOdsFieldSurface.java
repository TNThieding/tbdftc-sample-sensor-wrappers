package com.tbdftc.samples.sensorwrappers;

import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.tbdftc.fieldhandlers.FieldSurface;

import static com.tbdftc.fieldhandlers.FieldSurface.*;

public class MrOdsFieldSurface {

    private static final double WHITE_TAPE_THRESHOLD_ADDEND = 0.125;

    private OpticalDistanceSensor opticalDistanceSensor;
    private double whiteTapeThreshold;

    public MrOdsFieldSurface(OpticalDistanceSensor aOpticalDistanceSensor) {
        opticalDistanceSensor = aOpticalDistanceSensor;
        whiteTapeThreshold = opticalDistanceSensor.getLightDetected() + WHITE_TAPE_THRESHOLD_ADDEND;
    }

    public FieldSurface getFieldSurface() {
        if(opticalDistanceSensor.getLightDetected() > whiteTapeThreshold) return WHITE_TAPE;
        else return FOAM_TILE;
    }

    public double getLightDetected() {
        return opticalDistanceSensor.getLightDetected();
    }

}
