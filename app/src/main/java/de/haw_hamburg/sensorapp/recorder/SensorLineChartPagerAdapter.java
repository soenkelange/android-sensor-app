package de.haw_hamburg.sensorapp.recorder;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;

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
        SensorLineChart sensorLineChart = sensorLineCharts.get(position);
        LineChart lineChart = sensorLineChart.getLineChart();
        container.addView(lineChart);
        return lineChart;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
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
}
