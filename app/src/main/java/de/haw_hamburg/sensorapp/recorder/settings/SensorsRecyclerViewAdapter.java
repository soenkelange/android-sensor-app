package de.haw_hamburg.sensorapp.recorder.settings;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.List;

import de.haw_hamburg.sensorapp.R;
import de.haw_hamburg.sensorapp.widget.SectionedRecyclerViewAdapter;

/**
 * Created by s.lange on 13.02.17.
 */
class SensorsRecyclerViewAdapter extends SectionedRecyclerViewAdapter<SensorsRecyclerViewAdapter.HeaderViewHolder, SensorsRecyclerViewAdapter.ItemViewHolder> {

    private OnSensorCheckedChangeListener listener;
    private  OnHeaderCheckedChangeListener onHeaderCheckedChangeListener;
    private List<SensorCategory> sensorCategories;

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
        SensorCategory sensorCategory = sensorCategories.get(section);
        holder.setText(sensorCategory.getName());
        holder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onHeaderCheckedChangeListener != null) {
                    onHeaderCheckedChangeListener.onHeaderCheckedChanged(section, isChecked);
                    for (int i = 0; i < sensorCategories.get(section).getSensorsCount(); i++) {
                        sensorCategories.get(section).getSensor(i).setEnabled(isChecked);
                    }
                }
            }
        });
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder holder, int section, int item) {
        Sensor sensor = sensorCategories.get(section).getSensor(item);
        holder.setText(sensor.getName());
        holder.setSection(section);
        holder.setChecked(sensor.isEnabled());
        holder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (listener != null) {
                    listener.onSensorCheckedChanged(sensor, isChecked);
                    sensorCategories.get(section).getSensor(item).setEnabled(isChecked);
                }
            }
        });
    }

    @Override
    public int getSectionCount() {
        return sensorCategories.size();
    }

    @Override
    public int getItemCountForSection(int section) {
        return sensorCategories.get(section).getSensorsCount();
    }

    public OnSensorCheckedChangeListener getListener() {
        return listener;
    }

    public void setListener(OnSensorCheckedChangeListener listener) {
        this.listener = listener;
    }

    public void setOnHeaderCheckedChangeListener(OnHeaderCheckedChangeListener listener) {
        this.onHeaderCheckedChangeListener = listener;
    }

    public List<SensorCategory> getSensorCategories() {
        return sensorCategories;
    }

    public void setSensorCategories(List<SensorCategory> sensorCategories) {
        this.sensorCategories = sensorCategories;
    }


    public interface OnSensorCheckedChangeListener {
        void onSensorCheckedChanged(Sensor sensor, boolean isChecked);
    }

    public interface OnHeaderCheckedChangeListener {
        void onHeaderCheckedChanged(int section, boolean isChecked);
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final Switch subheaderSwitch;

        private HeaderViewHolder(View view) {
            super(view);
            subheaderSwitch = (Switch) itemView.findViewById(R.id.subheader);
        }

        private void setText(String text) {
            this.subheaderSwitch.setText(text);
        }

        private void setChecked(boolean enabled) {
            subheaderSwitch.setChecked(enabled);
        }

        private void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
            subheaderSwitch.setOnCheckedChangeListener(listener);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private final Switch textSwitchView;
        private int section;

        private ItemViewHolder(View view) {
            super(view);
            textSwitchView = (Switch) itemView.findViewById(R.id.textSwitch);
        }

        private void setText(String text) {
            this.textSwitchView.setText(text);
        }

        public void setChecked(boolean enabled) {
            textSwitchView.setChecked(enabled);
        }

        public int getSection() {
            return section;
        }

        private void setSection(int section) {
            this.section = section;
        }

        private void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
            textSwitchView.setOnCheckedChangeListener(listener);
        }
    }
}
