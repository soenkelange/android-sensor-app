package de.haw_hamburg.sensorapp.sensor.writer;

import android.hardware.SensorEvent;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.haw_hamburg.sensorapp.csv.CSVWriter;

/**
 * Created by s.lange on 10.05.17.
 */

public class SensorEventsCSVWriter {
    private static final String TAG = SensorEventsCSVWriter.class.getSimpleName();
    private static final String EMPTY_STRING = "";
    private final CSVWriter csvWriter;
    private List<SensorDescriptor> sensorDescriptorList = new ArrayList<>();

    public SensorEventsCSVWriter(CSVWriter csvWriter) {
        this.csvWriter = csvWriter;
    }

    public void writeHeaders() {
        List<String> headers = new ArrayList<>();
        for (SensorDescriptor sensorDescriptor : sensorDescriptorList) {
            List<String> sensorEventValuesDescriptions = getSensorEventValuesDescriptions(sensorDescriptor);
            headers.addAll(sensorEventValuesDescriptions);
        }
        writeLine(headers);
    }

    private List<String> getSensorEventValuesDescriptions(SensorDescriptor sensorDescriptor) {
        List<String> sensorEventValuesDescriptions = new ArrayList<>();
        for (int i = 0; i < sensorDescriptor.getSensorEventValuesLength(); i++) {
            sensorEventValuesDescriptions.add(sensorDescriptor.getSensorEventValueDescription(i));
        }
        return sensorEventValuesDescriptions;
    }

    public void write(SensorEvent sensorEvent) {
        List<String> fields = new ArrayList<>();
        for (SensorDescriptor sensorDescriptor : sensorDescriptorList) {
            if (sensorEvent.sensor.getType() == sensorDescriptor.getSensorType()) {
                fields.addAll(getSensorEventValues(sensorEvent, sensorDescriptor));
            } else {
                fields.addAll(getPlaceholders(sensorDescriptor));
            }
        }
        writeLine(fields);
    }

    private List<String> getSensorEventValues(SensorEvent sensorEvent, SensorDescriptor sensorDescriptor) {
        List<String> values = new ArrayList<>();
        for (int i = 0; i < sensorDescriptor.getSensorEventValuesLength(); i++) {
            values.add(String.valueOf(sensorEvent.values[i]));
        }
        return values;
    }

    private List<String> getPlaceholders(SensorDescriptor sensorDescriptor) {
        List<String> values = new ArrayList<>();
        for (int i = 0; i < sensorDescriptor.getSensorEventValuesLength(); i++) {
            values.add(EMPTY_STRING);
        }
        return values;
    }

    private void writeLine(List<String> fields) {
        try {
            csvWriter.writeNextLine(fields);
        } catch (IOException e) {
            Log.e(TAG, "Could not write next line to CSV", e);
        }
    }

    public void addSensorDescriptor(SensorDescriptor sensorDescriptor) {
        sensorDescriptorList.add(sensorDescriptor);
    }
}
