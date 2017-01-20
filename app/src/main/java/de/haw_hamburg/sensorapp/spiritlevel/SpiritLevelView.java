package de.haw_hamburg.sensorapp.spiritlevel;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
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

    public void updateLayoutParameters() {
        indicatorHeight = spiritLevelIndicator.getHeight();
        indicatorWidth = spiritLevelIndicator.getWidth();
        maxViewHeight = this.getHeight();
        maxViewWidth = this.getWidth();
        borderOffsetX = (int) ((maxViewWidth * 0.08) / 2);
        borderOffsetY = (int) ((maxViewHeight * (80/1700f)) / 2);
        maxSpiritLevelWidth = maxViewWidth - (2 * borderOffsetX);
        maxSpiritLevelHeight = maxViewHeight - (2 * borderOffsetY);
    }

    public void changeSpiritLevel(float pitch, float roll) {
        int targetX = calculatePosition((roll+270)%360, maxSpiritLevelWidth, borderOffsetX, indicatorWidth);
        int targetY = calculatePosition((pitch+270)%360, maxSpiritLevelHeight, borderOffsetY, indicatorHeight);
        Log.d("spirit", "pitch : "+pitch+" roll : "+roll);
        Log.d("spirit", "target x :"+targetX+" target y : "+targetY);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(spiritLevelIndicator, ImageView.X, targetX);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(spiritLevelIndicator, ImageView.Y, targetY);
        animatorX.setInterpolator(new LinearInterpolator());
        animatorY.setInterpolator(new LinearInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.start();
    }

    private int calculatePosition(float input, int dimension, int borderOffset, int indicatorDimension) {
        float scaler;
        if (input <= 180) {
            scaler = input / 180;
        }
        else {
            float i = input -180;
            scaler = 1 - (i / 180);
        }
        int position = (int) ((scaler * dimension) + borderOffset - (indicatorDimension / 2));
        return position;
    }
}
