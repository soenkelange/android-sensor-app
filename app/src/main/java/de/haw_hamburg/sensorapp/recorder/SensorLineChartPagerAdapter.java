package de.haw_hamburg.sensorapp.recorder;

import android.content.Context;
import android.graphics.Color;
import android.hardware.SensorEvent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
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
    private final int MAX_VISIBLE_ITEMS = 200;
    private static final int[] colorList = new int[7];

    static
    {
        colorList[0]= Color.parseColor("#F44336");
        colorList[1]= Color.parseColor("#2196F3");
        colorList[2]= Color.parseColor("#4CAF50");
        colorList[3]= Color.parseColor("#FFEB3B");
        colorList[4]= Color.parseColor("#795548");
        colorList[5]= Color.parseColor("#000000");
        colorList[6]= Color.parseColor("#9C27B0");
    }

    public SensorLineChartPagerAdapter(Context context) {
        this.context = context;
        this.sensorLineCharts = new ArrayList<>();
    }
    
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d(SensorLineChartPagerAdapter.class.getSimpleName(), "InstantaiteItem " + position);
        SensorLineChart sensorLineChart = sensorLineCharts.get(position);
        sensorLineChart.getLineChart().setData(new LineData());
        container.addView(sensorLineChart.getLineChart());
        sensorLineChart.setVisible(true);
        return sensorLineChart.getLineChart();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d(SensorLineChartPagerAdapter.class.getSimpleName(), "destroyItem " + position);
        SensorLineChart sensorLineChart = sensorLineCharts.get(position);
        container.removeView(sensorLineChart.getLineChart());
        sensorLineChart.getLineChart().clear();
        sensorLineChart.setVisible(false);
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
        SensorLineChart sensorLineChart = getSensorLiveChart(sensorEvent);
        LineChart lineChart = sensorLineChart.getLineChart();
        if (sensorLineChart.isVisible() && sensorLineChart.getAcceptsRequest(sensorEvent.timestamp)) {
            LineData lineData = lineChart.getLineData();
            for (int i = 0; i < sensorEvent.values.length; i++) {
                ILineDataSet sensorEventValueDataSet = lineData.getDataSetByIndex(i);
                if (sensorEventValueDataSet == null) {
                    sensorEventValueDataSet = createDataSet(i);
                    lineData.addDataSet(sensorEventValueDataSet);
                }
                if (lineData.getDataSetByIndex(i).getXMax() < 0) {
                    lineData.addEntry(new Entry(0, sensorEvent.values[i]), i);
                }
                else {
                    lineData.addEntry(new Entry(lineData.getDataSetByIndex(i).getXMax()+1, sensorEvent.values[i]), i);
                }
            }
            lineData.notifyDataChanged();
            if (lineData.getDataSetByIndex(0).getEntryCount() > MAX_VISIBLE_ITEMS) {
                for (int i = 0; i < lineData.getDataSetCount(); i++) {
                    lineData.removeEntry(lineData.getXMin(), i);
                }
            }
            lineData.notifyDataChanged();
            lineChart.notifyDataSetChanged();
            lineChart.setVisibleXRangeMaximum(MAX_VISIBLE_ITEMS);
            lineChart.setTouchEnabled(false);
            if (lineData.getXMax() > MAX_VISIBLE_ITEMS) {
                lineChart.moveViewToX(lineData.getXMax()- MAX_VISIBLE_ITEMS);
            }
            else {
                lineChart.invalidate();
            }
            sensorLineChart.setLastTimeStamp(sensorEvent.timestamp);
        }
    }

    private LineDataSet createDataSet(int i) {
        LineDataSet set = new LineDataSet(null, "DataSet #" + i);
        set.setLineWidth(2.5f);
        set.setColor(colorList[i%7]);
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
