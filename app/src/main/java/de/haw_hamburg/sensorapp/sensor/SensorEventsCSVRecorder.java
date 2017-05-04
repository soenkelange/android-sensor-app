package de.haw_hamburg.sensorapp.sensor;

import android.hardware.SensorEvent;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.haw_hamburg.sensorapp.csv.CSVWriter;

/**
 * Created by s.lange on 03.05.17.
 */
public class SensorEventsCSVRecorder {

    private static final String TAG = SensorEventsCSVRecorder.class.getSimpleName();

    private final CSVWriter csvWriter;
    private final List<SensorEventCSVWriter> sensorEventCSVWriters;

    private boolean headerWritten = false;
    private Map<Integer, SensorEvent> cachedSensorEvents = new HashMap<>();
    private long lastTime;

    public SensorEventsCSVRecorder(CSVWriter csvWriter) {
        this.csvWriter = csvWriter;
        this.sensorEventCSVWriters = new ArrayList<>();
    }

    public void write(SensorEvent sensorEvent) {
        Log.d(TAG, "SensorEvent: " + sensorEvent.sensor.getName());
        cachedSensorEvents.put(sensorEvent.sensor.getType(), sensorEvent);
        if (!headerWritten) {
            writeHeaders();
        }
        long currentTime = new Date().getTime();
        if ((currentTime - lastTime) > 50) {
            writeCachedValues();
            lastTime = currentTime;
        }
    }

    private void writeHeaders() {
        List<String> headers = new ArrayList<>();
        for (SensorEventCSVWriter sensorEventCSVWriter : sensorEventCSVWriters) {
            headers.addAll(sensorEventCSVWriter.getHeaders());
        }
        writeLine(headers);
    }

    private void writeCachedValues() {
//        List<String> values = new ArrayList<>();
//
//        for (SensorEventCSVWriter sensorEventCSVWriter : sensorEventCSVWriters) {
//            SensorEvent cachedSensorEvent = cachedSensorEvents.get(sensorEventCSVWriter.getSensorType());
//            int numberOfValues = sensorEventCSVWriter.getHeaders().size();
//            for (int i = 0; i < numberOfValues; i++) {
//
//            }
//        }
//
//        writeLine(values);

//        List<String> values = new ArrayList<>();
//        for (SensorEventCSVWriter sensorEventCSVWriter : sensorEventCSVWriters) {
//            SensorEvent cachedSensorEvent = cachedSensorEvents.get(sensorEventCSVWriter.getSensorType());
//            if (cachedSensorEvent != null) {
//
//            }
//            List<String> sensorEventValues = sensorEventCSVWriter.getValues(cachedSensorEvent);
//            values.addAll(sensorEventValues);
//        }
//        writeLine(values);
    }

    private void writeLine(List<String> fields) {
        try {
            csvWriter.writeNextLine(fields);
        } catch (IOException e) {
            Log.e(TAG, "Could not write to CSV file", e);
        }
    }

    public void addSensorEventCSVWriter(SensorEventCSVWriter sensorEventsCSVWriter) {
        sensorEventCSVWriters.add(sensorEventsCSVWriter);
    }
}
