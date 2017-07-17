package de.haw_hamburg.sensorapp.recorder;

import android.hardware.SensorEvent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import de.haw_hamburg.sensorapp.csv.CSVWriter;
import de.haw_hamburg.sensorapp.mvp.AbstractPresenter;
import de.haw_hamburg.sensorapp.recorder.settings.Sensor;
import de.haw_hamburg.sensorapp.sensor.SensorEventListenerInteractor;
import de.haw_hamburg.sensorapp.sensor.writer.SensorDescriptor;
import de.haw_hamburg.sensorapp.sensor.writer.SensorDescriptorsProvider;
import de.haw_hamburg.sensorapp.sensor.writer.SensorEventsCSVWriter;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by s.lange on 29.12.16.
 */

public class RecorderPresenter extends AbstractPresenter<RecorderView> {

    public static final String TAG = RecorderPresenter.class.getSimpleName();
    private final GetEnabledSensorInteractor enabledSensorInteractor;
    private final SensorEventListenerInteractor sensorEventListenerInteractor;
    private final SensorDescriptorsProvider sensorDescriptorsProvider;
    private File file;
    private BufferedWriter bufferedFileWriter;
    private SensorEventsCSVWriter sensorEventsCSVWriter;
    private Subscription subscription;
    private boolean recording;

    @Inject
    public RecorderPresenter(GetEnabledSensorInteractor getEnabledSensorInteractor,
                             SensorEventListenerInteractor sensorEventListenerInteractor,
                             SensorDescriptorsProvider sensorDescriptorsProvider) {
        this.enabledSensorInteractor = getEnabledSensorInteractor;
        this.sensorEventListenerInteractor = sensorEventListenerInteractor;
        this.sensorDescriptorsProvider = sensorDescriptorsProvider;
    }


    public void initialize() {
        getView().showNoSensorsEnabledHint();
        enabledSensorInteractor.execute()
                .flatMapIterable(sensors -> sensors)
                .subscribe(sensor -> {
                    getView().addSensorLineChart(sensor);
                    getView().showRecorderControls();
                });
    }

    public void onSettingsMenuItemClicked() {
        if (recording == false) {
            getView().showRecorderSettings();
        }
    }

    public void onControlButtonClicked() {
        if (isRecording()) {
            stopRecording();
            return;
        }
        startRecording();
    }

    private void startRecording() {
        recording = true;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy-HH-mm-ss");
        String todayDate = dateFormat.format(new Date());
        file = new File(Environment.getExternalStorageDirectory(), "Record-" + todayDate + ".csv");
        bufferedFileWriter = createBufferedFileWriter(file);
        CSVWriter csvWriter = new CSVWriter(bufferedFileWriter);
        sensorEventsCSVWriter = new SensorEventsCSVWriter(csvWriter);
        subscription = enabledSensorInteractor.execute()
                .flatMapIterable(sensors -> sensors)
                .map(new Func1<Sensor, Sensor>() {
                    @Override
                    public Sensor call(Sensor sensor) {
                        SensorDescriptor sensorDescriptor = getSensorDescriptor(sensor);
                        sensorEventsCSVWriter.addSensorDescriptor(sensorDescriptor);
                        return sensor;
                    }
                })
                .toList()
                .flatMapIterable(sensors -> sensors)
                .flatMap(new Func1<Sensor, Observable<SensorEvent>>() {
                    @Override
                    public Observable<SensorEvent> call(Sensor sensor) {
                        return sensorEventListenerInteractor.execute(sensor);
                    }
                })
                .map(new Func1<SensorEvent, SensorEvent>() {
                    @Override
                    public SensorEvent call(SensorEvent sensorEvent) {
                        getView().addSensorEvent(sensorEvent);
                        return sensorEvent;
                    }
                })
                .subscribe(new Action1<SensorEvent>() {
                    @Override
                    public void call(SensorEvent sensorEvent) {
                        sensorEventsCSVWriter.write(sensorEvent);
                    }
                });
        getView().showStopButton();
    }

    private BufferedWriter createBufferedFileWriter(File file) {
        try {
            return new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private SensorDescriptor getSensorDescriptor(Sensor sensor) {
        return sensorDescriptorsProvider.getSensorDescriptor(sensor);
    }

    public void stopRecording() {
        recording = false;
        subscription.unsubscribe();
        closeWriter();
        getView().showStartButton();
        getView().exportFile(Uri.fromFile(file));
    }

    private void closeWriter() {
        try {
            bufferedFileWriter.flush();
            bufferedFileWriter.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close writer", e);
        }
    }

    public void onOpenSettingsButtonClicked() {
        getView().showRecorderSettings();
    }

    public boolean isRecording() {
        return recording;
    }
}
