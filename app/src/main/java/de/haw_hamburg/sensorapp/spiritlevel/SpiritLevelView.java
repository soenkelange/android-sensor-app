package de.haw_hamburg.sensorapp.spiritlevel;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import de.haw_hamburg.sensorapp.R;

public class SpiritLevelView extends RelativeLayout {

    private ImageView spiritLevelIndicator;
    private int maxViewHeight;
    private int maxViewWidth;
    private int indicatorHeight;
    private int indicatorWidth;
    private int borderOffsetX;
    private int borderOffsetY;
    private int maxSpiritLevelHeight;
    private int maxSpiritLevelWidth;
    private float scalerPitch;
    private static final float BORDER_WIDTH_PERCENTAGE = 0.08f;
    private static final float BORDER_HEIGHT_PERCENTAGE = 80/1700f;

    public SpiritLevelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.view_spirit_level, this, true);
        spiritLevelIndicator = (ImageView) rootView.findViewById(R.id.spiritLevelIndicator);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        updateLayoutParameters();
    }

    private void updateLayoutParameters() {
        indicatorHeight = spiritLevelIndicator.getHeight();
        indicatorWidth = spiritLevelIndicator.getWidth();
        maxViewHeight = this.getHeight();
        maxViewWidth = this.getWidth();
        borderOffsetX = (int) ((maxViewWidth * BORDER_WIDTH_PERCENTAGE) / 2);
        borderOffsetY = (int) ((maxViewHeight * BORDER_HEIGHT_PERCENTAGE) / 2);
        maxSpiritLevelWidth = maxViewWidth - (2 * borderOffsetX);
        maxSpiritLevelHeight = maxViewHeight - (2 * borderOffsetY);
    }

    public void changeSpiritLevel(float pitch, float roll) {
        int targetY = calculatePosition(pitch, maxSpiritLevelHeight, borderOffsetY, indicatorHeight, false);
        int targetX = calculatePosition(roll, maxSpiritLevelWidth, borderOffsetX, indicatorWidth, true);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(spiritLevelIndicator, ImageView.X, targetX);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(spiritLevelIndicator, ImageView.Y, targetY);
        LinearInterpolator interpolator = new LinearInterpolator();
        animatorX.setInterpolator(interpolator);
        animatorY.setInterpolator(interpolator);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(interpolator);
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.start();
    }

    private int calculatePosition(float input, int dimension, int borderOffset, int indicatorDimension, boolean useRollFilter) {
        float adjustedInput = (input + 270) % 360;
        float scaler;
        if (!useRollFilter){
            if (adjustedInput <= 180) {
                scaler = adjustedInput / 180;
                scalerPitch = scaler;
            }
            else {
                float i = adjustedInput -180;
                scaler = 1 - (i / 180);
                scalerPitch = scaler;
            }
        }
        else {
            if (adjustedInput <= 180) {
                scaler = adjustedInput / 180;
                scaler = applyRollFilter(scaler);
            }
            else {
                float i = adjustedInput -180;
                scaler = 1 - (i / 180);
                scaler = applyRollFilter(scaler);
            }
        }
        int position = (int) ((scaler * dimension) + borderOffset - (indicatorDimension / 2));

        return position;
    }

    private float applyRollFilter(float input) {
        if (scalerPitch <= 0.5) {
            float i = (scalerPitch * 2 * input) + (0.5f * (1 - (scalerPitch * 2)));

            return i;
        }
        else {
            float i = scalerPitch - 0.5f;
            float j = ((1 - (i * 2)) * input) + (0.5f * (i * 2));

            return j;
        }
    }
}
