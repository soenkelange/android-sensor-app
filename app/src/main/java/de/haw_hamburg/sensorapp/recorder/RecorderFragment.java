package de.haw_hamburg.sensorapp.recorder;

import de.haw_hamburg.sensorapp.BaseMvpFragment;
import de.haw_hamburg.sensorapp.R;

/**
 * Use the {@link RecorderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecorderFragment extends BaseMvpFragment<RecorderPresenter, RecorderView> implements RecorderView {

    public RecorderFragment() {
        // Required empty public constructor
    }

    public static RecorderFragment newInstance() {
        return new RecorderFragment();
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
