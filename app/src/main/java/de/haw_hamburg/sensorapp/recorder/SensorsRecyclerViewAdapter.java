package de.haw_hamburg.sensorapp.recorder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import de.haw_hamburg.sensorapp.R;
import de.haw_hamburg.sensorapp.widget.SectionedRecyclerViewAdapter;

/**
 * Created by s.lange on 29.12.16.
 */

class SensorsRecyclerViewAdapter extends SectionedRecyclerViewAdapter<SensorsRecyclerViewAdapter.HeaderViewHolder, SensorsRecyclerViewAdapter.ItemViewHolder> {

    private Sensors sensors = new Sensors();
    private OnSensorCheckedChangeListener listener;

    SensorsRecyclerViewAdapter() {
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_subheader, parent, false);
        return new HeaderViewHolder(v);
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent) {
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_switch, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder holder, int section) {
        SensorCategory sensorCategory = getSensorCategory(section);
        holder.setText(sensorCategory.getName());
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder holder, int section, int item) {
        final Sensor sensor = getSensor(section, item);
        holder.setText(sensor.getName());
        holder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (listener != null) {
                    listener.onCheckedChanged(sensor, isChecked);
                }
            }
        });
    }

    @Override
    public int getSectionCount() {
        return sensors.getSensorCategoryCount();
    }

    @Override
    public int getItemCountForSection(int section) {
        return getSensorCategory(section).getSensorCount();
    }

    void setSensors(Sensors sensors) {
        this.sensors = sensors;
        notifyDataSetChanged();
    }

    void setOnSensorCheckedChangeListener(OnSensorCheckedChangeListener listener) {
        this.listener = listener;
    }

    private SensorCategory getSensorCategory(int position) {
        return sensors.getSensorCategory(position);
    }

    private Sensor getSensor(int categoryPosition, int positionWithinCategory) {
        SensorCategory sensorCategory = getSensorCategory(categoryPosition);
        return sensorCategory.getSensor(positionWithinCategory);
    }

    interface OnSensorCheckedChangeListener {
        void onCheckedChanged(Sensor sensor, boolean isChecked);
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView subheaderTextView;

        HeaderViewHolder(View itemView) {
            super(itemView);
            subheaderTextView = (TextView) itemView.findViewById(R.id.subheader);
        }

        void setText(String text) {
            subheaderTextView.setText(text);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private final Switch textSwitchView;

        ItemViewHolder(View itemView) {
            super(itemView);
            textSwitchView = (Switch) itemView.findViewById(R.id.textSwitch);
        }

        void setText(String text) {
            textSwitchView.setText(text);
        }

        void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
            textSwitchView.setOnCheckedChangeListener(listener);
        }
    }
}
