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
    private long lastTimeStamp;

    public SensorLineChart(Sensor sensor, LineChart lineChart) {
        this.sensor = sensor;
        this.lineChart = lineChart;
        lastTimeStamp = 0;
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

    public boolean getAcceptsRequest(long timeStamp) {
        if (lastTimeStamp +  50000000 <= timeStamp) {
            return true;
        }
        else {
            return false;
        }
    }

    public void setLastTimeStamp(long timeStamp) {
        lastTimeStamp = timeStamp;
    }
}
