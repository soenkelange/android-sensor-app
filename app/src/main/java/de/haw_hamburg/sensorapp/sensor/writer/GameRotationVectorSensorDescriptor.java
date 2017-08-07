package de.haw_hamburg.sensorapp.sensor.writer;

import android.hardware.Sensor;

import java.util.Arrays;
import java.util.List;

/**
 * Created by s.lange on 04.05.17.
 */

public class GameRotationVectorSensorDescriptor extends SensorDescriptor {

    @Override
    public List<String> getHeaders() {
        return Arrays.asList(
                "Rotation vector component along the x axis (x * sin(θ/2))",
                "Rotation vector component along the y axis (y * sin(θ/2))",
                "Rotation vector component along the z axis (z * sin(θ/2))");
    }

    @Override
    public int getSensorType() {
        return Sensor.TYPE_GAME_ROTATION_VECTOR;
    }
}
