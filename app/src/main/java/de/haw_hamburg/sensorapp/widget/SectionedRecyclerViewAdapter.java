package de.haw_hamburg.sensorapp.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by s.lange on 29.12.16.
 */

public abstract class SectionedRecyclerViewAdapter<HVH extends RecyclerView.ViewHolder, IVH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static final int HEADER_VIEW_TYPE = 1;
    static final int ITEM_VIEW_TYPE = 2;

    private boolean[] isHeader;
    private int[] section;
    private int[] indexWithinSection;

    public SectionedRecyclerViewAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_VIEW_TYPE) {
            return onCreateHeaderViewHolder(parent);
        }
        return onCreateItemViewHolder(parent);
    }

    public abstract HVH onCreateHeaderViewHolder(ViewGroup parent);

    public abstract IVH onCreateItemViewHolder(ViewGroup parent);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int header = getSection(position);
        int index = getIndexWithinSection(position);
        if (isSectionHeader(position)) {
            onBindHeaderViewHolder((HVH) holder, header);
        } else {
            onBindItemViewHolder((IVH) holder, header, index);
        }
    }

    public abstract void onBindHeaderViewHolder(HVH holder, int section);

    public abstract void onBindItemViewHolder(IVH holder, int section, int item);

    @Override
    public int getItemCount() {
        int headerCount = getSectionCount();
        int count = headerCount;
        for (int i = 0; i < headerCount; i++) {
            count += getItemCountForSection(i);
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (isSectionHeader(position)) {
            return HEADER_VIEW_TYPE;
        }
        return ITEM_VIEW_TYPE;
    }

    private boolean isSectionHeader(int position) {
        if (isHeader == null) {
            setupIndices();
        }
        return isHeader[position];
    }

    private int getSection(int position) {
        if (section == null) {
            setupIndices();
        }
        return section[position];
    }

    private int getIndexWithinSection(int position) {
        return indexWithinSection[position];
    }

    private void setupIndices() {
        int sectionCount = getSectionCount();
        int itemCount = getItemCount();
        isHeader = new boolean[itemCount];
        section = new int[itemCount];
        indexWithinSection = new int[itemCount];
        int position = 0;
        for (int s = 0; s < sectionCount; s++) {
            isHeader[position] = true;
            section[position] = s;
            indexWithinSection[position] = -1;
            position++;
            int itemCountForSection = getItemCountForSection(s);
            for (int i = 0; i < itemCountForSection; i++) {
                isHeader[position] = false;
                this.section[position] = s;
                indexWithinSection[position] = i;
                position++;
            }
        }
    }

    public abstract int getSectionCount();

    public abstract int getItemCountForSection(int section);

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            this.setIsRecyclable(false);
        }
    }
}
