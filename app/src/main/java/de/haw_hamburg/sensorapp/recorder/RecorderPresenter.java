package de.haw_hamburg.sensorapp.recorder;

import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import de.haw_hamburg.rxandroidsensor.RxSensorManager;
import de.haw_hamburg.sensorapp.csv.CSVWriter;
import de.haw_hamburg.sensorapp.mvp.AbstractPresenter;
import de.haw_hamburg.sensorapp.recorder.settings.Sensor;
import de.haw_hamburg.sensorapp.sensor.SensorEventCSVWriter;
import de.haw_hamburg.sensorapp.sensor.SensorEventsCSVRecorder;
import de.haw_hamburg.sensorapp.sensor.csv.AccelerometerSensorEventCSVWriter;
import de.haw_hamburg.sensorapp.sensor.csv.GravitySensorEventCSVWriter;
import de.haw_hamburg.sensorapp.sensor.csv.GyroscopeSensorEventCSVWriter;
import de.haw_hamburg.sensorapp.sensor.csv.MagneticFieldSensorEventCSVWriter;
import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;

/**
 * Created by s.lange on 29.12.16.
 */

public class RecorderPresenter extends AbstractPresenter<RecorderView> {

    private static final String TAG = RecorderPresenter.class.getSimpleName();
    private final GetEnabledSensorInteractor enabledSensorInteractor;
    private final RxSensorManager rxSensorManager;

    private Subscription sensorSubscription;
    private boolean isRecording = false;
    private File file;
    private FileWriter fileWriter;

    @Inject
    public RecorderPresenter(GetEnabledSensorInteractor getEnabledSensorInteractor, RxSensorManager rxSensorManager) {
        this.enabledSensorInteractor = getEnabledSensorInteractor;
        this.rxSensorManager = rxSensorManager;
        ;
    }

    @Override
    public void attachView(RecorderView view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (sensorSubscription != null && !sensorSubscription.isUnsubscribed()) {
            sensorSubscription.unsubscribe();
        }
    }

    public void initializeView() {
        getView().showStartButton();
    }

    public void onStartClicked() {
        startRecording();
    }

    public void onStopClicked() {
        stopRecording();
    }

    public void onSettingsClicked() {
        if (isRecording) {
            getView().showStopRecordingDialog();
        } else {
            getView().showRecorderSettings();
        }
    }

    private void startRecording() {
        getView().hideStartButton();
        getView().showStopButton();
        isRecording = true;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy-HH-mm-ss");
        String todayDate = dateFormat.format(new Date());
        file = new File(Environment.getExternalStorageDirectory(), "Record-" + todayDate + ".csv");
        fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CSVWriter csvWriter = new CSVWriter(fileWriter);
        SensorEventsCSVRecorder sensorEventsCSVRecorder = new SensorEventsCSVRecorder(csvWriter);
        sensorSubscription = enabledSensorInteractor.execute()
                .flatMapIterable(new Func1<List<Sensor>, Iterable<Sensor>>() {
                    @Override
                    public Iterable<Sensor> call(List<Sensor> sensors) {
                        return sensors;
                    }
                })
                .map(sensor -> {
                    SensorEventCSVWriter sensorEventsCSVWriter = createSensorEventsCSVWriter(sensor);
                    if (sensorEventsCSVWriter != null) {
                        sensorEventsCSVRecorder.addSensorEventCSVWriter(sensorEventsCSVWriter);
                    }
                    return sensor;
                })
                .toList()
                .flatMapIterable(new Func1<List<Sensor>, Iterable<Sensor>>() {
                    @Override
                    public Iterable<Sensor> call(List<Sensor> sensors) {
                        return sensors;
                    }
                })
                .flatMap(new Func1<Sensor, Observable<SensorEvent>>() {
                    @Override
                    public Observable<SensorEvent> call(Sensor sensor) {
                        return rxSensorManager.observeSensorEvents(sensor.getType(), SensorManager.SENSOR_DELAY_NORMAL);
                    }
                })
                .subscribe(sensorEventsCSVRecorder::write);
    }

    private SensorEventCSVWriter createSensorEventsCSVWriter(Sensor sensor) {
        switch (sensor.getType()) {
            case android.hardware.Sensor.TYPE_ACCELEROMETER:
                return new AccelerometerSensorEventCSVWriter();
            case android.hardware.Sensor.TYPE_GRAVITY:
                return new GravitySensorEventCSVWriter();
            case android.hardware.Sensor.TYPE_GYROSCOPE:
                return new GyroscopeSensorEventCSVWriter();
            case android.hardware.Sensor.TYPE_MAGNETIC_FIELD:
                return new MagneticFieldSensorEventCSVWriter();
        }
        return null;
    }

    private void stopRecording() {
        sensorSubscription.unsubscribe();
        try {
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getView().exportCSV(Uri.fromFile(file));
        file = null;
        isRecording = false;
        getView().hideStopButton();
        getView().showStartButton();
    }
}
