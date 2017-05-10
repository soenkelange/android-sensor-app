package de.haw_hamburg.sensorapp.recorder;

import com.github.mikephil.charting.charts.LineChart;

import java.util.Timer;
import java.util.TimerTask;

import de.haw_hamburg.sensorapp.recorder.settings.Sensor;

/**
 * Created by s.lange on 08.05.17.
 */

public class SensorLineChart {

    private final Sensor sensor;
    private final LineChart lineChart;
    private boolean visible;
    private boolean acceptsRequest;
    private Timer timer;

    public SensorLineChart(Sensor sensor, LineChart lineChart) {
        this.sensor = sensor;
        this.lineChart = lineChart;
        this.visible = false;
        acceptsRequest = true;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setAcceptsRequest(true);
            }
        }, 0,50);
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

    public void setAcceptsRequest(boolean acceptsRequest) {
        this.acceptsRequest = acceptsRequest;
    }

    public boolean getAcceptsRequest() {
        return acceptsRequest;
    }
}
