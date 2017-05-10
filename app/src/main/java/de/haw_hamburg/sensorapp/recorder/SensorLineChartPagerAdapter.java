package de.haw_hamburg.sensorapp.recorder;

import android.content.Context;
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
import com.github.mikephil.charting.utils.ColorTemplate;

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
        container.addView(sensorLineChart.getLineChart());
        sensorLineChart.setVisible(true);
        return sensorLineChart.getLineChart();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d(SensorLineChartPagerAdapter.class.getSimpleName(), "destroyItem " + position);
        SensorLineChart sensorLineChart = sensorLineCharts.get(position);
        container.removeView(sensorLineChart.getLineChart());
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
        lineChart.notifyDataSetChanged();
        if (lineData.getDataSetByIndex(0).getEntryCount() > 200) {
            for (int i = 0; i < lineData.getDataSetCount(); i++) {
                lineData.removeEntry(lineData.getXMin(), i);
            }
        }
        lineChart.setVisibleXRangeMaximum(200);
        lineChart.setTouchEnabled(false);
        if (sensorLineChart.isVisible()) {
            lineData.notifyDataChanged();
            lineChart.notifyDataSetChanged();
            if (lineData.getXMax() > 200) {
                lineChart.moveViewToX(lineData.getXMax()-200);
            }
        }
    }

    private LineDataSet createDataSet(int i) {
        LineDataSet set = new LineDataSet(null, "DataSet #" + i);
        set.setLineWidth(2.5f);
        set.setColor(ColorTemplate.MATERIAL_COLORS[i%3]);
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
