package de.haw_hamburg.sensorapp.recorder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import de.haw_hamburg.sensorapp.BaseMvpFragment;
import de.haw_hamburg.sensorapp.R;

/**
 * Use the {@link RecorderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecorderFragment extends BaseMvpFragment<RecorderPresenter, RecorderView> implements RecorderView {

    private RecyclerView sensorsRecyclerView;

    public RecorderFragment() {
        // Required empty public constructor
    }

    public static RecorderFragment newInstance() {
        return new RecorderFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sensorsRecyclerView = (RecyclerView) view.findViewById(R.id.sensorsRecyclerView);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recorder;
    }

    @Override
    public RecorderPresenter providePresenter() {
        return new RecorderPresenter();
    }
}
