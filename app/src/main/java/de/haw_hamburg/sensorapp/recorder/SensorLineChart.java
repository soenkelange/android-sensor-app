package de.haw_hamburg.sensorapp.recorder;

import com.github.mikephil.charting.charts.LineChart;

import de.haw_hamburg.sensorapp.recorder.settings.Sensor;

/**
 * Created by s.lange on 08.05.17.
 */

public class SensorLineChart {

    private final Sensor sensor;
    private final LineChart lineChart;
    private boolean visible;

    public SensorLineChart(Sensor sensor, LineChart lineChart) {
        this.sensor = sensor;
        this.lineChart = lineChart;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public LineChart getLineChart() {
        return lineChart;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }
}
