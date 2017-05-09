package de.haw_hamburg.sensorapp.recorder;

import android.content.Context;
import android.graphics.Color;
import android.hardware.SensorEvent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import de.haw_hamburg.sensorapp.recorder.settings.Sensor;

/**
 * Created by s.lange on 08.05.17.
 */
public class SensorLineChartPagerAdapter extends PagerAdapter {

    private final Context context;
    private final List<SensorLineChart> sensorLineCharts;

    public SensorLineChartPagerAdapter(Context context) {
        this.context = context;
        this.sensorLineCharts = new ArrayList<>();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d(SensorLineChartPagerAdapter.class.getSimpleName(), "InstantaiteItem " + position);
        SensorLineChart sensorLineChart = sensorLineCharts.get(position);
        LineChart lineChart = sensorLineChart.getLineChart();
        container.addView(lineChart);
        return lineChart;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d(SensorLineChartPagerAdapter.class.getSimpleName(), "destroyItem " + position);
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return sensorLineCharts.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Sensor sensor = sensorLineCharts.get(position).getSensor();
        return sensor.getName();
    }

    public void addSensorLineChart(Sensor sensor) {
        LineChart lineChart = new LineChart(context);
        lineChart.setData(new LineData());
        lineChart.getDescription().setEnabled(false);
        sensorLineCharts.add(new SensorLineChart(sensor, lineChart));
        notifyDataSetChanged();
    }

    public void addSensorEvent(SensorEvent sensorEvent) {
        Log.d(SensorLineChartPagerAdapter.class.getSimpleName(), "addSensorEvent");
        SensorLineChart sensorLineChart = getSensorLiveChart(sensorEvent);
        LineChart lineChart = sensorLineChart.getLineChart();
        LineData lineData = lineChart.getLineData();
        for (int i = 0; i < sensorEvent.values.length; i++) {
            ILineDataSet sensorEventValueDataSet = lineData.getDataSetByIndex(i);
            if (sensorEventValueDataSet == null) {
                sensorEventValueDataSet = createDataSet(i);
                lineData.addDataSet(sensorEventValueDataSet);
            }
            lineData.addEntry(new Entry(sensorEvent.timestamp, sensorEvent.values[i]), i);
        }
        lineData.notifyDataChanged();
        lineChart.notifyDataSetChanged();
        lineChart.getXAxis().setLabelCount(200);
        lineChart.moveViewToX(sensorEvent.timestamp);
    }

    private LineDataSet createDataSet(int i) {
        LineDataSet set = new LineDataSet(null, "DataSet #" + i);
        set.setLineWidth(2.5f);
        set.setDrawCircles(false);
        return set;
    }

    private SensorLineChart getSensorLiveChart(SensorEvent sensorEvent) {
        for (SensorLineChart sensorLineChart :
                sensorLineCharts) {
            if (sensorEvent.sensor.getType() == sensorLineChart.getSensor().getType()) {
                return sensorLineChart;
            }
        }
        return null;
    }
}
