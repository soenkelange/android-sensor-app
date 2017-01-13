package de.haw_hamburg.sensorapp;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class CompassView extends RelativeLayout{

    private ImageView compassView;
    private float currentAzimuth;

    public CompassView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.view_compass, this, true);
        compassView = (ImageView)rootView.findViewById(R.id.compass);
        currentAzimuth = 0;
    }

    public void rotateCompass (float azimuth){
        Animation animation = new RotateAnimation(-currentAzimuth, -azimuth, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        currentAzimuth = azimuth;
        animation.setDuration(500);
        animation.setRepeatCount(0);
        animation.setFillAfter(true);
        compassView.startAnimation(animation);
    }
}
