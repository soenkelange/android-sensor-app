package de.haw_hamburg.sensorapp.recorder;

import java.util.Collections;
import java.util.List;

/**
 * Created by s.lange on 30.12.16.
 */
public class Sensors {
    private final List<SensorCategory> sensorCategories;

    public Sensors() {
        sensorCategories = Collections.emptyList();
    }

    public Sensors(List<SensorCategory> sensorCategories) {
        this.sensorCategories = sensorCategories;
    }

    public SensorCategory getSensorCategory(int position)
    {
        return sensorCategories.get(position);
    }

    public int getSensorCategoryCount()
    {
        return sensorCategories.size();
    }
}
